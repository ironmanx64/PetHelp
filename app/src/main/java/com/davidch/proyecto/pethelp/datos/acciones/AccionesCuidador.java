package com.davidch.proyecto.pethelp.datos.acciones;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.modelo.Cuidador;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by adeka on 04/11/2016.
 */

public class AccionesCuidador {

    private static final String TAG = AccionesCuidador.class.getName();

    private ContentResolver contentResolver;
    private PetHelpServicio servicio;

    public AccionesCuidador(ContentResolver contentResolver, Context context) {
        this.contentResolver = contentResolver;
        this.servicio = FactoriaServicio.getPetHelpServicio(new Login(context));
    }

    public void insertar(final Cuidador cuidador) {
        new Thread() {
            @Override
            public void run() {

                try {
                    Response<Void> resultado = servicio.registrar(cuidador).execute();
                    if (resultado.isSuccessful()) {
                        contentResolver.insert(
                                PethelpContentProvider.getUriCuidadores(),
                                cuidador.toContentValues());
                    }
                    else {
                        Log.w(TAG, "Error al registrar cuidador (código http no válido " + resultado.code() + ")");
                    }

                } catch (IOException e) {
                    Log.w(TAG, "Error al registrar cuidador", e);
                }
            }
        }.start();
    }
}