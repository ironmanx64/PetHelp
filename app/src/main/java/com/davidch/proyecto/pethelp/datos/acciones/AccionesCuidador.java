package com.davidch.proyecto.pethelp.datos.acciones;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Cuidador;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    Response<Cuidador> resultado = servicio.registrar(cuidador).execute();
                    if (resultado.isSuccessful()) {
                        cuidador.setIdCuidador(resultado.body().getIdCuidador());
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

    public void borrar(final long[] idsCuidadores) {
        new Thread() {
            @Override
            public void run() {

                ArrayList<ContentProviderOperation> operaciones = new ArrayList<>();
                for (Long id: idsCuidadores) {
                    operaciones.add(ContentProviderOperation
                            .newUpdate(PethelpContentProvider.getUriCuidador(id))
                            .withValue(Cuidadores.BORRADO, true)
                            .build());
                }

                try {
                    contentResolver.applyBatch(PethelpContentProvider.AUTORITY, operaciones);
                } catch (Exception e) {
                    Log.w(TAG, "No se pudo realizar el borrado del cuidador", e);
                }

            }
        }.start();
    }
}
