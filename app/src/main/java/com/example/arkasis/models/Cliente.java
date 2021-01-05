package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.util.TreeMap;

public class Cliente implements Serializable {

    private String idCliente = "";
    private String strGenero = "";
    private String strCurp = "";
    private String strApellidoPaterno = "";
    private String strApellidoMaterno = "";
    private String strNombre1 = "";
    private String strNombre2 = "";
    private String datFechaNacimiento = "";
    private String strEdoCivil = "";
    private String idEdoCivil = "";
    private String strTelefono = "";
    private String strCelular = "";
    private String strCodigoPostal = "";
    private String strDireccion = "";
    private String strDireccionNumero = "";
    private String strDireccionNumeroInterno = "";
    private String strColonia = "";
    private String idEstado = "";
    private String strEstado = "";
    private String idMunicipio = "";
    private String strMunicipio = "";
    private String strClaveGrupo = "";
    private String idActividad = "";
    private String strDescripcionActividad = "";
    private String strNumeroElector = "";
    private String strClaveElector = "";
    private String strPaisNacimiento = "";
    private String strEstadoNacimiento = "";
    private String strNacionalidad = "";
    private String strEmail = "";
    private String strNombreConyugue = "";
    private String datFechaNacimientoConyugue = "";
    private String strLugarNacimientoConyugue = "";
    private String strOcupacion = "";

    public Cliente() {
    }

    public Cliente(LinkedTreeMap<Object, Object> treeMap) {
        this.idCliente = treeMap.get("idCliente").toString().trim();
        this.strGenero = treeMap.get("strGenero").toString().trim();
        this.strCurp = treeMap.get("strCurp").toString().trim();
        this.strApellidoPaterno = treeMap.get("strApellidoPaterno").toString().trim();
        this.strApellidoMaterno = treeMap.get("strApellidoMaterno").toString().trim();
        this.strNombre1 = treeMap.get("strNombre1").toString().trim();
        this.strNombre2 = treeMap.get("strNombre2").toString().trim();
        this.datFechaNacimiento = treeMap.get("datFechaNacimiento").toString().trim();
        this.strEdoCivil = treeMap.get("strEdoCivil").toString().trim();
        this.idEdoCivil = treeMap.get("idEdoCivil").toString().trim();
        this.strTelefono = treeMap.get("strTelefono").toString().trim();
        this.strCelular = treeMap.get("strCelular").toString().trim();
        this.strCodigoPostal = treeMap.get("strCodigoPostal").toString().trim();
        this.strDireccion = treeMap.get("strDireccion").toString().trim();
        this.strDireccionNumero = treeMap.get("strDireccionNumero").toString().trim();
        this.strDireccionNumeroInterno = treeMap.get("strDireccionNumeroInterno").toString().trim();
        this.strColonia = treeMap.get("strColonia").toString().trim();
        this.idEstado = treeMap.get("idEstado").toString().trim();
        this.strEstado = treeMap.get("strEstado").toString().trim();
        this.idMunicipio = treeMap.get("idMunicipio").toString().trim();
        this.strMunicipio = treeMap.get("strMunicipio").toString().trim();
        this.strClaveGrupo = treeMap.get("strClaveGrupo").toString().trim();
        this.idActividad = treeMap.get("idActividad").toString().trim();
        this.strDescripcionActividad = treeMap.get("strDescripcionActividad").toString().trim();
        this.strNumeroElector = treeMap.get("strNumeroElector").toString().trim();
        this.strClaveElector = treeMap.get("strClaveElector").toString().trim();
        this.strPaisNacimiento = treeMap.get("strPaisNacimiento").toString().trim();
        this.strEstadoNacimiento = treeMap.get("strEstadoNacimiento").toString().trim();
        this.strNacionalidad = treeMap.get("strNacionalidad").toString().trim();
        this.strEmail = treeMap.get("strEmail").toString().trim();
        this.strNombreConyugue = treeMap.get("strNombreConyugue").toString().trim();
        this.datFechaNacimientoConyugue = treeMap.get("datFechaNacimientoConyugue").toString().trim();
        this.strLugarNacimientoConyugue = treeMap.get("strLugarNacimientoConyugue").toString().trim();
        this.strOcupacion = treeMap.get("strOcupacion").toString().trim();
    }
    //Custom
    public String getMunicipioResidencia() {
        return strMunicipio.trim() + ", " + strEstado.trim();
    }

    public String getStrNombreCompleto() {
        return (strNombre1.trim()+" "+strNombre2.trim()).trim() +" "+strApellidoPaterno.trim()+" "+strApellidoMaterno.trim();
    }

    public String getStrDomicilio() {
        String direccion = strDireccion.trim()+(strDireccionNumero.trim() != "" ? " #"+strDireccionNumero.trim() : "")+" "+strDireccionNumeroInterno.trim();
        return (direccion.trim() != "" ? direccion.trim()+", " : "")+strColonia+" "+strMunicipio+", "+strEstado ;
    }

    public String getStrLugarNacimiento() {
        return (strEstadoNacimiento.trim() != "" ? strEstadoNacimiento.trim()+", " : "")+strPaisNacimiento.trim();
    }

    //Default
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getStrGenero() {
        return strGenero;
    }

    public void setStrGenero(String strGenero) {
        this.strGenero = strGenero;
    }

