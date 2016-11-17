package com.davidch.proyecto.pethelp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.servicio.ServicioMusicaAnimalia;

import static com.davidch.proyecto.pethelp.MascotasActivity.serviciomusicaanimalia;

public class LoginActivity extends AppCompatActivity {

    private EditText nombrelogin;
    private EditText passwordlogin;
    private ProgressBar barradeprogresocircular;
    static int savepos=0;
    protected static android.media.AudioManager AudioManager;
    protected static int volumenActual, volumenMaximo;
    protected static SoundPool mSound;
    protected static int idSonido;
    protected static boolean canPlaySound = false; //me dice si tengo el auto focus
    protected static boolean canLoadSound = false; //me dice si tengo el sonido cargado
    private Button buttonlogin;
    private Button buttonregistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //musica de fondo animalia
        serviciomusicaanimalia=new Intent(this,ServicioMusicaAnimalia.class);
        serviciomusicaanimalia.putExtra("pos",savepos);
        startService(serviciomusicaanimalia);


        Login login = new Login(this);
        if (login.isLogged()) {

            MascotasActivity.abrirMascotasActivity(this);
        }
        else {

            setContentView(R.layout.activity_login);

            nombrelogin = (EditText) findViewById(R.id.editTextNombrelogin);
            passwordlogin = (EditText) findViewById(R.id.editTextpasswordlogin);
            buttonlogin = (Button) findViewById(R.id.buttonaceptarlogin);
            buttonregistro = (Button) findViewById(R.id.buttonregistrarselogin);
            barradeprogresocircular= (ProgressBar)findViewById(R.id.progressBarLogin);


            buttonregistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);

                    LoginActivity.this.startActivity(intent);

                }
            });


            buttonlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barradeprogresocircular.setVisibility(View.VISIBLE);
                    loginmascotas();


                }

            });
        }

    }



    private void loginmascotas (){

        String nombre = nombrelogin.getText().toString();
        String password = passwordlogin.getText().toString();

        new Login(this, nombre, password);

        MascotasActivity.abrirMascotasActivity(this);
    }


}
