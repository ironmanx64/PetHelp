package com.davidch.proyecto.pethelp.datos.tablas;

/**
 * Created by adeka on 04/11/2016.
 */

public class Cuidadores extends Tabla {

    public static final String TABLA = "cuidadores";

    public static final String NICK = "nick";
    public static final String ID_MASCOTA = "id_mascota";
    public static final String ACTUAL = "actual";
    public static final String DUENIO = "duenio";

    public static final String [] PROYECCION_COMPLETA = {
            ID,
            NICK,
            ID_MASCOTA,
            ACTUAL,
            DUENIO
    };

    public static final String DROP =
            "DROP TABLE IF EXISTS " + TABLA;

    public static final String CREATE =
            "CREATE TABLE " + TABLA + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    ID_MASCOTA + " INTEGER, " +
                    NICK + " VARCHAR(45) DEFAULT NULL, " +
                    ACTUAL + " BOOLEAN DEFAULT 0, " +
                    DUENIO + " BOOLEAN DEFAULT 0, " +
                    ACTUALIZADO + " BOOLEAN DEFAULT 0, " +
                    INSERTADO + " BOOLEAN DEFAULT 0, " +
                    BORRADO + " BOOLEAN DEFAULT 0 " +
                    ")";

}