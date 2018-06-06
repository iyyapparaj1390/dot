package weakencode.com.weakencode.Api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by samsung on 3/31/2018.
 */

public class CreateDotPreference {
    private static CreateDotPreference dotID;
    private SharedPreferences sharedPreferences;
    public static String login="login";
    public static String token="access_token";
    public static String list="dotlist";

    public static CreateDotPreference getInstance(Context context) {
        if (dotID == null) {
            dotID = new CreateDotPreference(context);
        }
        return dotID;
    }

    private CreateDotPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("dot",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }
    public void saveDatabool(String key,boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putBoolean(key, value);
       /// prefsEditor.putString(key, ObjectSerializer.serialize(currentTasks));

        prefsEditor.apply();
    }
    public void clear() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
       // prefsEditor .putBoolean(key, value);
        /// prefsEditor.putString(key, ObjectSerializer.serialize(currentTasks));

        prefsEditor.clear();
        prefsEditor.commit();
    }



    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public boolean getDatabool(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

}
   // You can get YourPrefrence instance like:

