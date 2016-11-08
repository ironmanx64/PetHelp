package com.davidch.proyecto.pethelp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.fragments.AniadirCuidadorDialogFragment;
import com.davidch.proyecto.pethelp.modelo.Cuidador;
import com.davidch.proyecto.pethelp.modelo.Mascota;

public class DescriptionPetActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_MASCOTA = 0;
    private static final String PARAMETRO_ID_MASCOTA = "idMascota";

    private long idMascota;
    private Mascota mascota = null;

    public static void abrir(Context context, long id) {
        Intent intent = new Intent(context, DescriptionPetActivity.class);
        intent.putExtra(PARAMETRO_ID_MASCOTA, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idMascota = getIntent().getLongExtra(PARAMETRO_ID_MASCOTA, 0);

        setContentView(R.layout.activity_descripcion_pet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = new Bundle();
        args.putLong(PARAMETRO_ID_MASCOTA, idMascota);
        getSupportLoaderManager().initLoader(LOADER_MASCOTA, args, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mascota_cuidadores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemCuidadores:
                CuidadoresMascotaActivity.abrir(this, idMascota);
                break;
        }
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long idMascota = args.getLong(PARAMETRO_ID_MASCOTA);
        return new CursorLoader(this,
                PethelpContentProvider.getUriMascota(idMascota),
                Mascotas.PROYECCION_COMPLETA,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            mascota = new Mascota(data);
            setTitle(mascota.getNombre());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setTitle("");
    }
}
