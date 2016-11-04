package com.davidch.proyecto.pethelp.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Mascota;

/**
 * Created by adeka on 21/10/2016.
 */

public class PethelpSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "pethelp.sqlite";
    private static final int VERSION_BD = 3;

    public PethelpSQLiteOpenHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Mascotas.CREATE);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            db.execSQL(Mascotas.DROP);
            db.execSQL(Mascotas.CREATE);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
}
