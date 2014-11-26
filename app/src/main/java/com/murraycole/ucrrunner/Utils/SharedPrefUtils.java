package com.murraycole.ucrrunner.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by C on 11/26/2014.
 */
public class SharedPrefUtils {
    public static String getUID(Context context){
        SharedPreferences fbPrefs = (context).getSharedPreferences("FBPREFS", 0);
        return fbPrefs.getString("userData.uid", "");
    }
}
