package com.davidch.proyecto.pethelp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adeka on 22/08/2016.
 */
public class Mascota implements Parcelable {

    private long idMascota;
    private String nombre;

    public Mascota() {
    }

    public Mascota(long idMascota, String nombre) {
        this.idMascota = idMascota;
        this.nombre = nombre;
    }

    protected Mascota(Parcel in) {
        idMascota = in.readLong();
        nombre = in.readString();
    }

    public static final Creator<Mascota> CREATOR = new Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel in) {
            return new Mascota(in);
        }

        @Override
        public Mascota[] newArray(int size) {
            return new Mascota[size];
        }
    };

    public long getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(long idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idMascota);
        dest.writeString(nombre);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
