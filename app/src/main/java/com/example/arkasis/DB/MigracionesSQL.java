package com.example.arkasis.DB;

import com.example.arkasis.DB.tablas.TableActividades;
import com.example.arkasis.DB.tablas.TableCoordinadores;
import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.DB.tablas.TableSucursal;

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

    public static final String TRUNCATE_TABLA_ACTIVIDADES = "DELETE FROM " + TableActividades.table;
    public static final String TRUNCATE_TABLA_MUNICIPIOS = "DELETE FROM " + TableMunicipios.table;
    public static final String TRUNCATE_TABLA_SUCURSALES = "DELETE FROM " + TableSucursal.table;
    public static final String TRUNCATE_TABLA_COORDINADORES = "DELETE FROM " + TableCoordinadores.table;



}
