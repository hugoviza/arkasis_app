package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.example.arkasis.DB.ConexionSQLiteHelper;
import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.models.Municipio;

import java.util.ArrayList;
import java.util.List;

public class TableMunicipios extends Conexion {
    public static final String table = "catalogoMunicipios";
    public static final String col_idEstado_idMunicipio = "idEstado_idMunicipio";
    public static final String col_idEstado = "idEstado";
    public static final String col_strEstado = "strEstado";
    public static final String col_idMunicipio = "idMunicipio";
    public static final String col_strMunicipio = "strMunicipio";

    public TableMunicipios(@NonNull Context context) {
        super(context);
    }

    public void Insertar(Municipio municipio) {
        String query = "INSERT INTO "+table
                +" ("+col_idEstado_idMunicipio+", "+col_idEstado+", "+col_strEstado+", "+col_idMunicipio+", "+col_strMunicipio+" )"
                + " values"
                + " ('"+municipio.getIdEstado_IdMunicipio()+"', '"+municipio.getIdEstado()+"', '"+municipio.getStrEstado()+"', '"+municipio.getIdMunicipio()+"', '"+municipio.getStrMunicipio()+"')";
        this.dbWrite.execSQL(query);
    }

    public void InsertarBatch(List<Municipio> list) {
        for (Municipio municipio: list) {
            this.Insertar(municipio);
        };
    }

    public Integer getCount() {
        Cursor cursor = dbRead.rawQuery("Select count(*) as totalRegistros from "+table, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public void truncate() {
        String query = MigracionesSQL.TRUNCATE_TABLA_MUNICIPIOS;
        this.dbWrite.execSQL(query);
    }

    public List<Municipio> findAll(String buscar, Integer limit) {
        List<Municipio> listaMunicipio = new ArrayList<>();
        //WHERE CONCAT("+col_strMunicipio+", ' ' ,"+col_strEstado+") like '%"+buscar+"%'
        String query = "SELECT "+col_idEstado+", "+col_strEstado+", "+col_idMunicipio+", "+col_strMunicipio+" FROM "+table+" WHERE ("+col_strMunicipio+" || ' ' || "+col_strEstado+") like '%"+buscar+"%' ORDER BY "+col_strMunicipio+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            // String idEstado, String strEstado, String idMunicipio, String strMunicipio
            listaMunicipio.add(new Municipio(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3)));
        }

        return listaMunicipio;
    }
}
