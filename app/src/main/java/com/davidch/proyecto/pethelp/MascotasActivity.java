package com.davidch.proyecto.pethelp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.adaptadores.MascotasAdapter;
import com.davidch.proyecto.pethelp.sincronizacion.SincronizacionService;

public class MascotasActivity extends AppCompatActivity
        implements
            LoaderManager.LoaderCallbacks<Cursor>,
            MascotasAdapter.OnMascotaClickListener {

    public static final int LOADER_MASCOTAS = 1;

    private MascotasAdapter adapter;

    public static void abrirMascotasActivity(Context context) {
        Intent intent = new Intent(context, MascotasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mascotas);

        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotas);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclicerview);

        adapter = new MascotasAdapter(null, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmascotadescrip = new Intent (getBaseContext(),AniadirMascotaActivity.class);
                startActivity(intentmascotadescrip);
            }
        });

        SincronizacionService.startService(this);
        getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }



    //opcion quitada de menu puesto floating button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.aniadirmascota,menu);
        return super.onCreateOptionsMenu(menu);
        */
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.item_mas){

            Intent intentmascotadescrip = new Intent (this,AniadirMascotaActivity.class);

            startActivity(intentmascotadescrip);
        }

        return super.onOptionsItemSelected(item);


    }
    */



    @Override
    public void onMascotaClick(long id) {
        DescriptionPetActivity.abrir(this, id);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(PethelpContentProvider.getUriMascotas());
        loader.setProjection(MascotasAdapter.PROYECCION_MASCOTAS);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.switchCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.switchCursor(null);
    }
}
