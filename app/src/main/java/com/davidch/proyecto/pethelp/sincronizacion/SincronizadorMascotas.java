package com.davidch.proyecto.pethelp.sincronizacion;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by adeka on 24/10/2016.
 */

public class SincronizadorMascotas {

    public static final String TAG = SincronizadorMascotas.class.getName();
    private ContentResolver resolver;
    private PetHelpServicio sw;

    public SincronizadorMascotas(Context context) {
        resolver = context.getContentResolver();
        sw = FactoriaServicio.getPetHelpServicio(new Login(context));
    }

    private Cursor buscarPendientesActualizar() {
        return resolver.query(
                PethelpContentProvider.getUriMascotas(),
                Mascotas.PROYECCION_COMPLETA,
                Mascotas.ACTUALIZADO + "=1",
                null,
                null);
    }

    private Cursor buscarPendientesInsertar() {
        return resolver.query(
                PethelpContentProvider.getUriMascotas(),
                Mascotas.PROYECCION_COMPLETA,
                Mascotas.INSERTADO + "=1",
                null,
                null);
    }

    private Cursor buscarPendientesBorrar() {
        return resolver.query(
                PethelpContentProvider.getUriMascotas(),
                Mascotas.PROYECCION_COMPLETA,
                Mascotas.BORRADO + "=1",
                null,
                null);
    }

    public void sincronizar() {

        List<Mascota> actualizables = Mascota.fromCursor(buscarPendientesActualizar());
        List<Mascota> insertables = Mascota.fromCursor(buscarPendientesInsertar());
        List<Mascota> borrables = Mascota.fromCursor(buscarPendientesBorrar());

        Map<String, List<Mascota>> datos = new HashMap<>();
        datos.put("actualizables", actualizables);
        datos.put("insertables", insertables);
        datos.put("borrables", borrables);

        try {
            Response<List<Mascota>> mascotas = sw.sincronizarMascotas(datos)
                    .execute();

            if (mascotas.isSuccessful()) {
                resolver.delete(PethelpContentProvider.getUriMascotas(), null, null);
                ContentValues[] cvs = new ContentValues [mascotas.body().size()];
                int i = 0;
                for (Mascota mascota: mascotas.body()) {
                    cvs[i++] = mascota.toContentValues();
                }
                resolver.bulkInsert(PethelpContentProvider.getUriMascotas(), cvs);
            }
            else {
                Log.w(TAG, "Error al sincronizar en servidor (Código HTTP " + mascotas.code());
            }

        } catch (IOException e) {
            Log.w(TAG, "Error al sincronizar en servidor", e);
        }

    }
}
