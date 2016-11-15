package com.davidch.proyecto.pethelp.datos.tablas;

/**
 * Created by adeka on 04/11/2016.
 */

public class Cuidadores extends Tabla {

    public static final String TABLA = "cuidadores";

    public static final String NICK = "nick";
    public static final String ID_MASCOTA = "id_mascota";

    public static final String [] PROYECCION_COMPLETA = {
            ID,
            NICK,
            ID_MASCOTA
    };

    public static final String DROP =
            "DROP TABLE IF EXISTS " + TABLA;

    public static final String CREATE =
            "CREATE TABLE " + TABLA + " (" +
                    ID + " INTEGER, " +
                    ID_MASCOTA + " INTEGER, " +
                    NICK + " VARCHAR(45) DEFAULT NULL, " +
                    ACTUALIZADO + " BOOLEAN DEFAULT 0, " +
                    INSERTADO + " BOOLEAN DEFAULT 0, " +
                    BORRADO + " BOOLEAN DEFAULT 0, " +
                    "PRIMARY KEY (" + ID + ", " + ID_MASCOTA + ") " +
                    ")";

}