package yelm.io.template.stuff;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Handler;

import androidx.annotation.NonNull;

public class InternetConnectivity {

    private static Listener listener;

    public interface Listener {
        void internetConnectivityStatus(boolean status);
    }

    private static ConnectivityManager.NetworkCallback networkCallback;
    private static ConnectivityManager connectivityManager;
    private static NetworkRequest networkRequest;

    public static void registerNetworkCallback(Context context) {
        getConnectivityManager(context).registerNetworkCallback(networkRequest, networkCallback);
    }

    public static void unregisterNetworkCallback(Context context) {
        getConnectivityManager(context).unregisterNetworkCallback(networkCallback);
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        if (connectivityManager == null) {
            initInternetConnectivity(context);
            listener = (InternetConnectivity.Listener) context;
        }
        return connectivityManager;
    }

    private static void initInternetConnectivity(Context context) {
        networkRequest = new NetworkRequest.Builder().build();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Logging.logDebug("onAvailable");
                listener.internetConnectivityStatus(true);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Logging.logDebug("onLost");
                listener.internetConnectivityStatus(false);
            }

            //TODO! solve the problem: method onUnavailable() is never called
            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Logging.logDebug("onUnavailable");
                listener.internetConnectivityStatus(false);
            }
        };
    }
}