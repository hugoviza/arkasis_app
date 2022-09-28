package com.example.arkasis.models;

import com.google.gson.internal.LinkedTreeMap;

public class SolicitudDispersion {

    private int idSolicitud;
    private String strFechaAlta = "";
    private String strStatusSolicitud = "";
    private String idSucursal = "";
    private String strUsuario = "";
    private String idPromotor = "";
    private String strPromotor = "";
    private String strCordinador = "";
    private String idCliente = "";
    private String strApellidoPaterno = "";
    private String strApellidoMaterno = "";
    private String strNombre1 = "";
    private String strNombre2 = "";
    private String strFechaNacimiento = "";
    private String idGenero = "";
    private String strGenero = "";
    private String strCURP = "";
    private String strDomicilio = "";
    private String strDomicilioCodigoPostal = "";
    private String strDomicilioNumExt = "";
    private String strDomicilioNumInt = "";
    private String strDomicilioColonia = "";
    private String idDomicilioEstado = "";
    private String strDomicilioEstado = "";
    private String idDomicilioMunicipio = "";
    private String strDomicilioMunicipio = "";
    private String strEstadoCivil = "";
    private String idEstadoCivil = "";
    private String strTelefono = "";
    private String strCelular = "";
    private String strOcupacion = "";
    private String idActividad = "";
    private String strActividad = "";
    private String strCNBV = "";
    private String strNumeroINE = "";
    private String strClaveINE = "";
    private String strPais = "";
    private String strEstadoNacimiento = "";
    private String strNacionalidad = "";
    private String strEmail = "";
    private String strNombreConyuge = "";
    private String strLugarNacimientoConyuge = "";
    private String strFechaNacimientoConyuge = "";
    private String strOcupacionConyuge = "";
    private String strReferenciaBancaria = "";
    private String strBanco = "";
    private String strProducto = "";
    private int intPlazo;
    private int intQuedateCasa;
    private double dblMontoSolicitadoMejoraVivienda;
    private double dblMontoSolicitadoEquipandoHogar;
    private double dblIngresos = 0;
    private double dblEgresos = 0;
    private String strEstatusInserccionServidor = "";
    private int idTipoVencimiento = 0;
    private String strTipoVencimiento = "";
    private String idTipoContratoIndividual = "";
    private int intNumPagos = 0;

    private String strDomicilio_mejoraVivienda = "";
    private String strCodigoPostal_mejoraVivienda = "";
    private String strNumExt_mejoraVivienda = "";
    private String strNumInt_mejoraVivienda = "";
    private String strColonia_mejoraVivienda = "";
    private String idEstado_mejoraVivienda = "";
    private String strEstado_mejoraVivienda = "";
    private String idMunicipio_mejoraVivienda = "";
    private String strMunicipio_mejoraVivienda = "";

    private String strFotoINEFrontal_B64;
    private String strFotoINEFrontal_nombre;
    private String strFotoINEReverso_B64;
    private String strFotoINEReverso_nombre;
    private String strFotoPerfil_B64;
    private String strFotoPerfil_nombre;
    private String strFotoComprobanteDomicilio_B64;
    private String strFotoComprobanteDomicilio_nombre;

    public SolicitudDispersion() {
    }

