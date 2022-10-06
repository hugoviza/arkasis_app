package com.example.arkasis.utilerias;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arkasis.models.Dispositivo;
import com.example.arkasis.models.Usuario;

public class SharedPreferencesData {
    public static Usuario getUsuario(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("user", "") != "") {
            Usuario usuario = new Usuario();
            usuario.setUser(sharedPreferences.getString("user", null));
            usuario.setPassword(sharedPreferences.getString("password", null));
            usuario.setNombre(sharedPreferences.getString("name", null));
            return usuario;
        } else {
            return null;
        }
    }

    public static void setUsuario(Usuario usuario, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor sesion = sharedPreferences.edit();
        sesion.putString("user", usuario.getUser());
        sesion.putString("password", usuario.getPassword());
        sesion.putString("name", usuario.getNombre());
        sesion.commit();
    }



    public static Dispositivo getDispositivo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dispositivo", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("tokenActivacion", "") != "") {
            Dispositivo data = new Dispositivo();
            data.setIdSucursal(sharedPreferences.getString("idSucursal", null));
            data.setIdDispositivo(sharedPreferences.getInt("idDispositivo", 0));
            data.setPlataforma(sharedPreferences.getString("plataforma", null));
            data.setUUIDDispositivo(sharedPreferences.getString("UUIDDispositivo", null));
            data.setFechaHoraRegistroUUIDDispositivo(sharedPreferences.getString("fechaHoraRegistroUUIDDispositivo", null));
            data.setTokenActivacion(sharedPreferences.getString("tokenActivacion", null));
            data.setFechaHoraRegistroTokenActivacion(sharedPreferences.getString("fechaHoraRegistroTokenActivacion", null));
            data.setEstatusTokenAcivacion(sharedPreferences.getInt("estatusTokenAcivacion", 0));
            data.setUsuarioAlta(sharedPreferences.getString("usuarioAlta", null));
            data.setFechaHoraAlta(sharedPreferences.getString("fechaHoraAlta", null));
            return data;
        } else {
            return null;
        }
    }

    public static void setDispositivo(Dispositivo dispositivo, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dispositivo", Context.MODE_PRIVATE);
        SharedPreferences.Editor data = sharedPreferences.edit();
        data.putInt("idDispositivo", dispositivo.getIdDispositivo());
        data.putString("idSucursal", dispositivo.getIdSucursal());
        data.putInt("sharedPreferences", dispositivo.getIdDispositivo());
        data.putString("plataforma", dispositivo.getPlataforma());
        data.putString("UUIDDispositivo", dispositivo.getUUIDDispositivo());
        data.putString("fechaHoraRegistroUUIDDispositivo", dispositivo.getFechaHoraRegistroUUIDDispositivo());
        data.putString("tokenActivacion", dispositivo.getTokenActivacion());
        data.putString("fechaHoraRegistroTokenActivacion", dispositivo.getFechaHoraRegistroTokenActivacion());
        data.putInt("estatusTokenAcivacion", dispositivo.getEstatusTokenAcivacion());
        data.putString("usuarioAlta", dispositivo.getUsuarioAlta());
        data.putString("fechaHoraAlta", dispositivo.getFechaHoraAlta());
        data.commit();
    }
}
