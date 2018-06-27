package com.signity.library.utils;

import android.content.Context;

/**
 * Created by signity.
 */


public class SecurePrefManager {

    Context mContext;
    ObscuredSharedPreferences sharedPref;
    public static final String APP_PREFERENCES = "APP_PREFERENCES";

    public SecurePrefManager(Context context) {
        this.mContext = context;
        sharedPref = new ObscuredSharedPreferences(mContext, mContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE));

    }

    public void storeSharedValue(String key, String value) {
        ObscuredSharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearSharedPrefsValue(String key) {
        ObscuredSharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.commit();

    }

    public String getSharedValue(String key) {
        return sharedPref.getString(key, "");
    }

    public boolean getBooleanKeyValue(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public void storeBooleanValue(String key, boolean value) {
        ObscuredSharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void clearAppsAllPrefs() {
        ObscuredSharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }


}
