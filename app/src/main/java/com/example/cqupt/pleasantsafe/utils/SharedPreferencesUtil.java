package com.example.cqupt.pleasantsafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ming on 2017/11/20.
 */

public class SharedPreferencesUtil {


    private static SharedPreferences sp;


    public static boolean getBoolean(Context context, String key, boolean defValue) {
        boolean isToggledOn = getInstance(context).getBoolean(key, defValue);

        return isToggledOn;

    }

    public static void saveBoolean(Context context, String key, boolean value) {

        getInstance(context).edit().putBoolean(key, value).commit();

    }


    public static SharedPreferences getInstance(Context context) {

        if (sp == null) {

            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp;
    }

}
