package weakencode.com.weakencode.helper;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.security.MessageDigest;

/**
 * Created by iyyapparajr on 3/29/2017.
 */
public class Permission {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };

   /* <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />*/
   /* <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_CONTACTS"/>*/

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );


        }
    }
     public static boolean checknetwork(Activity act)
    {
        ConnectivityManager ConnectionManager=(ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {
            return true;
            //Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_LONG).show();

        }
        else
        {
            return false;
            //Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();

        }
    }
    public static boolean checknetworkfrom(Context act)
    {
        ConnectivityManager ConnectionManager=(ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {
            return true;
            //Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_LONG).show();

        }
        else
        {
            return false;
            //Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();

        }
    }
        /*if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_EXTERNAL_STORAGE);
        }*/
        public static void showmessage(String message, Activity act)
        {
            Toast.makeText(act,message, Toast.LENGTH_LONG).show();
        }
    public static void showmessage(String message, Context act)
    {
        Toast.makeText(act,message, Toast.LENGTH_LONG).show();
    }


    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}





/**
 * Created by samsung on 3/25/2018.
 */

//public class Permission {
//
//    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
//
//    static String[] permissions = new String[]{
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//
//            Manifest.permission.CAMERA,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION};
//
//
//
//    public static void checkPermissions(Activity per) {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p:permissions) {
//            if (ActivityCompat.checkSelfPermission(per, p) != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        try
//        {
//        ActivityCompat.requestPermissions(per, permissions, MULTIPLE_PERMISSIONS);}
//        catch (Exception e)
//        {
//            Log.e("message",e.getMessage());
//        }
//
////        if (!listPermissionsNeeded.isEmpty()) {
////            ActivityCompat.requestPermissions(per, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
////      per      //return false;
////        }
//       // return true;
//    }
//
//
//    }





