package weakencode.com.weakencode;

/**
 * Created by samsung on 3/25/2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import weakencode.com.weakencode.Api.CreateDotPreference;
import weakencode.com.weakencode.Api.MyModulePreference;
import weakencode.com.weakencode.helper.Jobservice;
import weakencode.com.weakencode.helper.Myservice;
import weakencode.com.weakencode.helper.Permission;
import weakencode.com.weakencode.helper.Utills;


/**
 * Created by abimathi on 03-Jan-17.
 */
public class SplashScreenActivity extends Activity {

MyModulePreference pref;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }
       // Permission.verifyStoragePermissions(this);

//pref=CreateDotPreference.getInstance(SplashScreenActivity.this);
        pref=new MyModulePreference(SplashScreenActivity.this);
        Thread startmenu=new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(4000);
                    runOnUiThread(new Runnable() {
                        public void run() {

                            if (Build.VERSION.SDK_INT <=Build.VERSION_CODES.N) {

                                ///Utills.scheduleJob(SplashScreenActivity.this);

                                if(!isMyServiceRunning(Myservice.class)) {
                                    startService(new Intent(SplashScreenActivity.this, Myservice.class));
                                }
                                else{
                                    Log.e("alreadystarted","start");
                                }
                                   if(!pref.getBoolean(CreateDotPreference.login,false))
                                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                else
                                   startActivity(new Intent(SplashScreenActivity.this,DotcreateActivity.class));
                                finish();
                            }
                            else{
                                if(!isMyServiceRunning(Jobservice.class)) {

                                    Utills.scheduleJob(SplashScreenActivity.this);
                                }
                                else{
                                    Log.e("alreadystarted","start");

                                }
                                if(!pref.getBoolean(CreateDotPreference.login,false))
                                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                else
                                    startActivity(new Intent(SplashScreenActivity.this,DotcreateActivity.class));
                                finish();

                            }

                        }
                    });
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        startmenu.start();

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
