package com.example.arkasis.DB;

import com.example.arkasis.DB.tablas.TableActividades;
import com.example.arkasis.DB.tablas.TableCoordinadores;
import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.DB.tablas.TableSucursal;
import com.example.arkasis.DB.tablas.TableTipoVencimiento;
import com.example.arkasis.models.SolicitudDispersion;

public class MigracionesSQL {
    public static final String CREAR_TABLA_MUNICIPIOS =
            "CREATE TABLE IF NOT EXISTS "+TableMunicipios.table+
                    " ("+TableMunicipios.col_idEstado_idMunicipio+" TEXT PRIMARY KEY, "+
                    TableMunicipios.col_idMunicipio+" TEXT, "+
                    TableMunicipios.col_strMunicipio+" TEXT, "+
                    TableMunicipios.col_idEstado+" TEXT, "+
                    TableMunicipios.col_strEstado+" TEXT)";

    public static final String CREAR_TABLA_ACTIVIDADES =
            "CREATE TABLE IF NOT EXISTS "+ TableActividades.table +
                    " ("+TableActividades.col_idActividad+" INTEGER PRIMARY KEY, "+
                    TableActividades.col_strActividad+" TEXT, "+
                    TableActividades.col_strCNBV+" TEXT)";

    public static final String CREAR_TABLA_SUCURSALES =
            "CREATE TABLE IF NOT EXISTS "+ TableSucursal.table+
                    " ("+TableSucursal.col_idSucursal+" INTEGER PRIMARY KEY, "+
                    TableSucursal.col_strClaveSucursal+" TEXT, "+
                    TableSucursal.col_strSucursal+" TEXT)";

    public static final String CREAR_TABLA_COORDINADORES =
            "CREATE TABLE IF NOT EXISTS "+ TableCoordinadores.table+
                    " ("+TableCoordinadores.col_idCoordinador+" INTEGER PRIMARY KEY, "+
                    TableCoordinadores.col_idSucursal+" INTEGER, "+
                    TableCoordinadores.col_strNombre+" TEXT)";

    public static final String CREAR_TABLA_TIPO_VENCIMIENTO =
            "CREATE TABLE IF NOT EXISTS "+ TableTipoVencimiento.table+
                    " ("+TableTipoVencimiento.col_idTipoVencimiento+" INTEGER PRIMARY KEY, "+
                    TableTipoVencimiento.col_strTipoVencimiento+" TEXT, "+
                    TableTipoVencimiento.col_idSucursal+" INTEGER, "+
                    TableTipoVencimiento.col_intNumDias+" INTEGER)";

