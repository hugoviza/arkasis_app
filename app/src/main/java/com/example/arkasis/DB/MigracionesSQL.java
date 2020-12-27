package com.example.arkasis.DB;

import com.example.arkasis.DB.tablas.TableMunicipios;

public class MigracionesSQL {
    public static final String CREAR_TABLA_MUNICIPIOS =
            "CREATE TABLE IF NOT EXISTS "+TableMunicipios.table+
                    " ("+TableMunicipios.col_idEstado_idMunicipio+" TEXT PRIMARY KEY, "+
                    TableMunicipios.col_idMunicipio+" TEXT, "+
                    TableMunicipios.col_strMunicipio+" TEXT, "+
                    TableMunicipios.col_idEstado+" TEXT, "+
                    TableMunicipios.col_strEstado+" TEXT)";

    public static final String TRUNCATE_TABLA_MUNICIPIOS = "DELETE FROM " + TableMunicipios.table;
}
