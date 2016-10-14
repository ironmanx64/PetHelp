package com.davidch.proyecto.pethelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
            Callback<List<Mascota>>,
            MascotasAdapter.OnMascotaClickListener {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);
        recyclerView=(RecyclerView)findViewById(R.id.reclicerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PetHelpServicio servicio = FactoriaServicio.getPetHelpServicio();

        servicio.getMisMascotas(new Login(this))
                .enqueue(this);
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
    public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
        if (response.isSuccessful()) {
            recyclerView.setAdapter(new MascotasAdapter(response.body(), this));
        }
        else {
            Log.w(MascotasActivity.class.getName(), "Error recuperando mascotas: " + response.code());
            try {
                Log.d(MascotasActivity.class.getName(), response.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onFailure(Call<List<Mascota>> call, Throwable t) {
        Log.d(MascotasActivity.class.getName(), "Error recuperando mascotas", t);
    }

    @Override
    public void onMascotaClick(Mascota mascota) {
        Intent intent = new Intent(this, DescriptionPetActivity.class);
        intent.putExtra("mascota", mascota);
        startActivity(intent);
    }



}
