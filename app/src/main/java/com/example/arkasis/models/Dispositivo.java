package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Dispositivo {
    private String idSucursal;
    private int idDispositivo;
    private String plataforma;
    private String UUIDDispositivo;
    private String fechaHoraRegistroUUIDDispositivo;
    private String tokenActivacion;
    private String fechaHoraRegistroTokenActivacion;
    private int estatusTokenAcivacion;
    private String usuarioAlta;
    private String fechaHoraAlta;
    private String usuarioModificacion;
    private String fechaHoraModificacion;

    public Dispositivo() {

    }

    public Dispositivo(LinkedTreeMap<Object, Object> treeMap) {
        this.idSucursal = treeMap.get("idSucursal").toString().trim();
        this.idDispositivo = (int)Double.parseDouble(treeMap.get("idDispositivo").toString().trim());
        this.plataforma = treeMap.get("plataforma").toString().trim();
        this.UUIDDispositivo = treeMap.get("uuidDispositivo").toString().trim();
        this.fechaHoraRegistroUUIDDispositivo = treeMap.get("fechaHoraRegistroUUIDDispositivo").toString().trim();
        this.tokenActivacion = treeMap.get("tokenActivacion").toString().trim();
        this.fechaHoraRegistroTokenActivacion = treeMap.get("fechaHoraRegistroTokenActivacion").toString().trim();
        this.estatusTokenAcivacion = (int)Double.parseDouble(treeMap.get("estatusTokenAcivacion").toString().trim());
        this.usuarioAlta = treeMap.get("usuarioAlta").toString().trim();
        this.fechaHoraAlta = treeMap.get("fechaHoraAlta").toString().trim();
        this.usuarioModificacion = treeMap.get("usuarioModificacion").toString().trim();
        this.fechaHoraModificacion = treeMap.get("fechaHoraModificacion").toString().trim();
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getUUIDDispositivo() {
        return UUIDDispositivo;
    }

    public void setUUIDDispositivo(String UUIDDispositivo) {
        this.UUIDDispositivo = UUIDDispositivo;
    }

    public String getFechaHoraRegistroUUIDDispositivo() {
        return fechaHoraRegistroUUIDDispositivo;
    }

    public void setFechaHoraRegistroUUIDDispositivo(String fechaHoraRegistroUUIDDispositivo) {
        this.fechaHoraRegistroUUIDDispositivo = fechaHoraRegistroUUIDDispositivo;
    }

    public String getTokenActivacion() {
        return tokenActivacion;
    }

    public void setTokenActivacion(String tokenActivacion) {
        this.tokenActivacion = tokenActivacion;
    }

    public String getFechaHoraRegistroTokenActivacion() {
        return fechaHoraRegistroTokenActivacion;
    }

    public void setFechaHoraRegistroTokenActivacion(String fechaHoraRegistroTokenActivacion) {
        this.fechaHoraRegistroTokenActivacion = fechaHoraRegistroTokenActivacion;
    }

    public int getEstatusTokenAcivacion() {
        return estatusTokenAcivacion;
    }

    public void setEstatusTokenAcivacion(int estatusTokenAcivacion) {
        this.estatusTokenAcivacion = estatusTokenAcivacion;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getFechaHoraAlta() {
        return fechaHoraAlta;
    }

    public void setFechaHoraAlta(String fechaHoraAlta) {
        this.fechaHoraAlta = fechaHoraAlta;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(String fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }
}