    public String getStrCurp() {
        return strCurp;
    }

    public void setStrCurp(String strCurp) {
        this.strCurp = strCurp;
    }

    public String getStrApellidoPaterno() {
        return strApellidoPaterno;
    }

    public void setStrApellidoPaterno(String strApellidoPaterno) {
        this.strApellidoPaterno = strApellidoPaterno;
    }

    public String getStrApellidoMaterno() {
        return strApellidoMaterno;
    }

    public void setStrApellidoMaterno(String strApellidoMaterno) {
        this.strApellidoMaterno = strApellidoMaterno;
    }

    public String getStrNombre1() {
        return strNombre1;
    }

    public void setStrNombre1(String strNombre1) {
        this.strNombre1 = strNombre1;
    }

    public String getStrNombre2() {
        return strNombre2;
    }

    public void setStrNombre2(String strNombre2) {
        this.strNombre2 = strNombre2;
    }

    public String getDatFechaNacimiento() {
        return datFechaNacimiento;
    }

    public void setDatFechaNacimiento(String datFechaNacimiento) {
        this.datFechaNacimiento = datFechaNacimiento;
    }

    public String getStrEdoCivil() {
        return strEdoCivil;
    }

    public void setStrEdoCivil(String strEdoCivil) {
        this.strEdoCivil = strEdoCivil;
    }

    public String getIdEdoCivil() {
        return idEdoCivil;
    }

    public void setIdEdoCivil(String idEdoCivil) {
        this.idEdoCivil = idEdoCivil;
    }

    public String getStrTelefono() {
        return strTelefono;
    }

    public void setStrTelefono(String strTelefono) {
        this.strTelefono = strTelefono;
    }

    public String getStrCelular() {
        return strCelular;
    }

    public void setStrCelular(String strCelular) {
        this.strCelular = strCelular;
    }

    public String getStrCodigoPostal() {
        return strCodigoPostal;
    }

    public void setStrCodigoPostal(String strCodigoPostal) {
        this.strCodigoPostal = strCodigoPostal;
    }

    public String getStrDireccion() {
        return strDireccion;
    }

    public void setStrDireccion(String strDireccion) {
        this.strDireccion = strDireccion;
    }

    public String getStrDireccionNumero() {
        return strDireccionNumero;
    }

    public void setStrDireccionNumero(String strDireccionNumero) {
        this.strDireccionNumero = strDireccionNumero;
    }

    public String getStrDireccionNumeroInterno() {
        return strDireccionNumeroInterno;
    }

    public void setStrDireccionNumeroInterno(String strDireccionNumeroInterno) {
        this.strDireccionNumeroInterno = strDireccionNumeroInterno;
    }

    public String getStrColonia() {
        return strColonia;
    }

    public void setStrColonia(String strColonia) {
        this.strColonia = strColonia;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getStrEstado() {
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
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
        this.strMunicipio = strMunicipio;
    }

    public String getStrClaveGrupo() {
        return strClaveGrupo;
    }

    public void setStrClaveGrupo(String strClaveGrupo) {
        this.strClaveGrupo = strClaveGrupo;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getStrDescripcionActividad() {
        return strDescripcionActividad;
    }

    public void setStrDescripcionActividad(String strDescripcionActividad) {
        this.strDescripcionActividad = strDescripcionActividad;
    }

    public String getStrNumeroElector() {
        return strNumeroElector;
    }

    public void setStrNumeroElector(String strNumeroElector) {
        this.strNumeroElector = strNumeroElector;
    }

    public String getStrClaveElector() {
        return strClaveElector;
    }

    public void setStrClaveElector(String strClaveElector) {
        this.strClaveElector = strClaveElector;
    }

    public String getStrPaisNacimiento() {
        return strPaisNacimiento;
    }

    public void setStrPaisNacimiento(String strPaisNacimiento) {
        this.strPaisNacimiento = strPaisNacimiento;
    }

    public String getStrEstadoNacimiento() {
        return strEstadoNacimiento;
    }

    public void setStrEstadoNacimiento(String strEstadoNacimiento) {
        this.strEstadoNacimiento = strEstadoNacimiento;
    }

    public String getStrNacionalidad() {
        return strNacionalidad;
    }

    public void setStrNacionalidad(String strNacionalidad) {
        this.strNacionalidad = strNacionalidad;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrNombreConyugue() {
        return strNombreConyugue;
    }

    public void setStrNombreConyugue(String strNombreConyugue) {
        this.strNombreConyugue = strNombreConyugue;
    }

    public String getDatFechaNacimientoConyugue() {
        return datFechaNacimientoConyugue;
    }

    public void setDatFechaNacimientoConyugue(String datFechaNacimientoConyugue) {
        this.datFechaNacimientoConyugue = datFechaNacimientoConyugue;
    }

    public String getStrLugarNacimientoConyugue() {
        return strLugarNacimientoConyugue;
    }

    public void setStrLugarNacimientoConyugue(String strLugarNacimientoConyugue) {
        this.strLugarNacimientoConyugue = strLugarNacimientoConyugue;
    }

    public String getStrOcupacion() {
        return strOcupacion;
    }

    public void setStrOcupacion(String strOcupacion) {
        this.strOcupacion = strOcupacion;
    }
}
