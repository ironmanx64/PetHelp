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
    private static final int LOADER_CUIDADORES = 1;
    private static final String PARAMETRO_ID_MASCOTA = "idMascota";

    private SimpleCursorAdapter adapterCuidadores;

    private Mascota mascota = null;

    public static void abrir(Context context, long id) {
        Intent intent = new Intent(context, DescriptionPetActivity.class);
        intent.putExtra(PARAMETRO_ID_MASCOTA, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long idMascota = getIntent().getLongExtra(PARAMETRO_ID_MASCOTA, 0);

        setContentView(R.layout.activity_description_pet);
        ImageView desPetImageview=(ImageView)findViewById(R.id.imageButtonPetDes);
        Toolbar desPetToolbar=(Toolbar)findViewById(R.id.toolbarpetDes);
        ListView listViewCuidadores =(ListView)findViewById(R.id.listViewCuidadores);

        adapterCuidadores = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String [] {Cuidadores.NICK},
                new int [] {android.R.id.text1},
                0);
        listViewCuidadores.setAdapter(adapterCuidadores);

        desPetToolbar.inflateMenu(R.menu.mascota_cuidadores);

        Bundle args = new Bundle();
        args.putLong(PARAMETRO_ID_MASCOTA, idMascota);
        getSupportLoaderManager().initLoader(LOADER_MASCOTA, args, this);

        getSupportLoaderManager().initLoader(LOADER_CUIDADORES, null, this);

        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotasdescripcion);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mascota != null) {
                    DialogFragment dialog = AniadirCuidadorDialogFragment.crear(idMascota);
                    dialog.show(getSupportFragmentManager(), "aniadirCuidador");
                }
                else {
                    Toast.makeText(DescriptionPetActivity.this, "Espera a que se carge la mascota", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_CUIDADORES:
                return new CursorLoader(this,
                        PethelpContentProvider.getUriCuidadores(),
                        new String [] {Cuidadores.ID, Cuidadores.NICK},
                        null, null, null);
            case LOADER_MASCOTA:
                long idMascota = args.getLong(PARAMETRO_ID_MASCOTA);
                return new CursorLoader(this,
                        PethelpContentProvider.getUriMascota(idMascota),
                        Mascotas.PROYECCION_COMPLETA,
                        null, null, null);
            default:
                throw new RuntimeException("Id de loader no válido: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_CUIDADORES:
                adapterCuidadores.swapCursor(data);
                break;
            case LOADER_MASCOTA:
                if (data != null && data.moveToFirst()) {
                    mascota = new Mascota(data);
                    setTitle(mascota.getNombre());
                }
                else {
                    onLoaderReset(loader);
                }
                break;
            default:
                throw new RuntimeException("Id de loader no válido");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_CUIDADORES:
                adapterCuidadores.swapCursor(null);
                break;
            case LOADER_MASCOTA:
                setTitle("");
                break;
            default:
                throw new RuntimeException("Id de loader no válido");
        }
    }
}
