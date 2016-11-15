package com.davidch.proyecto.pethelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.davidch.proyecto.pethelp.adaptadores.DetallesMascotaPagerAdapter;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;

import static com.davidch.proyecto.pethelp.R.anim.zoom_forward_in;

/**
 * Created by adeka on 09/11/2016.
 */

public class DescriptionPetActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static void abrir(Activity context, long id) {
        Intent intent = new Intent(context, com.davidch.proyecto.pethelp.DescriptionPetActivity.class);
        intent.putExtra(Mascotas.ID, id);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_pet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new DetallesMascotaPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gestion_mascota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditar:
                break;
        }
        return false;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0 && actionMode != null) {
            actionMode.finish();
            actionMode = null;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }
}
