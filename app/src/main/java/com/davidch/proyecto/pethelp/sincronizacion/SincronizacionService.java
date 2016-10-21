package com.davidch.proyecto.pethelp.sincronizacion;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
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

            PetHelpServicio servicio = FactoriaServicio.getPetHelpServicio(new Login(this));

            switch (intent.getAction()) {
                case ACTION_MASCOTAS:
                    handleActionMascotas(servicio);
                    break;
            }
        }
    }

    private void handleActionMascotas(PetHelpServicio servicio) {

        try {
            Response<List<Mascota>> mascotas = servicio.getMisMascotas().execute();
            if (mascotas.isSuccessful()) {
                getContentResolver().delete(PethelpContentProvider.getUriMascotas(), null, null);
                ContentValues [] cvs = new ContentValues [mascotas.body().size()];
                int i = 0;
                for (Mascota mascota: mascotas.body()) {
                    cvs[i++] = mascota.toContentValues();
                }
                getContentResolver().bulkInsert(PethelpContentProvider.getUriMascotas(), cvs);
            }
            else {
                Log.e(TAG, "No se pudieron sincronizar las mascotas (resultado http " + mascotas.code() + ")");
            }

        } catch (IOException e) {
            Log.e(TAG, "No se pudieron sincronizar las mascotas", e);
        }

    }

}
