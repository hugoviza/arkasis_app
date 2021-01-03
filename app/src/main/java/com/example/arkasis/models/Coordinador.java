package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Coordinador {
    private int idCoordinador;
    private int idSucursal;
    private String strNombre;

    public Coordinador(int idCoordinador, int idSucursal, String strNombre) {
        this.idCoordinador = idCoordinador;
        this.idSucursal = idSucursal;
        this.strNombre = strNombre;
    }

    public Coordinador(LinkedTreeMap<Object, Object> treeMap) {
        this.idCoordinador = Integer.parseInt(treeMap.get("idCoordinador").toString().trim());
        this.idSucursal = Integer.parseInt(treeMap.get("idSucursal").toString().trim());
        this.strNombre = treeMap.get("strNombre").toString().trim();
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }
}
