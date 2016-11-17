package com.davidch.proyecto.pethelp.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;

/**
 * Created by adeka on 17/11/2016.
 */

public class CuidadoresListAdapter extends SimpleCursorAdapter
    implements SimpleCursorAdapter.ViewBinder {

    public static final String [] PROYECCION = {Cuidadores.ID, Cuidadores.NICK, Cuidadores.DUENIO, Cuidadores.ACTUAL};

    public CuidadoresListAdapter(Context context, Cursor c) {
        super(context,
                R.layout.item_cuidador,
                c,
                new String [] {Cuidadores.NICK, Cuidadores.DUENIO, Cuidadores.ACTUAL},
                new int [] {R.id.textViewCuidador, R.id.imageViewEsCuidador, R.id.imageViewEsDuenio},
                0);

        setViewBinder(this);
    }


    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

        switch (columnIndex) {
            case 1:
                return false;
            case 2:
                if (cursor.getInt(2) != 1) {
                    view.setVisibility(View.GONE);
                }
                else {
                    view.setVisibility(View.VISIBLE);
                }
                return true;
            case 3:
                if (cursor.getInt(3) != 1) {
                    view.setVisibility(View.GONE);
                }
                else {
                    view.setVisibility(View.VISIBLE);
                }
                return true;
            default:
                throw new RuntimeException("indice de columna no valido");
        }
    }
}
