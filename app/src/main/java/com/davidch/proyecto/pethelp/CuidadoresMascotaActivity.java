package com.davidch.proyecto.pethelp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.fragments.AniadirCuidadorDialogFragment;
import com.davidch.proyecto.pethelp.modelo.Cuidador;

public class CuidadoresMascotaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_MASCOTA = "idMascota";
    public static final int LOADER_CUIDADORES = 1;

    public static void abrir(Context context, long idMascota) {
        Intent intent = new Intent(context, CuidadoresMascotaActivity.class);
        intent.putExtra(ID_MASCOTA, idMascota);
        context.startActivity(intent);
    }

    private CursorAdapter adapterCuidadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidadores_mascota);

        final long idMascota = getIntent().getLongExtra(ID_MASCOTA, -1);

        Bundle datos = new Bundle();
        datos.putLong(ID_MASCOTA, idMascota);
        getLoaderManager().initLoader(LOADER_CUIDADORES, datos, this);

        adapterCuidadores = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String [] {Cuidadores.NICK},
                new int [] {android.R.id.text1},
                0);
        ListView listViewCuidadres = (ListView)findViewById(R.id.listViewCuidadores);
        listViewCuidadres.setAdapter(adapterCuidadores);


        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotasedescripcion);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = AniadirCuidadorDialogFragment.crear(idMascota);
                dialog.show(getSupportFragmentManager(), "aniadirCuidador");
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long idMascota = args.getLong(ID_MASCOTA);
        return new CursorLoader(this,
                PethelpContentProvider.getUriCuidadores(),
                Cuidadores.PROYECCION_COMPLETA,
                Cuidadores.ID_MASCOTA + "=?",
                new String [] {Long.toString(idMascota)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterCuidadores.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterCuidadores.swapCursor(null);
    }
}
