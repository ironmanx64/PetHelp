package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.davidch.proyecto.pethelp.adaptadores.EspeciesAdapter;
import com.davidch.proyecto.pethelp.modelo.Especie;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.servicio.PetHelpServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AniadirMascotaActivity extends AppCompatActivity
        implements View.OnClickListener, Callback<List<Especie>> {
    public static final int REQUEST_CODE_SELECCIONAR_IMAGEN = 0;
    private ImageView imageViewmascota;
    private EditText editTextnombremascota;
    private EditText editTextapodomascota;
    private Spinner spinnerespecies;
    private Button buttonanidirmascotaimagen;
    private Button buttonelegirmascotaimagen;
    private Button buttonanidirmascota;
    private ArrayAdapter<Especie> adaptadorEspecies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_mascota);
        imageViewmascota = (ImageView) findViewById(R.id.imagenViewanidirmascota);
        editTextnombremascota = (EditText) findViewById(R.id.editText2nombreanidirmascotas);
        spinnerespecies = (Spinner) findViewById(R.id.spinnerespiciesaniadirmascota);
        buttonanidirmascotaimagen = (Button) findViewById(R.id.button3anidirfotoanidirmascota);
        buttonelegirmascotaimagen = (Button) findViewById(R.id.button2elegirfotoanidirmascota);
        buttonanidirmascota = (Button) findViewById(R.id.buttonanidiranidirmascota);
        editTextapodomascota = (EditText)findViewById(R.id.editTextapodo);

        buttonelegirmascotaimagen.setOnClickListener(this);

        adaptadorEspecies = new EspeciesAdapter(this,
                new ArrayList<Especie>());
        spinnerespecies.setAdapter(adaptadorEspecies);

        FactoriaServicio.getPetHelpServicio()
                .getEspecies(new Login(this))
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Especie>> call, Response<List<Especie>> response) {
        if (response.isSuccessful()) {
            adaptadorEspecies.clear();
            adaptadorEspecies.addAll(response.body());
        }
        else {
            Log.w(AniadirMascotaActivity.class.getName(), "Error al recuperar especies: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<List<Especie>> call, Throwable t) {
        Log.w(AniadirMascotaActivity.class.getName(), "Error al recuperar especies", t);
    }

    @Override
    public void onClick(View v) {
        if(buttonanidirmascotaimagen==v) {
            Intent intentSeleccionarImagen = new Intent(Intent.ACTION_GET_CONTENT);
            intentSeleccionarImagen.setType("image/*");
            startActivityForResult(intentSeleccionarImagen, REQUEST_CODE_SELECCIONAR_IMAGEN);
        }else if(buttonanidirmascota==v){
            String nombremascota=editTextnombremascota.getText().toString();
            String apodomascota=editTextapodomascota.getText().toString();




        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECCIONAR_IMAGEN) {

        }
    }




}
