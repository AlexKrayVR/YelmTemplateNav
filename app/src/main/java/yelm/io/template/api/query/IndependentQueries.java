package yelm.io.template.api.query;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yelm.io.template.api.client.RetrofitClient;
import yelm.io.template.api.interfaces.RestAPI;
import yelm.io.template.loader.app_settings.SharedPreferencesSetting;
import yelm.io.template.stuff.Logging;

public class IndependentQueries {

    static public void sendStatistic(String type) {
        Logging.logDebug("Method sendStatistic() - type: " + type);
        RetrofitClient.
                getClient(RestAPI.URL_API_MAIN).
                create(RestAPI.class).
                sendStatistic(
                        RestAPI.PLATFORM_NUMBER,
                        SharedPreferencesSetting.getDataString(SharedPreferencesSetting.USER_NAME),
                        type).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull final Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            Logging.logError("Method sendStatistic() - response is not successful." +
                                    "Code: " + response.code() + "Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Logging.logError("Method sendStatistic() - failure: " + t.toString());
                    }
                });
    }

    public static void sendAppTokenToServer(String token) {
        Logging.logDebug("Method sendAppTokenToServer() - token: " + token);
        RetrofitClient
                .getClient(RestAPI.URL_API_MAIN)
                .create(RestAPI.class)
                .putFCM(RestAPI.PLATFORM_NUMBER,
                        SharedPreferencesSetting.getDataString(SharedPreferencesSetting.USER_NAME),
                        token)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull final Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Logging.logDebug("FCM Token registered");
                        } else {
                            Logging.logDebug("Method sendRegistrationToServer() - Code: " + response.code() + "Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Logging.logDebug("Method sendRegistrationToServer() - failed: " + t.toString());
                    }
                });
    }


}
