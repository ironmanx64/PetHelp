package com.davidch.proyecto.pethelp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.davidch.proyecto.pethelp.modelo.Mascota;

public class DescriptionPetActivity extends AppCompatActivity {
    private ImageView desPetImageview;
    private Toolbar desPetToolbar;
    private ListView desPetListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mascota mascota = getIntent().getExtras().getParcelable("mascota");

        setTitle(mascota.getNombre());

        setContentView(R.layout.activity_description_pet);
        desPetImageview=(ImageView)findViewById(R.id.imageButtonPetDes);
        desPetToolbar=(Toolbar)findViewById(R.id.toolbarpetDes);
        desPetListView=(ListView)findViewById(R.id.listViewPetDes);

        desPetToolbar.inflateMenu(R.menu.mascota_cuidadores);
    }



}
