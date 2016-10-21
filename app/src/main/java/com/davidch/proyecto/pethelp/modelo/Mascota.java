package com.davidch.proyecto.pethelp.modelo;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.datos.tablas.Tabla;

import java.util.Date;

/**
 * Created by adeka on 22/08/2016.
 */
public class Mascota implements Parcelable {

    private long idMascota;
    private String nombre;
    private String apodo;
    private char sexo;
    private Date fechaReproduccion;
    private Date fechaNacimiento;
    private long idFamilia;

    public Mascota() {
    }

    public Mascota(long idMascota, String nombre, String apodo, char sexo, Date fechaReproduccion,
                   Date fechaNacimiento, long idFamilia) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.apodo = apodo;
        this.sexo = sexo;
        this.fechaReproduccion = fechaReproduccion;
        this.fechaNacimiento = fechaNacimiento;
        this.idFamilia = idFamilia;
    }

    protected Mascota(Parcel in) {
        idMascota = in.readLong();
        nombre = in.readString();
        apodo = in.readString();
        sexo = (char)in.readInt();
        fechaReproduccion = new Date(in.readLong());
        fechaNacimiento = new Date(in.readLong());
        idFamilia = in.readLong();
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
        dest.writeString(apodo);
        dest.writeInt((int)sexo);
        dest.writeLong(fechaReproduccion.getTime());
        dest.writeLong(fechaNacimiento.getTime());
        dest.writeLong(idFamilia);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Mascotas.NOMBRE, nombre);
        cv.put(Mascotas.APODO, apodo);
        cv.put(Mascotas.SEXO, Character.toString(sexo));
        cv.put(Mascotas.FECHA_NACIMIENTO, Tabla.toSQL(fechaNacimiento));
        cv.put(Mascotas.FECHA_REPRODUCCION, Tabla.toSQL(fechaReproduccion));
        cv.put(Mascotas.ID_FAMILIA, idFamilia);
        return cv;
    }
}
