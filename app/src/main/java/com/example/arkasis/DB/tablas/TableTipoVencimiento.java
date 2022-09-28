package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import com.example.arkasis.DB.MigracionesSQL;
import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.TipoVencimiento;

import java.util.ArrayList;
import java.util.List;

public class TableTipoVencimiento extends Conexion implements TableSQLite {
    public static final String table = "catalogoTipoVencimiento";
    public static final String col_idTipoVencimiento = "idTipoVencimiento";
    public static final String col_strTipoVencimiento = "strTipoVencimiento";
    public static final String col_idSucursal = "idSucursal";
    public static final String col_intNumDias = "intNumDias";

    public TableTipoVencimiento(Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        TipoVencimiento item = (TipoVencimiento) o;
        String query = "INSERT INTO "+ table
                +" ("+col_idTipoVencimiento+", "+col_strTipoVencimiento+", "+col_idSucursal+", "+col_intNumDias+")"
                + " values"
                + " ('"+item.getIdTipoVencimiento()+"', '"+item.getStrTipoVencimiento()+"', '"+item.getIdSucursal()+"', '"+item.getIntNumDias()+"')";
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
        String query = MigracionesSQL.TRUNCATE_TABLA_TIPO_VENCIMIENTO;
        this.dbWrite.execSQL(query);
    }

    @Override
    public List<Object> findAll(String buscar, Integer limit) {
        return null;
    }

    public List<Object> findAll(String idSucursal, String strTipoVencimiento, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "+col_idTipoVencimiento+", "+col_strTipoVencimiento+", "+col_idSucursal+", "+col_intNumDias+" FROM "+table+" WHERE ("+col_idSucursal+") = '"+idSucursal+"' AND "+col_strTipoVencimiento+" like '%"+strTipoVencimiento+"%' ORDER BY "+col_strTipoVencimiento+" limit "+limit;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new TipoVencimiento(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
        }

        return list;
    }
}
