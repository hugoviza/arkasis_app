package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Estado {
    private String idEstado;
    private String strEstado;

    public Estado(LinkedTreeMap<Object, Object> treeMap) {
        this.idEstado = treeMap.get("idEstado").toString().trim();
        this.strEstado = treeMap.get("strEstado").toString().trim();
    }

    public Estado(String idEstado, String strEstado) {
        this.idEstado = idEstado.trim();
        this.strEstado = strEstado.trim();
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getStrEstado() {
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }
}
