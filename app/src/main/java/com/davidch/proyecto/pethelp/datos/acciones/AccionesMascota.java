package com.davidch.proyecto.pethelp.datos.acciones;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by adeka on 31/10/2016.
 */
public class AccionesMascota {

    private static final String TAG = AccionesMascota.class.getName();

    private ContentResolver contentResolver;

    public AccionesMascota(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void borrar(final Collection<Long> ids) {

        new Thread() {

            @Override
            public void run() {

                ArrayList<ContentProviderOperation> operaciones = new ArrayList<>();
                for (Long id: ids) {
                    operaciones.add(ContentProviderOperation
                            .newUpdate(PethelpContentProvider.getUriMascota(id))
                            .withValue(Mascotas.BORRADO, true)
                            .build());
                }

                try {
                    contentResolver.applyBatch(PethelpContentProvider.AUTORITY, operaciones);
                } catch (Exception e) {
                    Log.w(TAG, "No se pudo realizar el borrado de mascotas", e);
                }
            }
        }.start();

    }

}
