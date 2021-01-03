package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Actividad {
    private int idActividad;
    private String strActividad;
    private String strCNBV;

    public Actividad(int idActividad, String strActividad, String strCNBV) {
        this.idActividad = idActividad;
        this.strActividad = strActividad;
        this.strCNBV = strCNBV;
    }

    public Actividad() {
    }

    public Actividad(LinkedTreeMap<Object, Object> treeMap) {
        this.idActividad = Integer.parseInt(treeMap.get("idActividad").toString().trim());
        this.strActividad = treeMap.get("strActividad").toString().trim();
        this.strCNBV = treeMap.get("strCNBV").toString().trim();
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getStrActividad() {
        return strActividad;
    }

    public void setStrActividad(String strActividad) {
        this.strActividad = strActividad;
    }

    public String getStrCNBV() {
        return strCNBV;
    }

    public void setStrCNBV(String strCNBV) {
        this.strCNBV = strCNBV;
    }
}
