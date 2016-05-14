package edu.umn.pednavigation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sandeep on 10/28/2015.
 */
public class Util {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        LogUtils.log("cm value: " + cm);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        LogUtils.log("netinfo value: " + netInfo);
        if (netInfo != null)
            LogUtils.log("is connecting " + netInfo.isConnectedOrConnecting());
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getDir(int direction){
        String dir = "none";
        if(direction >=345 || direction <=15)
            dir = "North";
        else if (direction >=75 && direction <=105)
            dir = "East";
        else if (direction >=30 && direction <=60)
            dir = "Northeast";
        else if (direction >=120 && direction <=150)
            dir = "Southeast";
        else if(direction>=165 && direction<=195)
            dir="South";
        else if (direction >=210 && direction <=240)
            dir = "Southwest";
        else if (direction >=300 && direction <=330)
            dir = "Northwest";
        else if(direction >=255 && direction <=285)
            dir="West";
        return dir;
    }

    public static String getDirectionCode(double dirDeg) {
        String dirStr = " ";
        if ((dirDeg > 348.75 && dirDeg <= 360) || (dirDeg >= 0 && dirDeg <= 11.25)) {
            dirStr = " north ";
        } else if (dirDeg > 11.25 && dirDeg <= 33.75) {
            dirStr = " north east north ";
        } else if (dirDeg > 33.75 && dirDeg <= 56.25) {
            dirStr = " north east ";
        } else if (dirDeg > 56.25 && dirDeg <= 78.75) {
            dirStr = " north east east ";
        } else if (dirDeg > 78.75 && dirDeg <= 101.25) {
            dirStr = " east ";
        } else if (dirDeg > 101.25 && dirDeg <= 123.75) {
            dirStr = " south east east ";
        } else if (dirDeg > 123.75 && dirDeg <= 146.25) {
            dirStr = " south east ";
        } else if (dirDeg > 146.25 && dirDeg <= 168.75) {
            dirStr = " south east south ";
        } else if (dirDeg > 168.75 && dirDeg <= 191.25) {
            dirStr = " south ";
        } else if (dirDeg > 191.25 && dirDeg <= 213.75) {
            dirStr = " south west south ";
        } else if (dirDeg > 213.75 && dirDeg <= 236.25) {
            dirStr = " south west ";
        } else if (dirDeg > 236.25 && dirDeg <= 258.75) {
            dirStr = " south west west ";
        } else if (dirDeg > 258.75 && dirDeg <= 281.25) {
            dirStr = " south ";
        } else if (dirDeg > 281.25 && dirDeg <= 303.75) {
            dirStr = " north west west ";
        } else if (dirDeg > 303.75 && dirDeg <= 326.25) {
            dirStr = " north west ";
        } else if (dirDeg > 326.25 && dirDeg <= 348.75) {
            dirStr = " north west north ";
        }
        return dirStr;
    }
}
