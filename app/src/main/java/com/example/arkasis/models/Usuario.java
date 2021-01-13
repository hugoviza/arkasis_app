package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class Usuario {
    private String user;
    private String password;
    private String nombre;

    public Usuario() {

    }

    public Usuario(LinkedTreeMap<Object, Object> treeMap) {
        this.user = treeMap.get("user").toString().trim();
        this.password = treeMap.get("password").toString().trim();
        this.nombre = treeMap.get("nombre").toString().trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
