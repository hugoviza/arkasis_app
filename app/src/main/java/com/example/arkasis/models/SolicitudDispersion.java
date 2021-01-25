package com.example.arkasis.models;

public class SolicitudDispersion {

    private int idSolicitud;
    private String strFechaAlta = "";
    private String strStatusSolicitud = "";
    private String idSucursal = "";
    private String strUsuarioPromotor = "";
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

    public SolicitudDispersion() {
    }


    public SolicitudDispersion(int idSolicitud, String strFechaAlta, String strStatusSolicitud, String idSucursal, String strUsuarioPromotor, String idPromotor, String strPromotor, String strCordinador, String idCliente, String strApellidoPaterno, String strApellidoMaterno, String strNombre1, String strNombre2, String strFechaNacimiento, String idGenero, String strGenero, String strCURP, String strDomicilio, String strDomicilioCodigoPostal, String strDomicilioNumExt, String strDomicilioNumInt, String strDomicilioColonia, String idDomicilioEstado, String strDomicilioEstado, String idDomicilioMunicipio, String strDomicilioMunicipio, String strEstadoCivil, String idEstadoCivil, String strTelefono, String strCelular, String strOcupacion, String idActividad, String strActividad, String strNumeroINE, String strClaveINE, String strPais, String strEstadoNacimiento, String strNacionalidad, String strEmail, String strNombreConyuge, String strLugarNacimientoConyuge, String strFechaNacimientoConyuge, String strOcupacionConyuge, String strReferenciaBancaria, String strBanco, String strProducto, int intPlazo, int intQuedateCasa, double dblMontoSolicitadoMejoraVivienda, double dblMontoSolicitadoEquipandoHogar, double dblIngresos, double dblEgresos, String strEstatusInserccionServidor, String strCNBV) {
        this.idSolicitud = idSolicitud;
        this.strFechaAlta = strFechaAlta;
        this.strStatusSolicitud = strStatusSolicitud;
        this.idSucursal = idSucursal;
        this.strUsuarioPromotor = strUsuarioPromotor;
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

    public String getStrUsuarioPromotor() {
        return strUsuarioPromotor;
    }

    public void setStrUsuarioPromotor(String strUsuarioPromotor) {
        this.strUsuarioPromotor = strUsuarioPromotor;
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
}
