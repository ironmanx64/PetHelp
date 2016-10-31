package com.davidch.proyecto.pethelp.utilidades;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by adeka on 28/10/2016.
 */

public class FmtFecha {

    private java.text.DateFormat df;

    public FmtFecha(Context context) {
        df = DateFormat.getDateFormat(context);
    }

    public Date desde(String cadena) {
        if (cadena == null || cadena.trim().equals("")) {
            return null;
        }
        else {
            try {
                return df.parse(cadena);
            } catch (ParseException e) {
                Log.d(FmtFecha.class.getName(), "Formato de fecha no válido", e);
                return null;
            }
        }
    }
}
