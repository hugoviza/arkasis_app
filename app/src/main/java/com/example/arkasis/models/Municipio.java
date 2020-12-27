package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Municipio {
    private String idEstado_idMunicipio, idEstado, strEstado, idMunicipio, strMunicipio;

    public Municipio() {
        idEstado = "";
        strEstado = "";
        idMunicipio = "";
        strMunicipio = "";
    }

    public Municipio(String idEstado, String strEstado, String idMunicipio, String strMunicipio) {
        this.idEstado = idEstado.trim();
        this.strEstado = strEstado.trim();
        this.idMunicipio = idMunicipio.trim();
        this.strMunicipio = strMunicipio.trim();
    }

    public Municipio(LinkedTreeMap<Object, Object> treeMap) {
        this.idEstado = treeMap.get("idEstado").toString().trim();
        this.strEstado = treeMap.get("strEstado").toString().trim();
        this.idMunicipio = treeMap.get("idMunicipio").toString().trim();
        this.strMunicipio = treeMap.get("strMunicipio").toString().trim();
    }

    public String getIdEstado_IdMunicipio() {
        return this.idEstado + "_" + this.idMunicipio;
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

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getStrMunicipio() {
        return strMunicipio;
    }

    public void setStrMunicipio(String strMunicipio) {
        this.strMunicipio = strMunicipio;
    }
}
