package com.davidch.proyecto.pethelp.modelo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.datos.tablas.Tabla;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adeka on 22/08/2016.
 */
public class Mascota implements Parcelable {

    @Expose(serialize = false, deserialize = false)
    @SerializedName("id")
    private long idMascota;
    @SerializedName("id_mascota")
    private long idMascotaServidor;
    private String nombre;
    private String apodo;
    private char sexo;
    @SerializedName("fecha_reproduccion")
    private Date fechaReproduccion;
    @SerializedName("fecha_nacimiento")
    private Date fechaNacimiento;
    private long idFamilia;

    private boolean actualizado;
    private boolean insertado;
    private boolean borrado;

    public Mascota() {
    }

    public Mascota(Cursor c) {
        idMascota = c.getInt(0);
        idMascotaServidor = c.getInt(1);
        nombre = c.getString(2);
        apodo = c.getString(3);
        sexo = c.getString(4).charAt(0);
        fechaReproduccion = Mascotas.dateFromSQL(c.getString(5));
        fechaNacimiento = Mascotas.dateFromSQL(c.getString(6));
        idFamilia = c.getInt(7);
        actualizado = c.getInt(8) == 1;
        insertado = c.getInt(9) == 1;
        borrado = c.getInt(10) == 1;
    }

    protected Mascota(Parcel in) {
        idMascota = in.readLong();
        idMascotaServidor = in.readLong();
        nombre = in.readString();
        apodo = in.readString();
        sexo = (char)in.readInt();
        fechaReproduccion = new Date(in.readLong());
        fechaNacimiento = new Date(in.readLong());
        idFamilia = in.readLong();
    }

    public static final List<Mascota> fromCursor(Cursor cursor) {
        List<Mascota> mascotas = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mascotas.add(new Mascota(cursor));
            }
            while (cursor.moveToNext());
        }
        return mascotas;
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

    public long getIdMascotaServidor() {
        return idMascotaServidor;
    }

    public void setIdMascotaServidor(long idMascotaServidor) {
        this.idMascotaServidor = idMascotaServidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaReproduccion() {
        return fechaReproduccion;
    }

    public void setFechaReproduccion(Date fechaReproduccion) {
        this.fechaReproduccion = fechaReproduccion;
    }

    public long getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(long idFamilia) {
        this.idFamilia = idFamilia;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idMascota);
        dest.writeLong(idMascotaServidor);
        dest.writeString(nombre);
        dest.writeString(apodo);
        dest.writeInt((int)sexo);
        dest.writeLong(fechaReproduccion.getTime());
        dest.writeLong(fechaNacimiento.getTime());
        dest.writeLong(idFamilia);
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaReproduccion(Date fechaReproduccion) {
        this.fechaReproduccion = fechaReproduccion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Mascotas.ID_SERVIDOR, idMascotaServidor);
        cv.put(Mascotas.NOMBRE, nombre);
        cv.put(Mascotas.APODO, apodo);
        cv.put(Mascotas.SEXO, Character.toString(sexo));
        cv.put(Mascotas.FECHA_NACIMIENTO, Tabla.dateToSQL(fechaNacimiento));
        cv.put(Mascotas.FECHA_REPRODUCCION, Tabla.dateToSQL(fechaReproduccion));
        cv.put(Mascotas.ID_FAMILIA, idFamilia);
        cv.put(Mascotas.ACTUALIZADO, actualizado);
        cv.put(Mascotas.INSERTADO, insertado);
        cv.put(Mascotas.BORRADO, borrado);
        return cv;
    }

    public void esNueva() {
        insertado = true;
    }

}
