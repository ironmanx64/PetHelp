package com.davidch.proyecto.pethelp;

import android.content.Intent;
import android.database.Cursor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mascotas);

        recyclerView=(RecyclerView)findViewById(R.id.reclicerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }

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

    @Override
    public void onMascotaClick(Mascota mascota) {
        Intent intent = new Intent(this, DescriptionPetActivity.class);
        intent.putExtra("mascota", mascota);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(PethelpContentProvider.getUriMascotas());
        loader.setProjection();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
