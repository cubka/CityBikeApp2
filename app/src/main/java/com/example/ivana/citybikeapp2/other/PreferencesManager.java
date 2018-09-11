package com.example.ivana.citybikeapp2.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("MySharedPreferencesFile", Activity.MODE_PRIVATE);
    }

        public static void setLimit (int limit, Context c){
            getPreferences(c).edit().putInt("limit", limit).apply();
        }
        public static int getLimit(Context limit){
            return getPreferences(limit).getInt("limit", 60);
        }
}
