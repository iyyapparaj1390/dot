package weakencode.com.weakencode.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weakencode.com.weakencode.Api.CreateDotPreference;
import weakencode.com.weakencode.Api.MyModulePreference;
import weakencode.com.weakencode.Api.Networkcall;
import weakencode.com.weakencode.R;

/**
 * Created by samsung on 6/5/2018.
 */

public class Jobservice extends JobService {
    SharedPreferences preference;
    MyModulePreference pref;

    Handler mHandler;
    BroadcastReceiver locationrecieve;
    Timer myTimer;
    NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    Context con;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        final Context con=Jobservice.this;
       // pref=CreateDotPreference.getInstance(con);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                if(Permission.checknetworkfrom(con))
                {
                    getdatlist();
                }

            }
        };
        loadtimer();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    void loadtimer()
    {


        myTimer=new Timer();
        myTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //Toast.makeText(Myservice.this, "timer", 1000).show();
                Log.e("timerfromjob","calling");
                Message message = mHandler.obtainMessage();
                message.sendToTarget();

            }
        }, 0,10*1000);
    }
    synchronized  void getdatlist()
    {
        pref=new MyModulePreference(Jobservice.this);
        Gson gson = new Gson();
        String json = pref.getString(CreateDotPreference.list,"");
        if(!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Createdot>>() {
            }.getType();
            ArrayList<Createdot> dotscreate = gson.fromJson(json, type);
            for (int i = 0; i < dotscreate.size(); i++) {
                final int p = i;
                final Createdot createdot = dotscreate.get(i);
                Apicalls networkcall = Networkcall.getClient().create(Apicalls.class);
                Call<String> getDetails = networkcall.createDot("Bearer " + pref.getString(CreateDotPreference.token,""), createdot.getName(), createdot.getPh_country_code(), createdot.getPhone_number(),
                        String.valueOf(createdot.getCategory_id()), createdot.getAddress(), createdot.getDescription(), createdot.getLat(), createdot.getLng(),
                        createdot.getPost_code(), createdot.getStreet(), createdot.getCity(), createdot.getState(), createdot.getCountry(), createdot.getFolder_id());

                getDetails.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {
                            Log.e("response", "" + response.body().toString());

                            if (response != null) {

                                JSONObject object = new JSONObject(response.body().toString());
                                remove(p, createdot.getName());
                                //Log.e("response", "" + getdata.toString());
                                Permission.showmessage("Successfully Created", (Context) Jobservice.this);
                            }
                        } catch (Exception e) {


                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {


                    }
                });

            }
        }

    }
    synchronized void remove(int p,String name)
    {
        Gson gson = new Gson();
        String json = pref.getString(CreateDotPreference.list,"");

        Type type = new TypeToken<ArrayList<Createdot>>(){}.getType();
        ArrayList<Createdot> dotscreate= gson.fromJson(json, type);
        dotscreate.remove(p);
        String jsonsave = gson.toJson(dotscreate);
        pref.put(CreateDotPreference.list,jsonsave);

        setData(name);

    }
    private void setData(String name) {
        notificationBuilder = new NotificationCompat.Builder(Jobservice.this)
                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle("Dotcreated")
                .setContentText(name);
        sendNotification();
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notificationManager.notify((int)System.currentTimeMillis(), notification);
    }

}
