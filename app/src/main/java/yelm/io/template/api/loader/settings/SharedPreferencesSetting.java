package yelm.io.template.api.loader.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSetting {

    public static final String USER_NAME = "USER_NAME";
    public static final String MIN_PRICE_FOR_FREE_DELIVERY = "MIN_PRICE_FOR_FREE_DELIVERY";
    public static final String COLOR = "COLOR";
    public static final String MIN_ORDER_PRICE = "MIN_ORDER_PRICE";
    public static final String PRICE_IN = "PRICE_IN";
    public static final String CURRENCY = "CNT";
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    public static final String API_TOKEN = "API_TOKEN";
    public static final String ROOM_CHAT_ID = "ROOM_ID";
    public static final String SHOP_CHAT_ID = "SHOP_ID";
    public static final String CLIENT_CHAT_ID = "CLIENT_ID";

    public static final String DISCOUNT_TYPE = "DISCOUNT_TYPE";
    public static final String DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    public static final String DISCOUNT_NAME = "DISCOUNT_NAME";

    public static final String PAYMENT_CARD = "PAYMENT_CARD";
    public static final String PAYMENT_MOBILE = "PAYMENT_MOBILE";
    public static final String PAYMENT_CASH = "PAYMENT_CASH";

    private static SharedPreferences settings = null;
    private static final String APP_PREFERENCES = "settings";

    public static SharedPreferences initSharedPreferencesSettings(Context context) {
        if (settings == null) {
            settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        }
        return settings;
    }

    public static void setData(String key, String data) {
        settings.edit().putString(key, data).apply();
    }

    public static void setData(String key, Boolean data) {
        settings.edit().putBoolean(key, data).apply();
    }

    public static String getDataString(String key) {
        return settings.getString(key, "");
    }

    public static Boolean getDataBoolean(String key) {
        return settings.getBoolean(key, false);
    }

}