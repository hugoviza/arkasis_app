package com.example.arkasis.models;

public class EstadoCivil {
    private int idEstadoCivil;
    private String strEstadoCivil;

    public EstadoCivil(int idEstadoCivil, String strEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
        this.strEstadoCivil = strEstadoCivil;
    }

    public int getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public String getStrEstadoCivil() {
        return strEstadoCivil;
    }

    public void setStrEstadoCivil(String strEstadoCivil) {
        this.strEstadoCivil = strEstadoCivil;
    }
}
