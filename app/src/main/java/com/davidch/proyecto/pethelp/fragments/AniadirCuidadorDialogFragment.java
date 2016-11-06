package com.davidch.proyecto.pethelp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.acciones.AccionesCuidador;
import com.davidch.proyecto.pethelp.modelo.Cuidador;

/**
 * Created by adeka on 04/11/2016.
 */

public class AniadirCuidadorDialogFragment extends DialogFragment
    implements DialogInterface.OnClickListener {

    private static final String ARG_ID_SERVIDOR_MASCOTA = "ID_SERVIDOR_MASCOTA";
    private long idServidorMascota;

    public static AniadirCuidadorDialogFragment crear(long idServidorMascota) {
        AniadirCuidadorDialogFragment dialog = new AniadirCuidadorDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID_SERVIDOR_MASCOTA, idServidorMascota);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idServidorMascota = getArguments().getLong(ARG_ID_SERVIDOR_MASCOTA);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getContext())
                .setTitle("Introduce el nick del cuidador")
                .setView(R.layout.nick_dialog_fragment)
                .setNegativeButton("Cancelar", this)
                .setPositiveButton("Añadir", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {

            AlertDialog alertDialog = (AlertDialog)dialog;
            TextView textViewNick = (TextView)alertDialog.findViewById(R.id.editTextNick);

            Cuidador cuidador = new Cuidador();
            cuidador.setIdServidorMascota(idServidorMascota);
            cuidador.setNick(textViewNick.getText().toString());

            new AccionesCuidador(getContext().getContentResolver(), getContext()).insertar(cuidador);
        }
        dismiss();
    }
}
