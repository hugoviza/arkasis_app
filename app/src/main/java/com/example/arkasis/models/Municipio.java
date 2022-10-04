package com.example.arkasis.models;

import com.example.arkasis.utilerias.Utileria;
import com.google.gson.internal.LinkedTreeMap;

public class Municipio {
    private String idEstado_idMunicipio, idEstado, strEstado, idMunicipio, strMunicipio;
    private int totalClientes;

    public Municipio() {
        idEstado = "";
        strEstado = "";
        idMunicipio = "";
        strMunicipio = "";
    }

    public Municipio(String idEstado, String strEstado, String idMunicipio, String strMunicipio) {
        this.idEstado = idEstado.trim();
        this.strEstado = Utileria.cleanString(strEstado.trim());
        this.idMunicipio = idMunicipio.trim();
        this.strMunicipio = Utileria.cleanString(strMunicipio.trim());
    }

    public Municipio(LinkedTreeMap<Object, Object> treeMap) {
        this.idEstado = treeMap.get("idEstado").toString().trim();
        this.strEstado = Utileria.cleanString(treeMap.get("strEstado").toString().trim());
        this.idMunicipio = treeMap.get("idMunicipio").toString().trim();
        this.strMunicipio = Utileria.cleanString(treeMap.get("strMunicipio").toString().trim());
    }

    public String getIdEstado_IdMunicipio() {
        return this.idEstado + "_" + this.idMunicipio;
    }

    public String getStrNombreMunicipioEstado() {
        return this.strMunicipio + ", " + this.strEstado;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public int getTotalClientes() { return totalClientes; }

    public void setTotalClientes(int totalClientes) { this.totalClientes = totalClientes; }

    public String getStrEstado() {
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = Utileria.cleanString(strEstado);
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
        this.strMunicipio = Utileria.cleanString(strMunicipio);
    }
}
