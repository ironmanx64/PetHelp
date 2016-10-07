package com.davidch.proyecto.pethelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davidch.proyecto.pethelp.modelo.Usuario;
import com.davidch.proyecto.pethelp.servicio.FactoriaServicio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity
    implements
        Callback<Void> {

    private EditText nickregistration;
    private EditText passwordregistration;
    private EditText emailregistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_activity);
        Button registarregistration=(Button)findViewById(R.id.buttonregistation);
        nickregistration=(EditText)findViewById(R.id.editTextnickregistration);
        passwordregistration=(EditText)findViewById(R.id.editTextpasswordregistration);
        emailregistration=(EditText)findViewById(R.id.editTextemailregistration);

        registarregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }

    private void registrar() {
        String nick=nickregistration.getText().toString();

        String password=passwordregistration.getText().toString();

        String email=emailregistration.getText().toString();

        Usuario usuario = new Usuario(nick,password,email);

        FactoriaServicio.getPetHelpServicio().registrar(usuario)
            .enqueue(this);

    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Toast.makeText(this, "El usuario se registro", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Log.w(RegistrationActivity.class.getName(), response.message());
            Toast.makeText(this, "Error al registrar, intentalo más tarde", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.w(RegistrationActivity.class.getName(), "Error al registrar", t);
        Toast.makeText(this, "Error al registrar, intentalo más tarde", Toast.LENGTH_LONG).show();
    }
}
