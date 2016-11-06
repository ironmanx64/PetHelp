package com.davidch.proyecto.pethelp.modelo;

import android.content.ContentValues;

import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adeka on 06/11/2016.
 */
public class Cuidador {

    @Expose(serialize = false, deserialize = false)
    private long idCuidador;
    @SerializedName("id_mascota")
    private long idServidorMascota;
    private String nick;

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

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cuidadores.ID, idCuidador);
        contentValues.put(Cuidadores.ID_SERVIDOR_MASCOTA, idServidorMascota);
        contentValues.put(Cuidadores.NICK, nick);
        return contentValues;
    }
}
