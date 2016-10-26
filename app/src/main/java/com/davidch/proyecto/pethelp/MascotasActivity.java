package com.davidch.proyecto.pethelp;

import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.adaptadores.MascotasAdapter;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MascotasActivity extends AppCompatActivity
        implements
            LoaderManager.LoaderCallbacks<Cursor>,
            MascotasAdapter.OnMascotaClickListener {

    public static final int LOADER_MASCOTAS = 1;

    private static final String [] PROYECCION_MASCOTAS = new String [] {

    };

    private RecyclerView recyclerView;
    private FloatingActionButton botonflotantemascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mascotas);

        recyclerView=(RecyclerView)findViewById(R.id.reclicerview);
        botonflotantemascotas=(FloatingActionButton)findViewById(R.id.buttonfloatingmascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmascotadescrip = new Intent (getBaseContext(),AniadirMascotaActivity.class);

                startActivity(intentmascotadescrip);
            }
        });


        //getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }


    /*
    //opcion quitada de menu puesto floating button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aniadirmascota,menu);
        return super.onCreateOptionsMenu(menu);
    }


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
    public void onMascotaClick(Mascota mascota) {
        Intent intent = new Intent(this, DescriptionPetActivity.class);
        intent.putExtra("mascota", mascota);
        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(PethelpContentProvider.getUriMascotas());
        loader.setProjection();
        */
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
