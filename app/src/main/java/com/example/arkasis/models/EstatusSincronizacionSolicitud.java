package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class EstatusSincronizacionSolicitud {
    private int idSolicitud;
    private boolean bitRegistrado;
    private String strMensaje;


    public EstatusSincronizacionSolicitud(LinkedTreeMap<Object, Object> treeMap) {
        this.idSolicitud = Integer.parseInt(treeMap.get("idSolicitud").toString().trim());
        this.bitRegistrado = Boolean.parseBoolean(treeMap.get("bitRegistrado").toString().trim());
        this.strMensaje = treeMap.get("strMensaje").toString().trim();
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public boolean isBitRegistrado() {
        return bitRegistrado;
    }

    public void setBitRegistrado(boolean bitRegistrado) {
        this.bitRegistrado = bitRegistrado;
    }

    public String getStrMensaje() {
        return strMensaje;
    }

    public void setStrMensaje(String strMensaje) {
        this.strMensaje = strMensaje;
    }
}
