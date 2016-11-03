package com.davidch.proyecto.pethelp.datos.acciones;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adeka on 31/10/2016.
 */

public class AccionesMascota {

    private ContentResolver contentResolver;

    public AccionesMascota(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void borrar(List<Long> ids) {

        ArrayList<ContentProviderOperation> operaciones = new ArrayList<>();
        for (Long id: ids) {
            operaciones.add(ContentProviderOperation
                    .newDelete(PethelpContentProvider.getUriMascotas())
                    .withSelection(Mascotas.ID + " = ?", new String [] {id.toString()})
                    .build());
        }

        try {
            contentResolver.applyBatch(PethelpContentProvider.AUTORITY, operaciones);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

}
