package com.davidch.proyecto.pethelp.modelo;

import android.content.ContentValues;
import android.database.Cursor;

import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adeka on 06/11/2016.
 */
public class Cuidador {

    @SerializedName("id_cuidador")
    private long idCuidador;
    @SerializedName("id_mascota")
    private long idServidorMascota;
    private String nick;
    @SerializedName("cuidador_actual")
    private boolean actual;
    private boolean duenio;

    public Cuidador() {
    }

    public Cuidador(Cursor cursor) {
        idCuidador = cursor.getLong(0);
        idServidorMascota = cursor.getLong(1);
        nick = cursor.getString(2);
        actual = cursor.getInt(3) != 0;
    }

    public long getIdCuidador() {
        return idCuidador;
    }

    public void setIdCuidador(long idCuidador) {
        this.idCuidador = idCuidador;
    }

    public long getIdServidorMascota() {
        return idServidorMascota;
    }

    public void setIdServidorMascota(long idServidorMascota) {
        this.idServidorMascota = idServidorMascota;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public boolean isDuenio() {
        return duenio;
    }

    public void setDuenio(boolean duenio) {
        this.duenio = duenio;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cuidadores.ID, idCuidador);
        contentValues.put(Cuidadores.ID_MASCOTA, idServidorMascota);
        contentValues.put(Cuidadores.NICK, nick);
        contentValues.put(Cuidadores.DUENIO, duenio);
        contentValues.put(Cuidadores.ACTUAL, actual);
        return contentValues;
    }

    public static List<Cuidador> fromCursor(Cursor cursor) {
        List<Cuidador> cuidadores = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cuidadores.add(new Cuidador(cursor));
            }
            while (cursor.moveToNext());
        }
        return cuidadores;
    }
}
