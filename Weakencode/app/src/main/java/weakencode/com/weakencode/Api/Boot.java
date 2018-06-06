package weakencode.com.weakencode.Api;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import okhttp3.internal.Util;
import weakencode.com.weakencode.SplashScreenActivity;
import weakencode.com.weakencode.helper.Jobservice;
import weakencode.com.weakencode.helper.Myservice;
import weakencode.com.weakencode.helper.Utills;

/**
 * Created by samsung on 6/5/2018.
 */

public class Boot extends BroadcastReceiver {
    Context con;

    @Override
    public void onReceive(Context context, Intent intent) {
        con=context;
        if (Build.VERSION.SDK_INT <=Build.VERSION_CODES.N) {
            if(!isMyServiceRunning(Myservice.class)) {
                context.startService(new Intent(context, Myservice.class));
            }
        }
        else {
            if(!isMyServiceRunning(Jobservice.class)) {

                Utills.scheduleJob(context);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager)con .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}

