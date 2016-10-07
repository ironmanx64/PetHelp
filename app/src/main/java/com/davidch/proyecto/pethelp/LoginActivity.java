package com.davidch.proyecto.pethelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.davidch.proyecto.pethelp.modelo.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText nombrelogin;
    private EditText passwordlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombrelogin=(EditText)findViewById(R.id.editTextNombrelogin);
        passwordlogin=(EditText)findViewById(R.id.editTextpasswordlogin);
        Button buttonlogin=(Button) findViewById(R.id.buttonaceptarlogin);
        Button buttonregistro=(Button) findViewById(R.id.buttonregistrarselogin);


        buttonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getBaseContext(),RegistrationActivity.class);

               LoginActivity.this.startActivity(intent);

            }
        });


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginmascotas();


                }

        });

    }

    private void loginmascotas (){

        String nombre = nombrelogin.getText().toString();
        String password = passwordlogin.getText().toString();

        new Login(this, nombre, password);

        Intent mascotasActivity = new Intent(getBaseContext(), com.davidch.proyecto.pethelp.MascotasActivity.class);
        startActivity(mascotasActivity);

    }


}
