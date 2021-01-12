package com.example.arkasis.utilerias;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = "Conectado a internet";
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Conectado con WIFI";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Conectado con DATOS MOVILES";
                return status;
            }
        } else {
            status = "";
            return status;
        }
        return status;
    }
}
