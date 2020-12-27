package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.arkasis.DB.ConexionSQLiteHelper;
import com.example.arkasis.config.Config;

public class Conexion {
    private ConexionSQLiteHelper conexionSQLiteHelper;
    public SQLiteDatabase dbWrite;
    public SQLiteDatabase dbRead;

    public Conexion(Context context) {
        conexionSQLiteHelper = new ConexionSQLiteHelper(context, Config.DB_NOMBRE, null, Config.DB_VERSION);
        dbWrite = conexionSQLiteHelper.getWritableDatabase();
        dbRead = conexionSQLiteHelper.getReadableDatabase();
    }

    public void CerraConexion() {
        dbWrite.close();
    }
}