    public SolicitudDispersion(int idSolicitud, String strFechaAlta, String strStatusSolicitud, String idSucursal, String strUsuario, String idPromotor, String strPromotor, String strCordinador, String idCliente, String strApellidoPaterno, String strApellidoMaterno, String strNombre1, String strNombre2, String strFechaNacimiento, String idGenero, String strGenero, String strCURP, String strDomicilio, String strDomicilioCodigoPostal, String strDomicilioNumExt, String strDomicilioNumInt, String strDomicilioColonia, String idDomicilioEstado, String strDomicilioEstado, String idDomicilioMunicipio, String strDomicilioMunicipio, String strEstadoCivil, String idEstadoCivil, String strTelefono, String strCelular, String strOcupacion, String idActividad, String strActividad, String strNumeroINE, String strClaveINE, String strPais, String strEstadoNacimiento, String strNacionalidad, String strEmail, String strNombreConyuge, String strLugarNacimientoConyuge, String strFechaNacimientoConyuge, String strOcupacionConyuge, String strReferenciaBancaria, String strBanco, String strProducto, int intPlazo, int intQuedateCasa, double dblMontoSolicitadoMejoraVivienda, double dblMontoSolicitadoEquipandoHogar, double dblIngresos, double dblEgresos, String strEstatusInserccionServidor, String strCNBV, String strDomicilio_mejoraVivienda, String strCodigoPostal_mejoraVivienda, String strNumExt_mejoraVivienda, String strNumInt_mejoraVivienda, String strColonia_mejoraVivienda, String idEstado_mejoraVivienda, String strEstado_mejoraVivienda, String idMunicipio_mejoraVivienda, String strMunicipio_mejoraVivienda, String strFotoINEFrontal_B64, String strFotoINEFrontal_nombre, String strFotoINEReverso_B64, String strFotoINEReverso_nombre, String strFotoPerfil_B64, String strFotoPerfil_nombre, String strFotoComprobanteDomicilio_B64, String strFotoComprobanteDomicilio_nombre, int idTipoVencimiento, String strTipoVencimiento, String idTipoContratoIndividual, int intNumPagos) {
        this.idSolicitud = idSolicitud;
        this.strFechaAlta = strFechaAlta;
        this.strStatusSolicitud = strStatusSolicitud;
        this.idSucursal = idSucursal;
        this.strUsuario = strUsuario;
        this.idPromotor = idPromotor;
        this.strPromotor = strPromotor;
        this.strCordinador = strCordinador;
        this.idCliente = idCliente;
        this.strApellidoPaterno = strApellidoPaterno;
        this.strApellidoMaterno = strApellidoMaterno;
        this.strNombre1 = strNombre1;
        this.strNombre2 = strNombre2;
        this.strFechaNacimiento = strFechaNacimiento;
        this.idGenero = idGenero;
        this.strGenero = strGenero;
        this.strCURP = strCURP;
        this.strDomicilio = strDomicilio;
        this.strDomicilioCodigoPostal = strDomicilioCodigoPostal;
        this.strDomicilioNumExt = strDomicilioNumExt;
        this.strDomicilioNumInt = strDomicilioNumInt;
        this.strDomicilioColonia = strDomicilioColonia;
        this.idDomicilioEstado = idDomicilioEstado;
        this.strDomicilioEstado = strDomicilioEstado;
        this.idDomicilioMunicipio = idDomicilioMunicipio;
        this.strDomicilioMunicipio = strDomicilioMunicipio;
        this.strEstadoCivil = strEstadoCivil;
        this.idEstadoCivil = idEstadoCivil;
        this.strTelefono = strTelefono;
        this.strCelular = strCelular;
        this.strOcupacion = strOcupacion;
        this.idActividad = idActividad;
        this.strActividad = strActividad;
        this.strNumeroINE = strNumeroINE;
        this.strClaveINE = strClaveINE;
        this.strPais = strPais;
        this.strEstadoNacimiento = strEstadoNacimiento;
        this.strNacionalidad = strNacionalidad;
        this.strEmail = strEmail;
        this.strNombreConyuge = strNombreConyuge;
        this.strLugarNacimientoConyuge = strLugarNacimientoConyuge;
        this.strFechaNacimientoConyuge = strFechaNacimientoConyuge;
        this.strOcupacionConyuge = strOcupacionConyuge;
        this.strReferenciaBancaria = strReferenciaBancaria;
        this.strBanco = strBanco;
        this.strProducto = strProducto;
        this.intPlazo = intPlazo;
        this.intQuedateCasa = intQuedateCasa;
        this.dblMontoSolicitadoMejoraVivienda = dblMontoSolicitadoMejoraVivienda;
        this.dblMontoSolicitadoEquipandoHogar = dblMontoSolicitadoEquipandoHogar;
        this.dblIngresos = dblIngresos;
        this.dblEgresos = dblEgresos;
        this.strCNBV = strCNBV;
        this.idTipoVencimiento = idTipoVencimiento;
        this.strTipoVencimiento = strTipoVencimiento;
        this.idTipoContratoIndividual = idTipoContratoIndividual;
        this.intNumPagos = intNumPagos;

        this.strEstatusInserccionServidor = strEstatusInserccionServidor;
        this.strDomicilio_mejoraVivienda = strDomicilio_mejoraVivienda;
        this.strCodigoPostal_mejoraVivienda = strCodigoPostal_mejoraVivienda;
        this.strNumExt_mejoraVivienda = strNumExt_mejoraVivienda;
        this.strNumInt_mejoraVivienda = strNumInt_mejoraVivienda;
        this.strColonia_mejoraVivienda = strColonia_mejoraVivienda;
        this.idEstado_mejoraVivienda = idEstado_mejoraVivienda;
        this.strEstado_mejoraVivienda = strEstado_mejoraVivienda;
        this.idMunicipio_mejoraVivienda = idMunicipio_mejoraVivienda;
        this.strMunicipio_mejoraVivienda = strMunicipio_mejoraVivienda;

        this.strFotoINEFrontal_B64 = strFotoINEFrontal_B64;
        this.strFotoINEFrontal_nombre = strFotoINEFrontal_nombre;
        this.strFotoINEReverso_B64 = strFotoINEReverso_B64;
        this.strFotoINEReverso_nombre = strFotoINEReverso_nombre;
        this.strFotoPerfil_B64 = strFotoPerfil_B64;
        this.strFotoPerfil_nombre = strFotoPerfil_nombre;
        this.strFotoComprobanteDomicilio_B64 = strFotoComprobanteDomicilio_B64;
        this.strFotoComprobanteDomicilio_nombre = strFotoComprobanteDomicilio_nombre;
    }

