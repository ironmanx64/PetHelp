package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.davidch.proyecto.pethelp.modelo.Especie;

public class AniadirMascotaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_SELECCIONAR_IMAGEN = 0;
    private ImageView imageViewmascota;
    private EditText editTextnombremascota;
    private Spinner spinnerespecies;
    private Button buttonanidirmascotaimagen;
    private Button buttonelegirmascotaimagen;
    private Button buttonanidirmascota;
    private Adapter adaptadorespecies;
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

        buttonelegirmascotaimagen.setOnClickListener(this);

        adaptadorespecies = new ArrayAdapter<Especie>();



        spinnerespecies.setAdapter();




    }



    @Override
    public void onClick(View v) {
        if(buttonanidirmascotaimagen==v) {
            Intent intentSeleccionarImagen = new Intent(Intent.ACTION_GET_CONTENT);
            intentSeleccionarImagen.setType("image/*");
            startActivityForResult(intentSeleccionarImagen, REQUEST_CODE_SELECCIONAR_IMAGEN);
        }else if(buttonanidirmascota==v){
            String nombremascota=editTextnombremascota.getText().toString();




        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECCIONAR_IMAGEN) {

        }
    }




}
