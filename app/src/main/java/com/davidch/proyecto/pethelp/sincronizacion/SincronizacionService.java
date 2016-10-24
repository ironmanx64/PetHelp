package com.davidch.proyecto.pethelp.sincronizacion;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
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

    private static final String ACTION_MASCOTAS = "com.davidch.proyecto.pethelp.sincronizacion.action.MASCOTAS";

    public SincronizacionService() {
        super("SincronizacionService");
    }

    public static void startActionMascotas(Context context) {
        Intent intent = new Intent(context, SincronizacionService.class);
        intent.setAction(ACTION_MASCOTAS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            switch (intent.getAction()) {
                case ACTION_MASCOTAS:
                    new SincronizadorMascotas(this).sincronizar();
                    break;
            }
        }
    }

}
