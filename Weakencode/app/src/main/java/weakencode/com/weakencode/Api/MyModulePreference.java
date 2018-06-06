package weakencode.com.weakencode.Api;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Created by samsung on 6/6/2018.
 */

public class MyModulePreference extends TrayPreferences {

    //public static String KEY_IS_FIRST_LAUNCH = "first_launch";
    public static String login="login";
    public static String token="access_token";
    public static String list="dotlist";

    public MyModulePreference(final Context context) {
        super(context, "myModule", 1);
    }
}

