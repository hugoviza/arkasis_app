package com.example.arkasis.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MigracionesSQL.CREAR_TABLA_ACTIVIDADES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_MUNICIPIOS);
        db.execSQL(MigracionesSQL.CREAR_TABLA_SUCURSALES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_COORDINADORES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_TIPO_VENCIMIENTO);
        db.execSQL(MigracionesSQL.CREAR_TABLA_SOLICITUDESDISPERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(MigracionesSQL.DROP_TABLA_ACTIVIDADES);
        db.execSQL(MigracionesSQL.DROP_TABLA_MUNICIPIOS);
        db.execSQL(MigracionesSQL.DROP_TABLA_SUCURSALES);
        db.execSQL(MigracionesSQL.DROP_TABLA_COORDINADORES);
        db.execSQL(MigracionesSQL.DROP_TABLA_TIPO_VENCIMIENTO);
        db.execSQL(MigracionesSQL.DROP_TABLA_SOLICITUDESDISPERSION);

        db.execSQL(MigracionesSQL.CREAR_TABLA_ACTIVIDADES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_MUNICIPIOS);
        db.execSQL(MigracionesSQL.CREAR_TABLA_SUCURSALES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_COORDINADORES);
        db.execSQL(MigracionesSQL.CREAR_TABLA_TIPO_VENCIMIENTO);
        db.execSQL(MigracionesSQL.CREAR_TABLA_SOLICITUDESDISPERSION);
    }
}
