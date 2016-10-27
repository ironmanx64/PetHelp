package com.davidch.proyecto.pethelp.sincronizacion;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.util.Log;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SincronizacionService extends IntentService {

    private static final String TAG = SincronizacionService.class.getName();

    public SincronizacionService() {
        super("SincronizacionService");
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, SincronizacionService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (hayConexionAInternet()) {
                new SincronizadorMascotas(this).sincronizar();
            }
            else {
                Log.d(TAG, "Sincronizacion interrumpida. No hay conexión.");
            }
        }
    }

    private boolean hayConexionAInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
