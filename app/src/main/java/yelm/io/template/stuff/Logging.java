package yelm.io.template.stuff;

import android.util.Log;

import yelm.io.template.BuildConfig;

public class Logging {
    private static String error = "AppError";
    private static String debug = "AppMessage";

    public static void logDebug(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(Logging.debug, message);
        }
    }

    public static void logError(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(Logging.error, message);
        }
    }
}