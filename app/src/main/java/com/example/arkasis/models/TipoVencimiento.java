package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class TipoVencimiento {
    private int idTipoVencimiento;
    private String strTipoVencimiento;
    private int idSucursal;
    private int intNumDias;

    public TipoVencimiento(int idTipoVencimiento, String strTipoVencimiento, int idSucursal, int intNumDias) {
        this.idTipoVencimiento = idTipoVencimiento;
        this.strTipoVencimiento = strTipoVencimiento;
        this.idSucursal = idSucursal;
        this.intNumDias = intNumDias;
    }

    public TipoVencimiento(LinkedTreeMap<Object, Object> treeMap) {
        this.idTipoVencimiento = Integer.parseInt(treeMap.get("idTipoVencimiento").toString().trim());
        this.strTipoVencimiento = treeMap.get("strTipoVencimiento").toString().trim();
        this.idSucursal = Integer.parseInt(treeMap.get("idSucursal").toString().trim());
        this.intNumDias = ((Double)treeMap.get("intNumDias")).intValue();
    }

    public int getIdTipoVencimiento() {
        return idTipoVencimiento;
    }

    public void setIdTipoVencimiento(int idTipoVencimiento) {
        this.idTipoVencimiento = idTipoVencimiento;
    }

    public String getStrTipoVencimiento() {
        return strTipoVencimiento;
    }

    public void setStrTipoVencimiento(String strTipoVencimiento) {
        this.strTipoVencimiento = strTipoVencimiento;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIntNumDias() {
        return intNumDias;
    }

    public void setIntNumDias(int intNumDias) {
        this.intNumDias = intNumDias;
    }
}
