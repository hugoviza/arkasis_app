package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.Sucursal;

import java.util.ArrayList;
import java.util.List;

public class TableSucursal extends Conexion implements TableSQLite {
    public static final String table = "catalogoSucursales";
    public static final String col_idSucursal = "idSucursal";
    public static final String col_strClaveSucursal = "strClaveSucursal";
    public static final String col_strSucursal = "strSucursal";

    public TableSucursal(Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        Sucursal item = (Sucursal)o;
        String query = "INSERT INTO "+table
                +" ("+col_idSucursal+", "+col_strClaveSucursal+", "+col_strSucursal+")"
                + " values"
                + " ('"+item.getIdSucursal()+"', '"+item.getStrClaveSucursal()+"', '"+item.getStrSucursal()+"')";
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
        String query = MigracionesSQL.TRUNCATE_TABLA_SUCURSALES;
        this.dbWrite.execSQL(query);
    }

    @Override
    public List<Object> findAll(String buscar, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idSucursal+", "+col_strClaveSucursal+", "+col_strSucursal+" FROM "+table+" WHERE ("+col_strSucursal+") like '%"+buscar+"%' ORDER BY "+col_strSucursal+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new Sucursal(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }

        return list;
    }
}