    public SolicitudDispersion(LinkedTreeMap<Object, Object> treeMap) {
        this.idSolicitud = (int)Double.parseDouble(treeMap.get("idSolicitud").toString().trim());
        this.strFechaAlta = treeMap.get("strFechaAlta").toString().trim();
        this.strStatusSolicitud = treeMap.get("strStatusSolicitud").toString().trim();
        this.idSucursal = treeMap.get("idSucursal").toString().trim();
        this.strUsuario = treeMap.get("strUsuario").toString().trim();
        this.idPromotor = treeMap.get("idPromotor").toString().trim();
        this.strPromotor = treeMap.get("strPromotor").toString().trim();
        this.strCordinador = treeMap.get("strCordinador").toString().trim();
        this.idCliente = treeMap.get("idCliente").toString().trim();
        this.strApellidoPaterno = treeMap.get("strApellidoPaterno").toString().trim();
        this.strApellidoMaterno = treeMap.get("strApellidoMaterno").toString().trim();
        this.strNombre1 = treeMap.get("strNombre1").toString().trim();
        this.strNombre2 = treeMap.get("strNombre2").toString().trim();
        this.strFechaNacimiento = treeMap.get("strFechaNacimiento").toString().trim();
        this.idGenero = treeMap.get("idGenero").toString().trim();
        this.strGenero = treeMap.get("strGenero").toString().trim();
        this.strCURP = treeMap.get("strCURP").toString().trim();
        this.strDomicilio = treeMap.get("strDomicilio").toString().trim();
        this.strDomicilioCodigoPostal = treeMap.get("strDomicilioCodigoPostal").toString().trim();
        this.strDomicilioNumExt = treeMap.get("strDomicilioNumExt").toString().trim();
        this.strDomicilioNumInt = treeMap.get("strDomicilioNumInt").toString().trim();
        this.strDomicilioColonia = treeMap.get("strDomicilioColonia").toString().trim();
        this.idDomicilioEstado = treeMap.get("idDomicilioEstado").toString().trim();
        this.strDomicilioEstado = treeMap.get("strDomicilioEstado").toString().trim();
        this.idDomicilioMunicipio = treeMap.get("idDomicilioMunicipio").toString().trim();
        this.strDomicilioMunicipio = treeMap.get("strDomicilioMunicipio").toString().trim();
        this.strEstadoCivil = treeMap.get("strEstadoCivil").toString().trim();
        this.idEstadoCivil = treeMap.get("idEstadoCivil").toString().trim();
        this.strTelefono = treeMap.get("strTelefono").toString().trim();
        this.strCelular = treeMap.get("strCelular").toString().trim();
        this.strOcupacion = treeMap.get("strOcupacion").toString().trim();
        this.idActividad = treeMap.get("idActividad").toString().trim();
        this.strActividad = treeMap.get("strActividad").toString().trim();
        this.strNumeroINE = treeMap.get("strNumeroINE").toString().trim();
        this.strClaveINE = treeMap.get("strClaveINE").toString().trim();
        this.strPais = treeMap.get("strPais").toString().trim();
        this.strEstadoNacimiento = treeMap.get("strEstadoNacimiento").toString().trim();
        this.strNacionalidad = treeMap.get("strNacionalidad").toString().trim();
        this.strEmail = treeMap.get("strEmail").toString().trim();
        this.strNombreConyuge = treeMap.get("strNombreConyuge").toString().trim();
        this.strLugarNacimientoConyuge = treeMap.get("strLugarNacimientoConyuge").toString().trim();
        this.strFechaNacimientoConyuge = treeMap.get("strFechaNacimientoConyuge").toString().trim();
        this.strOcupacionConyuge = treeMap.get("strOcupacionConyuge").toString().trim();
        this.strReferenciaBancaria = treeMap.get("strReferenciaBancaria").toString().trim();
        this.strBanco = treeMap.get("strBanco").toString().trim();
        this.strProducto = treeMap.get("strProducto").toString().trim();
        this.intPlazo = (int)Double.parseDouble(treeMap.get("intPlazo").toString().trim());
        this.intQuedateCasa = (int)Double.parseDouble(treeMap.get("intQuedateCasa").toString().trim());
        this.dblMontoSolicitadoMejoraVivienda = Double.parseDouble(treeMap.get("dblMontoSolicitadoMejoraVivienda").toString().trim());
        this.dblMontoSolicitadoEquipandoHogar = Double.parseDouble(treeMap.get("dblMontoSolicitadoEquipandoHogar").toString().trim());
        this.dblIngresos = Double.parseDouble(treeMap.get("dblIngresos").toString().trim());
        this.dblEgresos = Double.parseDouble(treeMap.get("dblEgresos").toString().trim());
        this.strCNBV = treeMap.get("strCNBV").toString().trim();
        this.strDomicilio_mejoraVivienda = treeMap.get("strDomicilio_mejoraVivienda").toString().trim();
        this.strCodigoPostal_mejoraVivienda = treeMap.get("strCodigoPostal_mejoraVivienda").toString().trim();
        this.strNumExt_mejoraVivienda = treeMap.get("strNumExt_mejoraVivienda").toString().trim();
        this.strNumInt_mejoraVivienda = treeMap.get("strNumInt_mejoraVivienda").toString().trim();
        this.strColonia_mejoraVivienda = treeMap.get("strColonia_mejoraVivienda").toString().trim();
        this.idEstado_mejoraVivienda = treeMap.get("idEstado_mejoraVivienda").toString().trim();
        this.strEstado_mejoraVivienda = treeMap.get("strEstado_mejoraVivienda").toString().trim();
        this.idMunicipio_mejoraVivienda = treeMap.get("idMunicipio_mejoraVivienda").toString().trim();
        this.strMunicipio_mejoraVivienda = treeMap.get("strMunicipio_mejoraVivienda").toString().trim();
        this.strFotoINEFrontal_B64 = treeMap.get("strFotoINEFrontal_B64").toString().trim();
        this.strFotoINEFrontal_nombre = treeMap.get("strFotoINEFrontal_nombre").toString().trim();
        this.strFotoINEReverso_B64 = treeMap.get("strFotoINEReverso_B64").toString().trim();
        this.strFotoINEReverso_nombre = treeMap.get("strFotoINEReverso_nombre").toString().trim();
        this.strFotoPerfil_B64 = treeMap.get("strFotoPerfil_B64").toString().trim();
        this.strFotoPerfil_nombre = treeMap.get("strFotoPerfil_nombre").toString().trim();
        this.strFotoComprobanteDomicilio_B64 = treeMap.get("strFotoComprobanteDomicilio_B64").toString().trim();
        this.strFotoComprobanteDomicilio_nombre = treeMap.get("strFotoComprobanteDomicilio_nombre").toString().trim();
    }

