package yelm.io.template.loader.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yelm.io.template.Constants.Constants;
import yelm.io.template.api.client.RetrofitClient;
import yelm.io.template.api.interfaces.RestAPI;
import yelm.io.template.loader.app_settings.SharedPreferencesSetting;
import yelm.io.template.databinding.ActivityLoaderBinding;
import yelm.io.template.loader.model.ApplicationSettings;
import yelm.io.template.loader.model.UserLoginResponse;
import yelm.io.template.main.MainActivity;
import yelm.io.template.stuff.DeviceInfo;
import yelm.io.template.stuff.Logging;
import yelm.io.template.stuff.NotificationChannelCreator;

public class LoaderActivity extends AppCompatActivity {
    ActivityLoaderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesSetting.initSharedPreferencesSettings(this);
        NotificationChannelCreator.createNotificationChannel(this);

    }

    /**
     * get main settings of application such as: MERCHANT_PUBLIC_ID, MIN_ORDER_PRICE, CURRENCY, etc
     * and after all launch MainActivity
     */
    private void getApplicationSettings() {
        RetrofitClient.
                getClient(RestAPI.URL_API_MAIN).
                create(RestAPI.class).
                getAppSettings(RestAPI.PLATFORM_NUMBER,
                        getResources().getConfiguration().locale.getLanguage(),
                        getResources().getConfiguration().locale.getCountry()
                ).
                enqueue(new Callback<ApplicationSettings>() {
                    @Override
                    public void onResponse(@NotNull Call<ApplicationSettings> call, @NotNull final Response<ApplicationSettings> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Constants.MERCHANT_PUBLIC_ID = response.body().getSettings().getPublicId();
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.MIN_PRICE_FOR_FREE_DELIVERY, response.body().getSettings().getMinDeliveryPrice());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.MIN_ORDER_PRICE, response.body().getSettings().getMinOrderPrice());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.CURRENCY, response.body().getCurrency());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.COLOR, response.body().getSettings().getTheme());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.PRICE_IN, response.body().getSymbol());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.COUNTRY_CODE, response.body().getSettings().getRegionCode());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.PAYMENT_CARD, response.body().getSettings().getPayment().getCard());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.PAYMENT_MOBILE, response.body().getSettings().getPayment().getApplepay());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.PAYMENT_CASH, response.body().getSettings().getPayment().getPlaceorder());
                                launchMain();
                            } else {
                                Logging.logError("Method getApplicationSettings(): by some reason response is null!");
                            }
                        } else {
                            Logging.logError("Method getApplicationSettings() response is not successful." +
                                    " Code: " + response.code() + "Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ApplicationSettings> call, @NotNull Throwable t) {
                        Logging.logError("Method getApplicationSettings() failure: " + t.toString());
                    }
                });
    }

    //if user does not exist then we pull request to create it
    private void checkUser() {
        if (SharedPreferencesSetting.getSettings().contains(SharedPreferencesSetting.USER_NAME)) {
            Logging.logDebug("Method checkUser() - user exist: " + SharedPreferencesSetting.getDataString(SharedPreferencesSetting.USER_NAME));
            getApplicationSettings();
            getChatSettings(SharedPreferencesSetting.getDataString(SharedPreferencesSetting.USER_NAME));
        } else {
            RetrofitClient.
                    getClient(RestAPI.URL_API_MAIN)
                    .create(RestAPI.class)
                    .createUser(getResources().getConfiguration().locale.getLanguage(),
                            getResources().getConfiguration().locale.getCountry(),
                            RestAPI.PLATFORM_NUMBER,
                            DeviceInfo.getDeviceInfo(this))
                    .enqueue(new Callback<UserLoginResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<UserLoginResponse> call, @NotNull Response<UserLoginResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(USER_NAME, response.body().getLogin()).apply();
                                    Logging.logDebug("Method checkUser() - created user: " + settings.getString(USER_NAME, ""));
                                    getChatSettings(settings.getString(USER_NAME, ""));
                                    getApplicationSettings();
                                } else {
                                    Logging.logError("Method checkUser() - by some reason response is null!");
                                }
                            } else {
                                Logging.logError("Method checkUser() - response is not successful. " +
                                        "Code: " + response.code() + "Message: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<UserLoginResponse> call, @NotNull Throwable t) {
                            Logging.logError("Method checkUser() - failure: " + t.toString());
                        }
                    });
        }
    }



    private void launchMain() {
        Bundle args = getIntent().getExtras();
        Intent intent = new Intent(LoaderActivity.this, MainActivity.class);
        if (args != null) {
            String data = args.getString("data");
            if (data != null) {
                Logging.logDebug("LoaderActivity - Notification data: " + data);
                try {
                    JSONObject jsonObj = new JSONObject(data);
                    Logging.logDebug("jsonObj id: " + jsonObj.getString("id"));
                    Logging.logDebug("jsonObj name: " + jsonObj.getString("name"));
                    intent.putExtra("id", jsonObj.getString("id"));
                    intent.putExtra("name", jsonObj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Logging.logDebug("LoaderActivity - id: " + args.getString("id"));
                Logging.logDebug("LoaderActivity - name: " + args.getString("name"));
                intent.putExtra("id", args.getString("id"));
                intent.putExtra("name", args.getString("name"));
            }
        } else {
            Logging.logDebug("LoaderActivity - args is NULL");
        }
        startActivity(intent);
        finish();
    }

}
