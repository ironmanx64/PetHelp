package com.davidch.proyecto.pethelp.datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;

public class PethelpContentProvider extends ContentProvider {

    public static final String AUTORITY = "com.davidch.proyecto.pethelp.datos";
    public static final Uri BASE_URI = Uri.fromParts("content", AUTORITY, "");

    public static Uri getUriMascotas() {
        return Uri.withAppendedPath(BASE_URI, "mascotas");
    }

    private static final UriMatcher URI_MATCHER;
    private static final int URI_NOENCONTRADA = -1;
    private static final int URI_MASCOTAS = 1;

    static {
        URI_MATCHER = new UriMatcher(URI_NOENCONTRADA);
        URI_MATCHER.addURI(AUTORITY, "mascotas", URI_MASCOTAS);
    }

    private SQLiteOpenHelper sqliteOpenHelper;

    @Override
    public boolean onCreate() {
        sqliteOpenHelper = new PethelpSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        int borrado;
        switch (URI_MATCHER.match(uri)) {
            case URI_MASCOTAS:
                borrado = db.delete(Mascotas.TABLA, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return borrado;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        int cont = 0;
        switch (URI_MATCHER.match(uri)) {
            case URI_MASCOTAS:
                for (ContentValues value: values) {
                    if (db.insert(Mascotas.TABLA, null, value) != -1) {
                        cont++;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        db.close();
        if (cont > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return cont;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        long id;
        switch (URI_MATCHER.match(uri)) {
            case URI_MASCOTAS:
                id = db.insert(Mascotas.TABLA, null, values);
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        db.close();
        Uri uriGenerada = Uri.withAppendedPath(uri, Long.toString(id));
        getContext().getContentResolver().notifyChange(uriGenerada, null);
        return uriGenerada;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case URI_MASCOTAS:
                cursor = db.query(Mascotas.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        db.close();
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
