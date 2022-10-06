package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.example.arkasis.DB.ConexionSQLiteHelper;
import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.Estado;
import com.example.arkasis.models.Municipio;

import java.util.ArrayList;
import java.util.List;

public class TableMunicipios extends Conexion implements TableSQLite {
    public static final String table = "catalogoMunicipios";
    public static final String col_idEstado_idMunicipio = "idEstado_idMunicipio";
    public static final String col_idEstado = "idEstado";
    public static final String col_strEstado = "strEstado";
    public static final String col_idMunicipio = "idMunicipio";
    public static final String col_strMunicipio = "strMunicipio";

    public TableMunicipios(@NonNull Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        Municipio item = (Municipio)o;
        String query = "REPLACE INTO "+table
                +" ("+col_idEstado_idMunicipio+", "+col_idEstado+", "+col_strEstado+", "+col_idMunicipio+", "+col_strMunicipio+" )"
                + " values"
                + " ('"+item.getIdEstado_IdMunicipio()+"', '"+item.getIdEstado()+"', '"+item.getStrEstado()+"', '"+item.getIdMunicipio()+"', '"+item.getStrMunicipio()+"')";
        this.dbWrite.execSQL(query);
    }

    @Override
    public void insertarBatch(List<Object> objectList) {
        for (Object item: objectList) {
            this.insertar(item);
        };
    }

    public int getCount() {
        Cursor cursor = dbRead.rawQuery("Select count(*) as totalRegistros from "+ table, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public void truncate() {
        String query = MigracionesSQL.TRUNCATE_TABLA_MUNICIPIOS;
        this.dbWrite.execSQL(query);
    }

    public List<Object> findAll(String buscar, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idEstado+", "+col_strEstado+", "+col_idMunicipio+", "+col_strMunicipio+" FROM "+table+" WHERE ("+col_strMunicipio+" || ' ' || "+col_strEstado+") like '%"+buscar+"%' ORDER BY "+col_strMunicipio+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Municipio(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3)));
        }

        return list;
    }

    public List<Object> findAllEstados(String buscar) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idEstado+", "+col_strEstado+" FROM "+table+" WHERE ("+col_strEstado+") like '%"+buscar+"%' GROUP BY "+col_strEstado+" ORDER BY "+col_strEstado;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Estado(cursor.getString(0), cursor.getString(1)));
        }

        return list;
    }
}