    public static final String CREAR_TABLA_SOLICITUDESDISPERSION =
            "CREATE TABLE IF NOT EXISTS "+ TableSolicitudesDispersion.table+
                    " ("+ TableSolicitudesDispersion.col_idSolicitud+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TableSolicitudesDispersion.col_strFechaAlta + " TEXT,"
                    + TableSolicitudesDispersion.col_strStatusSolicitud + " TEXT,"
                    + TableSolicitudesDispersion.col_idSucursal + " TEXT,"
                    + TableSolicitudesDispersion.col_strUsuario + " TEXT,"
                    + TableSolicitudesDispersion.col_idPromotor + " TEXT,"
                    + TableSolicitudesDispersion.col_strPromotor + " TEXT,"
                    + TableSolicitudesDispersion.col_strCordinador + " TEXT,"
                    + TableSolicitudesDispersion.col_idCliente + " TEXT,"
                    + TableSolicitudesDispersion.col_strApellidoPaterno + " TEXT,"
                    + TableSolicitudesDispersion.col_strApellidoMaterno + " TEXT,"
                    + TableSolicitudesDispersion.col_strNombre1 + " TEXT,"
                    + TableSolicitudesDispersion.col_strNombre2 + " TEXT,"
                    + TableSolicitudesDispersion.col_strFechaNacimiento + " TEXT,"
                    + TableSolicitudesDispersion.col_idGenero + " REAL,"
                    + TableSolicitudesDispersion.col_strGenero + " TEXT,"
                    + TableSolicitudesDispersion.col_strCURP + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilio + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioCodigoPostal + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioNumExt + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioNumInt + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioColonia + " TEXT,"
                    + TableSolicitudesDispersion.col_idDomicilioEstado + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioEstado + " TEXT,"
                    + TableSolicitudesDispersion.col_idDomicilioMunicipio + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilioMunicipio + " TEXT,"
                    + TableSolicitudesDispersion.col_strEstadoCivil + " TEXT,"
                    + TableSolicitudesDispersion.col_idEstadoCivil + " TEXT,"
                    + TableSolicitudesDispersion.col_strTelefono + " TEXT,"
                    + TableSolicitudesDispersion.col_strCelular + " TEXT,"
                    + TableSolicitudesDispersion.col_strOcupacion + " TEXT,"
                    + TableSolicitudesDispersion.col_idActividad + " TEXT,"
                    + TableSolicitudesDispersion.col_strActividad + " TEXT,"
                    + TableSolicitudesDispersion.col_strNumeroINE + " TEXT,"
                    + TableSolicitudesDispersion.col_strClaveINE + " TEXT,"
                    + TableSolicitudesDispersion.col_strPais + " TEXT,"
                    + TableSolicitudesDispersion.col_strEstadoNacimiento + " TEXT,"
                    + TableSolicitudesDispersion.col_strNacionalidad + " TEXT,"
                    + TableSolicitudesDispersion.col_strEmail + " TEXT,"
                    + TableSolicitudesDispersion.col_strNombreConyuge + " TEXT,"
                    + TableSolicitudesDispersion.col_strLugarNacimientoConyuge + " TEXT,"
                    + TableSolicitudesDispersion.col_strFechaNacimientoConyuge + " TEXT,"
                    + TableSolicitudesDispersion.col_strOcupacionConyuge + " TEXT,"
                    + TableSolicitudesDispersion.col_strReferenciaBancaria + " TEXT,"
                    + TableSolicitudesDispersion.col_strBanco + " TEXT,"
                    + TableSolicitudesDispersion.col_strProducto + " TEXT,"
                    + TableSolicitudesDispersion.col_intPlazo + " INTEGER,"
                    + TableSolicitudesDispersion.col_intQuedateCasa + " INTEGER,"
                    + TableSolicitudesDispersion.col_dblMontoSolicitadoEquipandoHogar + " REAL,"
                    + TableSolicitudesDispersion.col_dblMontoSolicitadoMejoraVivienda + " REAL,"
                    + TableSolicitudesDispersion.col_dblIngresos + " REAL,"
                    + TableSolicitudesDispersion.col_dblEgresos + " REAL,"
                    + TableSolicitudesDispersion.col_strEstatusInserccionServidor + " TEXT,"
                    + TableSolicitudesDispersion.col_strCNBV + " TEXT,"
                    + TableSolicitudesDispersion.col_strDomicilio_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strCodigoPostal_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strNumExt_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strNumInt_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strColonia_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_idEstado_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strEstado_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_idMunicipio_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strMunicipio_mejoraVivienda + " TEXT,"
                    + TableSolicitudesDispersion.col_strFotoINEFrontal_B64 + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoINEFrontal_nombre + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoINEReverso_B64 + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoINEReverso_nombre + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoPerfil_B64 + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoPerfil_nombre + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoComprobanteDomicilio_B64 + " TEXT, "
                    + TableSolicitudesDispersion.col_strFotoComprobanteDomicilio_nombre + " TEXT, "
                    + TableSolicitudesDispersion.col_idTipoVencimiento + " INTEGER, "
                    + TableSolicitudesDispersion.col_strTipoVencimiento + " TEXT, "
                    + TableSolicitudesDispersion.col_idTipoContratoIndividual + " TEXT, "
                    + TableSolicitudesDispersion.col_intNumPagos + " INTEGER "
                    + " )";

    public static final String TRUNCATE_TABLA_TIPO_VENCIMIENTO = "DELETE FROM " + TableActividades.table;
    public static final String TRUNCATE_TABLA_ACTIVIDADES = "DELETE FROM " + TableActividades.table;
    public static final String TRUNCATE_TABLA_MUNICIPIOS = "DELETE FROM " + TableMunicipios.table;
    public static final String TRUNCATE_TABLA_SUCURSALES = "DELETE FROM " + TableSucursal.table;
    public static final String TRUNCATE_TABLA_COORDINADORES = "DELETE FROM " + TableCoordinadores.table;
    public static final String TRUNCATE_TABLA_SOLICITUDESDISPERSION = "DELETE FROM " + TableSolicitudesDispersion.table;

    public static final String DROP_TABLA_ACTIVIDADES = "DROP TABLE IF EXISTS " + TableActividades.table;
    public static final String DROP_TABLA_MUNICIPIOS = "DROP TABLE IF EXISTS " + TableMunicipios.table;
    public static final String DROP_TABLA_SUCURSALES = "DROP TABLE IF EXISTS " + TableSucursal.table;
    public static final String DROP_TABLA_COORDINADORES = "DROP TABLE IF EXISTS " + TableCoordinadores.table;
    public static final String DROP_TABLA_SOLICITUDESDISPERSION = "DROP TABLE IF EXISTS " + TableSolicitudesDispersion.table;
    public static final String DROP_TABLA_TIPO_VENCIMIENTO = "DROP TABLE IF EXISTS " + TableTipoVencimiento.table;

}
