package com.davidch.proyecto.pethelp.datos.tablas;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adeka on 21/10/2016.
 */

public class Tabla {

    private static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-dd-mm");

    public static final String ID = "_id";

    public static final String toSQL(Date date) {
        if (date == null) {
            return null;
        }
        return SQL_DATE_FORMAT.format(date);
    }

}
