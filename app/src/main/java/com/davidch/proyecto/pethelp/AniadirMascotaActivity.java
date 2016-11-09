package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AniadirMascotaActivity extends AppCompatActivity
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
    private DatePickerDialog fromDatePickerDialognacimiento;
    private DatePickerDialog fromDatePickerDialogreproducion;
    private SimpleDateFormat dateFormatter;

    private AccionesMascota accionesMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aniadir_mascota);

        accionesMascota = new AccionesMascota(getContentResolver());

        imageViewFotoMascota = (ImageView) findViewById(R.id.imagenViewFotoMascota);
        editTextNombreMascota = (EditText) findViewById(R.id.editTextNombreMascota);
        editTextFechaNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editTextFechaReproduccion = (EditText) findViewById(R.id.editTextFechaReproduccion);
        editTextApodoMascota = (EditText)findViewById(R.id.editTextApodoMascota);
        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecie);
        imageViewFotoMascota = (ImageView) findViewById(R.id.imagenViewFotoMascota);

        buttonElegirFotoMascota = (Button) findViewById(R.id.buttonElegirFotoMascota);
        buttonAniadirMascota = (Button) findViewById(R.id.buttonAniadirMascota);

        buttonElegirFotoMascota.setOnClickListener(this);
        buttonAniadirMascota.setOnClickListener(this);

        setDateTimeFieldfechanacimiento();
        setDateTimeFieldfechareproduccion();

        adaptadorEspecies = new EspeciesAdapter(this, new ArrayList<Especie>());
        spinnerEspecie.setAdapter(adaptadorEspecies);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

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





    private void setDateTimeFieldfechanacimiento() {
        editTextFechaNacimiento.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialognacimiento = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextFechaNacimiento.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    };

    private void setDateTimeFieldfechareproduccion() {
        editTextFechaReproduccion.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialogreproducion = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextFechaNacimiento.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    };


    public void onClickdatepickerfechanacimiento(View view) {
        fromDatePickerDialognacimiento.show();
    }

    public void onClickdatepickerfechareproducion(View v) {
        fromDatePickerDialogreproducion.show();
    }




}
