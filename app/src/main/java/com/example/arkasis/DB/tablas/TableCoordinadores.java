package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Coordinador;

import java.util.ArrayList;
import java.util.List;

public class TableCoordinadores extends Conexion implements TableSQLite {

    public static final String table = "catalogoCoordinadores";
    public static final String col_idCoordinador = "idCoordinador";
    public static final String col_idSucursal = "idSucursal";
    public static final String col_strNombre = "strNombre";

    public TableCoordinadores(Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        Coordinador item = (Coordinador) o;
        String query = "INSERT INTO "+ table
                +" ("+col_idCoordinador+", "+col_idSucursal+", "+col_strNombre+")"
                + " values"
                + " ('"+item.getIdCoordinador()+"', '"+item.getIdSucursal()+"', '"+item.getStrNombre()+"')";
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
        String query = MigracionesSQL.TRUNCATE_TABLA_COORDINADORES;
        this.dbWrite.execSQL(query);
    }

    @Override
    public List<Object> findAll(String buscar, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idCoordinador+", "+col_idSucursal+", "+col_strNombre+" FROM "+table+" WHERE ("+col_strNombre+") like '%"+buscar+"%' ORDER BY "+col_strNombre+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Coordinador(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
        }

        return list;
    }

    public List<Object> findAll(String buscar, String idSucursal, Integer limit) {
        List<Object> list = new ArrayList<>();
        String filtroSucursal = (idSucursal == null || idSucursal == "") ? "" : "AND "+col_idSucursal+" = '"+idSucursal+"'";
        String query = "SELECT "+col_idCoordinador+", "+col_idSucursal+", "+col_strNombre+" FROM "+table+
                " WHERE ("+col_strNombre+") like '%"+buscar+"%' "+
                filtroSucursal+" ORDER BY "+col_strNombre+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Coordinador(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
        }

        return list;
    }
}
