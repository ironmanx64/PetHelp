package com.davidch.proyecto.pethelp.datos.tablas;

/**
 * Created by adeka on 21/10/2016.
 */

public class Mascotas extends Tabla {

    public static final String TABLA = "mascotas";

    public static final String NOMBRE = "nombre";
    public static final String APODO = "apodo";
    public static final String SEXO = "sexo";
    public static final String FECHA_REPRODUCCION = "fecha_reproduccion";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String ID_FAMILIA = "id_familia";

    public static final String [] PROYECCION_COMPLETA = {
            ID,
            NOMBRE,
            APODO,
            SEXO,
            FECHA_REPRODUCCION,
            FECHA_NACIMIENTO,
            ID_FAMILIA,
            ACTUALIZADO,
            INSERTADO,
            BORRADO
    };

    public static final String DROP =
            "DROP TABLE " + TABLA;

    public static final String CREATE =
            "CREATE TABLE " + TABLA + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOMBRE + " VARCHAR(45) DEFAULT NULL, " +
                    APODO + " VARCHAR(45) DEFAULT NULL, " +
                    SEXO + " CHAR(1) DEFAULT NULL, " +
                    FECHA_REPRODUCCION + " DATE DEAFULT NULL, " +
                    FECHA_NACIMIENTO + " DATE DEFAULT NULL, " +
                    ID_FAMILIA + " INTEGER DEFAULT NULL," +
                    ACTUALIZADO + " BOOLEAN DEFAULT FALSE, " +
                    INSERTADO + " BOOLEAN DEFAULT FALSE, " +
                    BORRADO + " BOOLEAN DEFAULT FALSE" +
                    ")";

}
