package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class ResumenSolicitudes {
    private int intTotalTramite = 0;
    private int intTotalAutorizado = 0;
    private int intTotalRechazado = 0;
    private int intTotalCancelado = 0;
    private int intTotalMinistrado = 0;
    private int intTotalRegistros = 0;

    public ResumenSolicitudes(LinkedTreeMap<Object, Object> treeMap) {
        this.intTotalTramite = ((Double)treeMap.get("intTotalTramite")).intValue();
        this.intTotalAutorizado = ((Double)treeMap.get("intTotalAutorizado")).intValue();
        this.intTotalRechazado = ((Double)treeMap.get("intTotalRechazado")).intValue();
        this.intTotalCancelado = ((Double)treeMap.get("intTotalCancelado")).intValue();
        this.intTotalMinistrado = ((Double)treeMap.get("intTotalMinistrado")).intValue();
        this.intTotalRegistros = ((Double)treeMap.get("intTotalRegistros")).intValue();
    }

    public ResumenSolicitudes() {
    }

    public int getPorcentajeTramite() {
        return intTotalTramite * 100 / intTotalRegistros;
    }

    public int getPorcentajeAutorizado() {
        return intTotalAutorizado * 100 / intTotalRegistros;
    }
    public int getPorcentajeRechazado() {
        return intTotalRechazado * 100 / intTotalRegistros;
    }

    public int getPorcentajeCancelado() {
        return intTotalCancelado * 100 / intTotalRegistros;
    }

    public int getPorcentajeMinistrado() {
        return intTotalMinistrado * 100 / intTotalRegistros;
    }

    public int getIntTotalTramite() {
        return intTotalTramite;
    }

    public void setIntTotalTramite(int intTotalTramite) {
        this.intTotalTramite = intTotalTramite;
    }

    public int getIntTotalAutorizado() {
        return intTotalAutorizado;
    }

    public void setIntTotalAutorizado(int intTotalAutorizado) {
        this.intTotalAutorizado = intTotalAutorizado;
    }

    public int getIntTotalRechazado() {
        return intTotalRechazado;
    }

    public void setIntTotalRechazado(int intTotalRechazado) {
        this.intTotalRechazado = intTotalRechazado;
    }

    public int getIntTotalCancelado() {
        return intTotalCancelado;
    }

    public void setIntTotalCancelado(int intTotalCancelado) {
        this.intTotalCancelado = intTotalCancelado;
    }

    public int getIntTotalMinistrado() {
        return intTotalMinistrado;
    }

    public void setIntTotalMinistrado(int intTotalMinistrado) {
        this.intTotalMinistrado = intTotalMinistrado;
    }

    public int getIntTotalRegistros() {
        return intTotalRegistros;
    }

    public void setIntTotalRegistros(int intTotalRegistros) {
        this.intTotalRegistros = intTotalRegistros;
    }
}