    public String getStrNombreCompleto() {
        return (strNombre1.trim()+" "+strNombre2.trim()).trim() +" "+strApellidoPaterno.trim()+" "+strApellidoMaterno.trim();
    }

    public String getStrDomicilioCompleto() {
        String direccion = strDomicilio.trim()+(strDomicilioNumExt.trim() != "" ? " #"+strDomicilioNumExt.trim() : "")+" "+strDomicilioNumInt.trim();
        return (direccion.trim() != "" ? direccion.trim()+", " : "")+strDomicilioColonia+" "+strDomicilioMunicipio+", "+strDomicilioEstado ;
    }

    public String getStrLugarNacimiento() {
        return (strEstadoNacimiento.trim() != "" ? strEstadoNacimiento.trim()+", " : "")+strPais.trim();
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getStrFechaAlta() {
        return strFechaAlta;
    }

    public void setStrFechaAlta(String strFechaAlta) {
        this.strFechaAlta = strFechaAlta;
    }

    public String getStrStatusSolicitud() {
        return strStatusSolicitud;
    }

    public void setStrStatusSolicitud(String strStatusSolicitud) {
        this.strStatusSolicitud = strStatusSolicitud;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(String idPromotor) {
        this.idPromotor = idPromotor;
    }

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public String getStrPromotor() {
        return strPromotor;
    }

    public void setStrPromotor(String strPromotor) {
        this.strPromotor = strPromotor;
    }

    public String getStrCordinador() {
        return strCordinador;
    }

    public void setStrCordinador(String strCordinador) {
        this.strCordinador = strCordinador;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public String getStrFechaNacimiento() {
        return strFechaNacimiento;
    }

    public void setStrFechaNacimiento(String strFechaNacimiento) {
        this.strFechaNacimiento = strFechaNacimiento;
    }

    public String getStrGenero() {
        return strGenero;
    }

    public void setStrGenero(String strGenero) {
        this.strGenero = strGenero;
    }

    public String getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(String idGenero) {
        this.idGenero = idGenero;
    }

    public String getStrCURP() {
        return strCURP;
    }

    public void setStrCURP(String strCURP) {
        this.strCURP = strCURP;
    }

    public String getStrDomicilio() {
        return strDomicilio;
    }

    public void setStrDomicilio(String strDomicilio) {
        this.strDomicilio = strDomicilio;
    }

    public String getStrDomicilioCodigoPostal() {
        return strDomicilioCodigoPostal;
    }

    public void setStrDomicilioCodigoPostal(String strDomicilioCodigoPostal) {
        this.strDomicilioCodigoPostal = strDomicilioCodigoPostal;
    }

    public String getStrDomicilioNumExt() {
        return strDomicilioNumExt;
    }

    public void setStrDomicilioNumExt(String strDomicilioNumExt) {
        this.strDomicilioNumExt = strDomicilioNumExt;
    }

    public String getStrDomicilioNumInt() {
        return strDomicilioNumInt;
    }

    public void setStrDomicilioNumInt(String strDomicilioNumInt) {
        this.strDomicilioNumInt = strDomicilioNumInt;
    }

    public String getStrDomicilioColonia() {
        return strDomicilioColonia;
    }

    public void setStrDomicilioColonia(String strDomicilioColonia) {
        this.strDomicilioColonia = strDomicilioColonia;
    }

    public String getIdDomicilioEstado() {
        return idDomicilioEstado;
    }

    public void setIdDomicilioEstado(String idDomicilioEstado) {
        this.idDomicilioEstado = idDomicilioEstado;
    }

    public String getIdDomicilioMunicipio() {
        return idDomicilioMunicipio;
    }

    public void setIdDomicilioMunicipio(String idDomicilioMunicipio) {
        this.idDomicilioMunicipio = idDomicilioMunicipio;
    }

    public String getStrDomicilioEstado() {
        return strDomicilioEstado;
    }

    public void setStrDomicilioEstado(String strDomicilioEstado) {
        this.strDomicilioEstado = strDomicilioEstado;
    }

    public String getStrDomicilioMunicipio() {
        return strDomicilioMunicipio;
    }

    public void setStrDomicilioMunicipio(String strDomicilioMunicipio) {
        this.strDomicilioMunicipio = strDomicilioMunicipio;
    }

    public String getStrEstadoCivil() {
        return strEstadoCivil;
    }

    public void setStrEstadoCivil(String strEstadoCivil) {
        this.strEstadoCivil = strEstadoCivil;
    }

    public String getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(String idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
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

    public String getStrOcupacion() {
        return strOcupacion;
    }

    public void setStrOcupacion(String strOcupacion) {
        this.strOcupacion = strOcupacion;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getStrActividad() {
        return strActividad;
    }

    public void setStrActividad(String strActividad) {
        this.strActividad = strActividad;
    }

    public String getStrNumeroINE() {
        return strNumeroINE;
    }

    public void setStrNumeroINE(String strNumeroINE) {
        this.strNumeroINE = strNumeroINE;
    }

    public String getStrClaveINE() {
        return strClaveINE;
    }

    public void setStrClaveINE(String strClaveINE) {
        this.strClaveINE = strClaveINE;
    }

    public String getStrPais() {
        return strPais;
    }

    public void setStrPais(String strPais) {
        this.strPais = strPais;
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

    public String getStrNombreConyuge() {
        return strNombreConyuge;
    }

    public void setStrNombreConyuge(String strNombreConyuge) {
        this.strNombreConyuge = strNombreConyuge;
    }

    public String getStrLugarNacimientoConyuge() {
        return strLugarNacimientoConyuge;
    }

    public void setStrLugarNacimientoConyuge(String strLugarNacimientoConyuge) {
        this.strLugarNacimientoConyuge = strLugarNacimientoConyuge;
    }

    public String getStrFechaNacimientoConyuge() {
        return strFechaNacimientoConyuge;
    }

    public void setStrFechaNacimientoConyuge(String strFechaNacimientoConyuge) {
        this.strFechaNacimientoConyuge = strFechaNacimientoConyuge;
    }

    public String getStrOcupacionConyuge() {
        return strOcupacionConyuge;
    }

    public void setStrOcupacionConyuge(String strOcupacionConyuge) {
        this.strOcupacionConyuge = strOcupacionConyuge;
    }

    public String getStrReferenciaBancaria() {
        return strReferenciaBancaria;
    }

    public void setStrReferenciaBancaria(String strReferenciaBancaria) {
        this.strReferenciaBancaria = strReferenciaBancaria;
    }

    public String getStrBanco() {
        return strBanco;
    }

    public void setStrBanco(String strBanco) {
        this.strBanco = strBanco;
    }

    public String getStrProducto() {
        return strProducto;
    }

    public void setStrProducto(String strProducto) {
        this.strProducto = strProducto;
    }

    public int getIntPlazo() {
        return intPlazo;
    }

    public void setIntPlazo(int intPlazo) {
        this.intPlazo = intPlazo;
    }

    public int getIntQuedateCasa() {
        return intQuedateCasa;
    }

    public void setIntQuedateCasa(int intQuedateCasa) {
        this.intQuedateCasa = intQuedateCasa;
    }

    public double getDblIngresos() {
        return dblIngresos;
    }

    public void setDblIngresos(double dblIngresos) {
        this.dblIngresos = dblIngresos;
    }

    public double getDblEgresos() {
        return dblEgresos;
    }

    public void setDblEgresos(double dblEgresos) {
        this.dblEgresos = dblEgresos;
    }

    public String getStrEstatusInserccionServidor() {
        return strEstatusInserccionServidor;
    }

    public void setStrEstatusInserccionServidor(String strEstatusInserccionServidor) {
        this.strEstatusInserccionServidor = strEstatusInserccionServidor;
    }

    public String getStrCNBV() {
        return strCNBV;
    }

    public void setStrCNBV(String strCNBV) {
        this.strCNBV = strCNBV;
    }

    public double getDblMontoSolicitadoMejoraVivienda() {
        return dblMontoSolicitadoMejoraVivienda;
    }

    public void setDblMontoSolicitadoMejoraVivienda(double dblMontoSolicitadoMejoraVivienda) {
        this.dblMontoSolicitadoMejoraVivienda = dblMontoSolicitadoMejoraVivienda;
    }

    public double getDblMontoSolicitadoEquipandoHogar() {
        return dblMontoSolicitadoEquipandoHogar;
    }

    public void setDblMontoSolicitadoEquipandoHogar(double dblMontoSolicitadoEquipandoHogar) {
        this.dblMontoSolicitadoEquipandoHogar = dblMontoSolicitadoEquipandoHogar;
    }

    public String getStrDomicilio_mejoraVivienda() {
        return strDomicilio_mejoraVivienda;
    }

    public void setStrDomicilio_mejoraVivienda(String strDomicilio_mejoraVivienda) {
        this.strDomicilio_mejoraVivienda = strDomicilio_mejoraVivienda;
    }

    public String getStrCodigoPostal_mejoraVivienda() {
        return strCodigoPostal_mejoraVivienda;
    }

    public void setStrCodigoPostal_mejoraVivienda(String strCodigoPostal_mejoraVivienda) {
        this.strCodigoPostal_mejoraVivienda = strCodigoPostal_mejoraVivienda;
    }

    public String getStrNumExt_mejoraVivienda() {
        return strNumExt_mejoraVivienda;
    }

    public void setStrNumExt_mejoraVivienda(String strNumExt_mejoraVivienda) {
        this.strNumExt_mejoraVivienda = strNumExt_mejoraVivienda;
    }

    public String getStrNumInt_mejoraVivienda() {
        return strNumInt_mejoraVivienda;
    }

    public void setStrNumInt_mejoraVivienda(String strNumInt_mejoraVivienda) {
        this.strNumInt_mejoraVivienda = strNumInt_mejoraVivienda;
    }

    public String getStrColonia_mejoraVivienda() {
        return strColonia_mejoraVivienda;
    }

    public void setStrColonia_mejoraVivienda(String strColonia_mejoraVivienda) {
        this.strColonia_mejoraVivienda = strColonia_mejoraVivienda;
    }

    public String getIdEstado_mejoraVivienda() {
        return idEstado_mejoraVivienda;
    }

    public void setIdEstado_mejoraVivienda(String idEstado_mejoraVivienda) {
        this.idEstado_mejoraVivienda = idEstado_mejoraVivienda;
    }

    public String getStrEstado_mejoraVivienda() {
        return strEstado_mejoraVivienda;
    }

    public void setStrEstado_mejoraVivienda(String strEstado_mejoraVivienda) {
        this.strEstado_mejoraVivienda = strEstado_mejoraVivienda;
    }

    public String getIdMunicipio_mejoraVivienda() {
        return idMunicipio_mejoraVivienda;
    }

    public void setIdMunicipio_mejoraVivienda(String idMunicipio_mejoraVivienda) {
        this.idMunicipio_mejoraVivienda = idMunicipio_mejoraVivienda;
    }

    public String getStrMunicipio_mejoraVivienda() {
        return strMunicipio_mejoraVivienda;
    }

    public void setStrMunicipio_mejoraVivienda(String strMunicipio_mejoraVivienda) {
        this.strMunicipio_mejoraVivienda = strMunicipio_mejoraVivienda;
    }

    public String getStrFotoINEFrontal_B64() {
        return strFotoINEFrontal_B64;
    }

    public void setStrFotoINEFrontal_B64(String strFotoINEFrontal_B64) {
        this.strFotoINEFrontal_B64 = strFotoINEFrontal_B64;
    }

    public String getStrFotoINEFrontal_nombre() {
        return strFotoINEFrontal_nombre;
    }

    public void setStrFotoINEFrontal_nombre(String strFotoINEFrontal_nombre) {
        this.strFotoINEFrontal_nombre = strFotoINEFrontal_nombre;
    }

    public String getStrFotoINEReverso_B64() {
        return strFotoINEReverso_B64;
    }

    public void setStrFotoINEReverso_B64(String strFotoINEReverso_B64) {
        this.strFotoINEReverso_B64 = strFotoINEReverso_B64;
    }

    public String getStrFotoINEReverso_nombre() {
        return strFotoINEReverso_nombre;
    }

    public void setStrFotoINEReverso_nombre(String strFotoINEReverso_nombre) {
        this.strFotoINEReverso_nombre = strFotoINEReverso_nombre;
    }

    public String getStrFotoPerfil_B64() {
        return strFotoPerfil_B64;
    }

    public void setStrFotoPerfil_B64(String strFotoPerfil_B64) {
        this.strFotoPerfil_B64 = strFotoPerfil_B64;
    }

    public String getStrFotoPerfil_nombre() {
        return strFotoPerfil_nombre;
    }

    public void setStrFotoPerfil_nombre(String strFotoPerfil_nombre) {
        this.strFotoPerfil_nombre = strFotoPerfil_nombre;
    }

    public String getStrFotoComprobanteDomicilio_B64() {
        return strFotoComprobanteDomicilio_B64;
    }

    public void setStrFotoComprobanteDomicilio_B64(String strFotoComprobanteDomicilio_B64) {
        this.strFotoComprobanteDomicilio_B64 = strFotoComprobanteDomicilio_B64;
    }

    public String getStrFotoComprobanteDomicilio_nombre() {
        return strFotoComprobanteDomicilio_nombre;
    }

    public void setStrFotoComprobanteDomicilio_nombre(String strFotoComprobanteDomicilio_nombre) {
        this.strFotoComprobanteDomicilio_nombre = strFotoComprobanteDomicilio_nombre;
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

    public String getIdTipoContratoIndividual() {
        return idTipoContratoIndividual;
    }

    public void setIdTipoContratoIndividual(String idTipoContratoIndividual) {
        this.idTipoContratoIndividual = idTipoContratoIndividual;
    }

    public int getIntNumPagos() {
        return intNumPagos;
    }

    public void setIntNumPagos(int intNumPagos) {
        this.intNumPagos = intNumPagos;
    }
}
