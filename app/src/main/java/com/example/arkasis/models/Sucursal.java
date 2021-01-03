package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Sucursal {
    private int idSucursal;
    private String strClaveSucursal;
    private String strSucursal;

    public Sucursal(int idSucursal, String strClaveSucursal, String strSucursal) {
        this.idSucursal = idSucursal;
        this.strClaveSucursal = strClaveSucursal;
        this.strSucursal = strSucursal;
    }

    public Sucursal(LinkedTreeMap<Object, Object> treeMap) {
        this.idSucursal = Integer.parseInt(treeMap.get("idSucursal").toString().trim());
        this.strClaveSucursal = treeMap.get("strClaveSucursal").toString().trim();
        this.strSucursal = treeMap.get("strSucursal").toString().trim();
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getStrClaveSucursal() {
        return strClaveSucursal;
    }

    public void setStrClaveSucursal(String strClaveSucursal) {
        this.strClaveSucursal = strClaveSucursal;
    }

    public String getStrSucursal() {
        return strSucursal;
    }

    public void setStrSucursal(String strSucursal) {
        this.strSucursal = strSucursal;
    }
}
