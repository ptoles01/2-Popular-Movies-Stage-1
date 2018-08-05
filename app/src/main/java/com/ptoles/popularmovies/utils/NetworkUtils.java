package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ptoles.popularmovies.MainActivity;

import java.util.Objects;

public class NetworkUtils {
    //https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     /*  Check the internet connection
     // Return: true if connected to the internet
     //         false if not connected to the internet
     */
    private static NetworkUtils instance = new NetworkUtils();
    static Context context;
    static boolean  connected = false;

    public static NetworkUtils getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
            }

            connected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
            connected = false;
            return connected;
        }

    }
}
