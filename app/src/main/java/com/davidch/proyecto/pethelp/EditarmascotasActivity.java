package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.davidch.proyecto.pethelp.adaptadores.EspeciesAdapter;
import com.davidch.proyecto.pethelp.datos.acciones.AccionesMascota;
import com.davidch.proyecto.pethelp.modelo.Especie;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;
import com.davidch.proyecto.pethelp.utilidades.FmtFecha;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.davidch.proyecto.pethelp.R.id.buttonAniadirMascota;
import static com.davidch.proyecto.pethelp.R.id.buttonElegirFotoMascota;
import static com.davidch.proyecto.pethelp.R.id.editTextApodoMascota;
import static com.davidch.proyecto.pethelp.R.id.editTextFechaNacimiento;
import static com.davidch.proyecto.pethelp.R.id.editTextFechaReproduccion;
import static com.davidch.proyecto.pethelp.R.id.editTextNombreMascota;
import static com.davidch.proyecto.pethelp.R.id.imageViewFotoMascota;
import static com.davidch.proyecto.pethelp.R.id.spinnerEspecie;

/**
 * Created by hp on 03/11/2016.
 */

public class EditarmascotasActivity extends AppCompatActivity
        implements View.OnClickListener, Callback<List<Especie>> {

    public static final int REQUEST_CODE_SELECCIONAR_IMAGEN = 0;
    private ImageView imageViewFotoMascota;
    private EditText editTextNombreMascota;
    private EditText editTextApodoMascota;
    private EditText editTextFechaNacimiento;
    private EditText editTextFechaReproduccion;
    private Spinner spinnerEspecie;
    private Button buttonElegirFotoMascota;
    private Button buttonAniadirMascota;
    private ArrayAdapter<Especie> adaptadorEspecies;

    private AccionesMascota accionesMascota;

    private Uri mImageUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_mascota);

        accionesMascota = new AccionesMascota(getContentResolver());

        imageViewFotoMascota = (ImageView) findViewById(R.id.imagenViewFotoMascota);
        editTextNombreMascota = (EditText) findViewById(R.id.editTextNombreMascota);
        editTextFechaNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editTextFechaReproduccion = (EditText) findViewById(R.id.editTextFechaReproduccion);
        editTextApodoMascota = (EditText)findViewById(R.id.editTextApodoMascota);
        spinnerEspecie = (Spinner) findViewById(R.id.spinnerespicieseditarmascota);
        imageViewFotoMascota = (ImageView) findViewById(R.id.imagenViewFotoMascota);

        buttonElegirFotoMascota = (Button) findViewById(R.id.buttonElegirFotoMascota);
        buttonAniadirMascota = (Button) findViewById(R.id.buttonAniadirMascota);

        buttonElegirFotoMascota.setOnClickListener(this);
        buttonAniadirMascota.setOnClickListener(this);

        adaptadorEspecies = new EspeciesAdapter(this, new ArrayList<Especie>());
        spinnerEspecie.setAdapter(adaptadorEspecies);

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
        if (buttonElegirFotoMascota == v) {
            Intent intentSeleccionarImagen = new Intent(Intent.ACTION_GET_CONTENT);
            intentSeleccionarImagen.setType("image/*");
            startActivityForResult(intentSeleccionarImagen, REQUEST_CODE_SELECCIONAR_IMAGEN);

        } else if (buttonAniadirMascota == v) {

            Mascota mascota = new Mascota();

            mascota.setNombre(editTextNombreMascota.getText().toString());
            mascota.setApodo(editTextApodoMascota.getText().toString());
            FmtFecha fmtFecha = new FmtFecha(this);
            mascota.setFechaNacimiento(fmtFecha.desde(editTextFechaNacimiento.getText().toString()));
            mascota.setFechaReproduccion(fmtFecha.desde(editTextFechaReproduccion.getText().toString()));

            mascota.esNueva();

            accionesMascota.insertar(mascota);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECCIONAR_IMAGEN) {
            Bitmap imagenmascota = (Bitmap) data.getExtras().get("data");
            imageViewFotoMascota.setImageBitmap(imagenmascota);

        }

    }

    public void onClickdatepickerfechanacimiento(View v) {
        DatePickerDialog.OnDateSetListener datenacimientodialog = new DatePickerDialog.OnDateSetListener() {
            Calendar calendarioDatePicker = new GregorianCalendar();

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                calendarioDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendarioDatePicker.set(Calendar.MONTH, monthOfYear);
                calendarioDatePicker.set(Calendar.DAY_OF_MONTH, year);
                updateLabel();
            }

            private void updateLabel() {
                Date fechaDatePicker = calendarioDatePicker.getTime();

                String dateString = DateFormat.getDateInstance().format(fechaDatePicker);

                editTextFechaNacimiento.setText(dateString);
            }

        };
    }

    public void onClickdatepickerfechareproducion(View v) {
        DatePickerDialog.OnDateSetListener datereproducciondialog = new DatePickerDialog.OnDateSetListener() {
            Calendar calendarioDatePicker = new GregorianCalendar();

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                calendarioDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendarioDatePicker.set(Calendar.MONTH, monthOfYear);
                calendarioDatePicker.set(Calendar.DAY_OF_MONTH, year);
                updateLabel();
            }

            private void updateLabel() {
                Date fechaDatePicker = calendarioDatePicker.getTime();

                String dateString = DateFormat.getDateInstance().format(fechaDatePicker);

                editTextFechaReproduccion.setText(dateString);
            }

        };
    }


}
