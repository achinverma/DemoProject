package com.signity.library.utils;

/**
 * Created by Signity.
 */

public class Logg {

    static final boolean IS_LOGGING = true;

    public static void i(String tag, String string) {
        if (IS_LOGGING) android.util.Log.i(tag, string);
    }
    public static void e(String tag, String string) {
        if (IS_LOGGING) android.util.Log.e(tag, string);
    }
    public static void d(String tag, String string) {
        if (IS_LOGGING) android.util.Log.d(tag, string);
    }
    public static void v(String tag, String string) {
        if (IS_LOGGING) android.util.Log.v(tag, string);
    }
    public static void w(String tag, String string) {
        if (IS_LOGGING) android.util.Log.w(tag, string);
    }

}
