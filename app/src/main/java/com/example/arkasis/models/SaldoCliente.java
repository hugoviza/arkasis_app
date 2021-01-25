package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class SaldoCliente {
    private String strCurp = "";
    private String strFolioContrato = "";
    private String datFechaMinistracion = "";
    private String datFechaVencimiento = "";
    private String intTotalPagos = "";
    private String dblCapital = "";
    private String dblIntereses = "";
    private String dblSeguro = "";
    private String dblTotal = "";
    private String dblAbono = "";
    private String dblSaldo = "";
    private String strProducto = "";

    public SaldoCliente(String strCurp, String strFolioContrato, String datFechaMinistracion, String datFechaVencimiento, String intTotalPagos, String dblCapital, String dblIntereses, String dblSeguro, String dblTotal, String dblAbono, String dblSaldo, String strProducto) {
        this.strCurp = strCurp;
        this.strFolioContrato = strFolioContrato;
        this.datFechaMinistracion = datFechaMinistracion;
        this.datFechaVencimiento = datFechaVencimiento;
        this.intTotalPagos = intTotalPagos;
        this.dblCapital = dblCapital;
        this.dblIntereses = dblIntereses;
        this.dblSeguro = dblSeguro;
        this.dblTotal = dblTotal;
        this.dblAbono = dblAbono;
        this.dblSaldo = dblSaldo;
        this.strProducto = strProducto;
    }

    public SaldoCliente() {

    }

    public SaldoCliente(LinkedTreeMap<Object, Object> treeMap) {
        this.strCurp = treeMap.get("strCurp").toString().trim();
        this.strFolioContrato = treeMap.get("strFolioContrato").toString().trim();
        this.datFechaMinistracion = treeMap.get("datFechaMinistracion").toString().trim();
        this.datFechaVencimiento = treeMap.get("datFechaVencimiento").toString().trim();
        this.intTotalPagos = treeMap.get("intTotalPagos").toString().trim();
        this.dblCapital = treeMap.get("dblCapital").toString().trim();
        this.dblIntereses = treeMap.get("dblIntereses").toString().trim();
        this.dblSeguro = treeMap.get("dblSeguro").toString().trim();
        this.dblTotal = treeMap.get("dblTotal").toString().trim();
        this.dblAbono = treeMap.get("dblAbono").toString().trim();
        this.dblSaldo = treeMap.get("dblSaldo").toString().trim();
        this.strProducto = treeMap.get("strProducto").toString().trim();
    }

    public String getStrCurp() {
        return strCurp;
    }

    public void setStrCurp(String strCurp) {
        this.strCurp = strCurp;
    }

    public String getStrFolioContrato() {
        return strFolioContrato;
    }

    public void setStrFolioContrato(String strFolioContrato) {
        this.strFolioContrato = strFolioContrato;
    }

    public String getDatFechaMinistracion() {
        return datFechaMinistracion;
    }

    public void setDatFechaMinistracion(String datFechaMinistracion) {
        this.datFechaMinistracion = datFechaMinistracion;
    }

    public String getDatFechaVencimiento() {
        return datFechaVencimiento;
    }

    public void setDatFechaVencimiento(String datFechaVencimiento) {
        this.datFechaVencimiento = datFechaVencimiento;
    }

    public String getIntTotalPagos() {
        return intTotalPagos;
    }

    public void setIntTotalPagos(String intTotalPagos) {
        this.intTotalPagos = intTotalPagos;
    }

    public String getDblCapital() {
        return dblCapital;
    }

    public void setDblCapital(String dblCapital) {
        this.dblCapital = dblCapital;
    }

    public String getDblIntereses() {
        return dblIntereses;
    }

    public void setDblIntereses(String dblIntereses) {
        this.dblIntereses = dblIntereses;
    }

    public String getDblSeguro() {
        return dblSeguro;
    }

    public void setDblSeguro(String dblSeguro) {
        this.dblSeguro = dblSeguro;
    }

    public String getDblTotal() {
        return dblTotal;
    }

    public void setDblTotal(String dblTotal) {
        this.dblTotal = dblTotal;
    }

    public String getDblAbono() {
        return dblAbono;
    }

    public void setDblAbono(String dblAbono) {
        this.dblAbono = dblAbono;
    }

    public String getDblSaldo() {
        return dblSaldo;
    }

    public void setDblSaldo(String dblSaldo) {
        this.dblSaldo = dblSaldo;
    }

    public String getStrProducto() {
        return strProducto;
    }

    public void setStrProducto(String strProducto) {
        this.strProducto = strProducto;
    }
}
