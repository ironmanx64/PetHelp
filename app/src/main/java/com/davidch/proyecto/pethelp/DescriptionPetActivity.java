package com.davidch.proyecto.pethelp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Mascota;

public class DescriptionPetActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_MASCOTA = 0;
    public static final String PARAMETRO_ID_MASCOTA = "idMascota";

    private ImageView desPetImageview;
    private Toolbar desPetToolbar;
    private ListView desPetListView;

    public static void abrir(Context context, long id) {
        Intent intent = new Intent(context, DescriptionPetActivity.class);
        intent.putExtra(PARAMETRO_ID_MASCOTA, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long idMascota = getIntent().getLongExtra(PARAMETRO_ID_MASCOTA, 0);

        setContentView(R.layout.activity_description_pet);
        desPetImageview=(ImageView)findViewById(R.id.imageButtonPetDes);
        desPetToolbar=(Toolbar)findViewById(R.id.toolbarpetDes);
        desPetListView=(ListView)findViewById(R.id.listViewPetDes);

        desPetToolbar.inflateMenu(R.menu.mascota_cuidadores);

        Bundle args = new Bundle();
        args.putLong(PARAMETRO_ID_MASCOTA, idMascota);
        getSupportLoaderManager().initLoader(LOADER_MASCOTA, args, this);

        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotasedescripcion);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmascotadescrip = new Intent (getBaseContext(),EditarmascotasActivity.class);
                startActivity(intentmascotadescrip);
            }
        });
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
        if (data != null && data.moveToFirst()) {
            Mascota mascota = new Mascota(data);
            setTitle(mascota.getNombre());
        }
        else {
            onLoaderReset(loader);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setTitle("");
    }
}
