package com.davidch.proyecto.pethelp.sincronizacion;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Cuidador;
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
 * Created by adeka on 15/11/2016.
 */

public class SincronizacionCuidadores {

    public static final String TAG = SincronizadorMascotas.class.getName();
    private ContentResolver resolver;
    private PetHelpServicio sw;

    public SincronizacionCuidadores(Context context) {
        resolver = context.getContentResolver();
        sw = FactoriaServicio.getPetHelpServicio(new Login(context));
    }

    private Cursor buscarPendientesBorrar() {
        return resolver.query(
                PethelpContentProvider.getUriCuidadores(),
                Cuidadores.PROYECCION_COMPLETA,
                Cuidadores.BORRADO + "=1",
                null,
                null);
    }

    public void sincronizar() {
        /*
        List<Cuidador> borrables = Cuidador.fromCursor(buscarPendientesBorrar());

        Map<String, List<Cuidador>> datos = new HashMap<>();
        datos.put("borrables", borrables);

        try {
            sw.borrarCuidadores(datos).execute();

        } catch (IOException e) {
            Log.w(TAG, "Error al sincronizar en servidor", e);
        }*/

    }
}
