package com.example.arkasis.utilerias;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.arkasis.R;
import com.example.arkasis.adapters.AdaptadorListaClientes;

public class CustomReceiver extends BroadcastReceiver {
    private String status = "";
    private OnChangeStatus onChangeStatus;

    public interface OnChangeStatus {
        void onChange(String status);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        status = status.isEmpty() ? context.getString(R.string.sin_conexion) : "";
        onChangeStatus.onChange(status);
    }

    public String getStatus() {
        return status;
    }

    public void setOnItemClickListener(OnChangeStatus onChangeStatus) {
        this.onChangeStatus = onChangeStatus;
    }
}
