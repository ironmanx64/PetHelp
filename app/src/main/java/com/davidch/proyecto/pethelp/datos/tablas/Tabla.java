package com.davidch.proyecto.pethelp.datos.tablas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adeka on 21/10/2016.
 */

public class Tabla {

    private static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-dd-mm");

    public static final String ID = "_id";
    public static final String ACTUALIZADO = "actualizado";
    public static final String INSERTADO = "insertado";
    public static final String BORRADO = "borrado";

    public static final String dateToSQL(Date date) {
        if (date == null) {
            return null;
        }
        return SQL_DATE_FORMAT.format(date);
    }

    public static final Date dateFromSQL(String date) {
        if (date == null) {
            return null;
        }
        try {
            return SQL_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
