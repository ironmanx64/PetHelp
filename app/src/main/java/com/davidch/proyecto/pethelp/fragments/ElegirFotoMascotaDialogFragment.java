package com.davidch.proyecto.pethelp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.acciones.AccionesCuidador;
import com.davidch.proyecto.pethelp.modelo.Cuidador;

import java.io.File;
import java.text.SimpleDateFormat;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by hp on 09/11/2016.
 */

public class ElegirFotoMascotaDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {
    public static final int REQUEST_CODE_SELECCIONAR_IMAGEN = 0;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 0;
    private Uri fileUri;

    public static AniadirCuidadorDialogFragment crear(long idServidorMascota) {
        AniadirCuidadorDialogFragment dialog = new AniadirCuidadorDialogFragment();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getContext())
                .setTitle("Elige como poner la foto de la mascota :D")
                .setNegativeButton("Camara", this)
                .setPositiveButton("Galeria", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {

            Intent intentSeleccionarImagen = new Intent(Intent.ACTION_GET_CONTENT);
            intentSeleccionarImagen.setType("image/*");
            startActivityForResult(intentSeleccionarImagen, REQUEST_CODE_SELECCIONAR_IMAGEN);

        }else if(which == DialogInterface.BUTTON_NEGATIVE){

            Intent intentTomarfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // start the image capture Intent
            startActivityForResult(intentTomarfoto, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }

        dismiss();
    }

}

