package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Municipio;

import java.util.ArrayList;
import java.util.List;

public class TableActividades extends Conexion implements TableSQLite {
    public static final String table = "catalogoActividades";
    public static final String col_idActividad = "idActividad";
    public static final String col_strActividad = "strActividad";
    public static final String col_strCNBV = "strCNBV";


    public TableActividades(Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        Actividad item = (Actividad) o;
        String query = "REPLACE INTO "+ table
                +" ("+col_idActividad+", "+col_strActividad+", "+col_strCNBV+")"
                + " values"
                + " ('"+item.getIdActividad()+"', '"+item.getStrActividad()+"', '"+item.getStrCNBV()+"')";
        this.dbWrite.execSQL(query);
    }

    @Override
    public void insertarBatch(List<Object> objectList) {
        for (Object item: objectList) {
            this.insertar(item);
        };
    }

    @Override
    public int getCount() {
        Cursor cursor = dbRead.rawQuery("Select count(*) as totalRegistros from "+ table, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    @Override
    public void truncate() {
        String query = MigracionesSQL.TRUNCATE_TABLA_ACTIVIDADES;
        this.dbWrite.execSQL(query);
    }

    @Override
    public List<Object> findAll(String buscar, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idActividad+", "+col_strActividad+", "+col_strCNBV+" FROM "+table+" WHERE ("+col_strActividad+") like '%"+buscar+"%' ORDER BY "+col_strActividad+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Actividad(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }

        return list;
    }
}
