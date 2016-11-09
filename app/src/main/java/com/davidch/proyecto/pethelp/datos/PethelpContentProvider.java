package com.davidch.proyecto.pethelp.datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.datos.tablas.Tabla;
import com.davidch.proyecto.pethelp.modelo.Mascota;

public class PethelpContentProvider extends ContentProvider {

    public static final String AUTORITY = "com.davidch.proyecto.pethelp.datos";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTORITY + "/");

    public static Uri getUriMascotas() {
        return Uri.withAppendedPath(BASE_URI, "mascotas");
    }
    public static Uri getUriMascota(long id) {
        return Uri.withAppendedPath(getUriMascotas(), Long.toString(id));
    }

    public static Uri getUriCuidadores() {
        return Uri.withAppendedPath(BASE_URI, "cuidadores");
    }
    public static Uri getUriCuidador(long id) {
        return Uri.withAppendedPath(getUriMascotas(), Long.toString(id));
    }

    private static final UriMatcher URI_MATCHER;
    private static final int URI_NOENCONTRADA = -1;
    private static final int URI_MASCOTAS = 1;
    private static final int URI_MASCOTA = 2;
    private static final int URI_CUIDADORES = 3;
    private static final int URI_CUIDADOR = 4;

    static {
        URI_MATCHER = new UriMatcher(URI_NOENCONTRADA);
        URI_MATCHER.addURI(AUTORITY, "mascotas", URI_MASCOTAS);
        URI_MATCHER.addURI(AUTORITY, "mascotas/#", URI_MASCOTA);
        URI_MATCHER.addURI(AUTORITY, "cuidadores", URI_CUIDADORES);
        URI_MATCHER.addURI(AUTORITY, "cuidadores/#", URI_CUIDADOR);
    }

    private SQLiteOpenHelper sqliteOpenHelper;

    @Override
    public boolean onCreate() {
        sqliteOpenHelper = new PethelpSQLiteOpenHelper(getContext());
        return true;
    }

    private String getTableFromUri(int uri) {
        switch (uri) {
            case URI_MASCOTA:
            case URI_MASCOTAS:
                return Mascotas.TABLA;
            case URI_CUIDADOR:
            case URI_CUIDADORES:
                return Cuidadores.TABLA;
            default:
                throw new IllegalArgumentException("Código de uri no válido: " + uri);
        }
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
        if (cont > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return cont;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        long id;
        int codigoUri = URI_MATCHER.match(uri);
        switch (codigoUri) {
            case URI_MASCOTAS:
            case URI_CUIDADORES:
                id = db.insert(getTableFromUri(codigoUri), null, values);
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        Uri uriGenerada = Uri.withAppendedPath(uri, Long.toString(id));
        getContext().getContentResolver().notifyChange(uriGenerada, null);
        return uriGenerada;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        int codigoUri = URI_MATCHER.match(uri);
        switch (codigoUri) {
            case URI_MASCOTAS:
            case URI_CUIDADORES:
                cursor = db.query(getTableFromUri(codigoUri), projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_MASCOTA:
            case URI_CUIDADOR:
                cursor = db.query(
                        Mascotas.TABLA,
                        projection,
                        Mascotas.ID + "=?",
                        new String [] {
                                uri.getLastPathSegment()
                        }, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        int modificadas = 0;
        int codigoUri = URI_MATCHER.match(uri);
        switch (codigoUri) {
            case URI_MASCOTA:
            case URI_CUIDADOR:
                 modificadas = db.update(
                        getTableFromUri(codigoUri),
                        values,
                        Tabla.ID + "=?",
                        new String [] {
                                uri.getLastPathSegment()
                        });
                break;
            default:
                throw new IllegalArgumentException("Uri no válida: " + uri);
        }
        if (modificadas > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return modificadas;
    }
}
