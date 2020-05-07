package com.techuva.ome_new.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**

 Created by kishore.i on 02/23/2019.
 */
public class Utils {

    public static String getStringFromSP(Context context, String preferenceKey) {
        SharedPreferences preferences = context.getSharedPreferences("OEM_PREFERENCE", Context.MODE_PRIVATE);
        String preferenceValue = preferences.getString(preferenceKey, null);
        return preferenceValue;
    }


    public static void setStringToSP(Context context, String preferenceKey, String preferenceValue) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("OEM_PREFERENCE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(preferenceKey, preferenceValue);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}