package yelm.io.template.loader.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yelm.io.template.Constants.Constants;
import yelm.io.template.Constants.Statistics;
import yelm.io.template.R;
import yelm.io.template.api.client.RetrofitClient;
import yelm.io.template.api.interfaces.RestAPI;
import yelm.io.template.api.query.IndependentQueries;
import yelm.io.template.chat.model.ChatSettings;
import yelm.io.template.database.Common;
import yelm.io.template.database.Database;
import yelm.io.template.loader.app_settings.SharedPreferencesSetting;
import yelm.io.template.databinding.ActivityLoaderBinding;
import yelm.io.template.loader.model.ApplicationSettings;
import yelm.io.template.loader.model.UserLoginResponse;
import yelm.io.template.main.MainActivity;
import yelm.io.template.loader.app_settings.DeviceInfo;
import yelm.io.template.stuff.InternetConnectivity;
import yelm.io.template.stuff.Logging;
import yelm.io.template.stuff.SystemServiceState;

public class LoaderActivity extends AppCompatActivity implements InternetConnectivity.Listener {
    ActivityLoaderBinding binding;
    private static final int INTERNET_SETTINGS_CODE = 91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesSetting.initSharedPreferencesSettings(this);
        initRoom();
        InternetConnectivity.registerNetworkCallback(this);
    }

    /**
     * if network is not connected show snackbar for the user to turn on the internet connection
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!SystemServiceState.isNetworkConnected(this)) {
            showSnackbar();
        }
    }

    /**
     * init database: stores the cart and user addresses
     */
    private void initRoom() {
        Common.database = Database.getInstance(this);
        Common.basketCartDao = Common.database.basketCartDao();
        Common.userAddressesDao = Common.database.userAddressesDao();
    }

    /**
     * unregister NetworkCallback
     */
    @Override
    protected void onDestroy() {
        InternetConnectivity.unregisterNetworkCallback(this);
        super.onDestroy();
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.layout),
                R.string.loaderActivityNoNetworkConnection,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.loaderActivityUpdateNetworkConnection, view -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        startActivityForResult(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), INTERNET_SETTINGS_CODE);
                    } else {
                        //startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), INTERNET_SETTINGS_CODE);
                        startActivityForResult(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS), INTERNET_SETTINGS_CODE);
                    }
                });
        snackbar.show();
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
                                Logging.logDebug("ApplicationSettings: " + response.body().toString());

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

    /**
     * at the first start of app we check if user exist - if not we create user by pull request, otherwise continue collect app info
     */
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
                                    Logging.logDebug("Method checkUser() - created user: " + response.body().getLogin());
                                    SharedPreferencesSetting.setData(SharedPreferencesSetting.USER_NAME, response.body().getLogin());
                                    getChatSettings(response.body().getLogin());
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

    /**
     * get chat settings for socket connections such as: API_TOKEN, SHOP_CHAT_ID, ROOM_CHAT_ID, CLIENT_CHAT_ID
     *
     * @param login is the user login
     */
    private void getChatSettings(String login) {
        RetrofitClient.
                getClient(RestAPI.URL_API_MAIN).
                create(RestAPI.class).
                getChatSettings(login).
                enqueue(new Callback<ChatSettings>() {
                    @Override
                    public void onResponse(@NotNull Call<ChatSettings> call, @NotNull final Response<ChatSettings> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Logging.logDebug("ChatSettingsClass: " + response.body().toString());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.API_TOKEN, response.body().getApiToken());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.SHOP_CHAT_ID, response.body().getShop());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.ROOM_CHAT_ID, response.body().getRoomId());
                                SharedPreferencesSetting.setData(SharedPreferencesSetting.CLIENT_CHAT_ID, response.body().getClient());
                            } else {
                                Logging.logError("Method getChatSettings(): by some reason response is null!");
                            }
                        } else {
                            Logging.logError("Method getChatSettings() response is not successful." +
                                    " Code: " + response.code() + "Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ChatSettings> call, @NotNull Throwable t) {
                        Logging.logError("Method getChatSettings() failure: " + t.toString());
                    }
                });
    }

    /**
     * launch MainActivity, if LoaderActivity was launched by notifications set its data to intent
     */
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

    /**
     * launch the app if internet is available, if network is not connected show snackbar for the user to turn on the internet connection
     *
     * @param status from InternetConnectivity.class that
     */
    @Override
    public void internetConnectivityStatus(boolean status) {
        if (status) {
            IndependentQueries.sendStatistic(Statistics.OPEN_APP.getReason());
            checkUser();
        } else {
            showSnackbar();
        }
    }
}