package yelm.io.template;

import android.util.Log;

public class Logging {
    private static String error = "ErrorMessage";
    private static String debug = "DebugMessage";

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