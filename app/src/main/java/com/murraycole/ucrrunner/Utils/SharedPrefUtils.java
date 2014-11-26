package com.murraycole.ucrrunner.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by C on 11/26/2014.
 */
public class SharedPrefUtils {
    public static String getCurrUID(Context context){
        SharedPreferences fbPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return fbPrefs.getString("userData.uid", null);
    }
}
