package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.davidch.proyecto.pethelp.adaptadores.EspeciesAdapter;
import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.modelo.Especie;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.utilidades.FmtFecha;

import java.util.ArrayList;
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
    private EditText editTextFechaNacimiento;
    private EditText editTextFechaReproduccion;
    private Spinner spinnerespecies;
    private Button buttonanidirmascotaimagen;
    private Button buttonelegirmascotaimagen;
    private Button buttonAniadirMascota;
    private ArrayAdapter<Especie> adaptadorEspecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_mascota);
        imageViewmascota = (ImageView) findViewById(R.id.imagenViewanidirmascota);
        editTextnombremascota = (EditText) findViewById(R.id.editText2nombreeditarmascotas);
        editTextFechaNacimiento = (EditText) findViewById(R.id.editarmascotafechanacimientoeditext);
        editTextFechaReproduccion = (EditText) findViewById(R.id.editarmascotafechareproduccioneditext);
        spinnerespecies = (Spinner) findViewById(R.id.spinnerespiciesaniadirmascota);
        buttonanidirmascotaimagen = (Button) findViewById(R.id.button3anidirfotoanidirmascota);
        buttonelegirmascotaimagen = (Button) findViewById(R.id.button2elegirfotoeditarmascota);

        buttonAniadirMascota = (Button) findViewById(R.id.buttoneditareditarmascota);

        editTextapodomascota = (EditText)findViewById(R.id.editTextapodoanidirmascotas;

        buttonelegirmascotaimagen.setOnClickListener(this);
        buttonAniadirMascota.setOnClickListener(this);

        adaptadorEspecies = new EspeciesAdapter(this,
                new ArrayList<Especie>());
        spinnerespecies.setAdapter(adaptadorEspecies);

        FactoriaServicio.getPetHelpServicio(new Login(this))
                .getEspecies()
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
        }else if(buttonAniadirMascota==v) {

            Mascota mascota = new Mascota();

            mascota.setNombre(editTextnombremascota.getText().toString());
            mascota.setApodo(editTextapodomascota.getText().toString());
            FmtFecha fmtFecha = new FmtFecha(this);
            mascota.setFechaNacimiento(fmtFecha.desde(editTextFechaNacimiento.getText().toString()));
            mascota.setFechaReproduccion(fmtFecha.desde(editTextFechaReproduccion.getText().toString()));

            mascota.esNueva();

            AsyncQueryHandler insertQH = new AsyncQueryHandler(getContentResolver()) {};
            insertQH.startInsert(0, null, PethelpContentProvider.getUriMascotas(), mascota.toContentValues());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECCIONAR_IMAGEN) {

        }
    }




}
