package com.davidch.proyecto.pethelp;

import android.content.AsyncQueryHandler;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;

import com.davidch.proyecto.pethelp.adaptadores.DrawerListAdapter;
import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.adaptadores.MascotasAdapter;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.fragments.ArticleFragment;
import com.davidch.proyecto.pethelp.modelovista.DrawerItem;
import com.davidch.proyecto.pethelp.servicio.IntentServicemusicaanimalia;
import com.davidch.proyecto.pethelp.sincronizacion.SincronizacionService;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MascotasActivity extends AppCompatActivity
        implements
            LoaderManager.LoaderCallbacks<Cursor>,
            MascotasAdapter.OnMascotaClickListener {

    public static final int LOADER_MASCOTAS = 1;

    private MascotasAdapter adapter;

    private AsyncQueryHandler accionesDatos;



    private String[] tagTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;

    public static void abrirMascotasActivity(Context context) {
        Intent intent = new Intent(context, MascotasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Obtener arreglo de strings desde los recursos
        tagTitles = getResources().getStringArray(R.array.Tags);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerList = (ListView) findViewById(R.id.left_drawer);

        //Nueva lista de drawer items
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0],R.drawable.hamster1));
        items.add(new DrawerItem(tagTitles[1],R.drawable.hamster2));
        items.add(new DrawerItem(tagTitles[2],R.drawable.hamster3));
        items.add(new DrawerItem(tagTitles[3],R.drawable.hamster4));
        items.add(new DrawerItem(tagTitles[4],R.drawable.hamster5));
        items.add(new DrawerItem(tagTitles[5],R.drawable.hamster6));

        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));



        setContentView(R.layout.activity_mascotas);

        accionesDatos = new AsyncQueryHandler(getContentResolver()) {};

        IntentServicemusicaanimalia.startMusica(getBaseContext());

        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotas);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclicerview);

        adapter = new MascotasAdapter(null, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmascotadescrip = new Intent (getBaseContext(),AniadirMascotaActivity.class);
                startActivity(intentmascotadescrip);
            }
        });

        SincronizacionService.startService(this);
        getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }

    private void selectItem(int position) {
        // Crear nuevo fragmento
        Fragment fragment = new ArticleFragment();
        //Mandar como argumento la posición del item
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_ARTICLES_NUMBER, position);
        fragment.setArguments(args);

        //Reemplazar contenido
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    //opcion quitada de menu puesto floating button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.aniadirmascota,menu);
        return super.onCreateOptionsMenu(menu);
        */
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }



    CustomActionBarDrawerToggle(Activity mActivity,
                                       DrawerLayout mDrawerLayout) {
        super(mActivity, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close);

    }


    public void onDrawerClosed(View view) {
            //Acciones que se ejecutan cuando se cierra el drawer
    }

    public void onDrawerOpened(View drawerView) {
        //Acciones que se ejecutan cuando se despliega el drawer
    }
    };
    //Seteamos la escucha
    drawerLayout.setDrawerListener(drawerToggle);

            drawer.setDrawerListener(toggle);

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.item_mas){

            Intent intentmascotadescrip = new Intent (this,AniadirMascotaActivity.class);

            startActivity(intentmascotadescrip);
        }

        return super.onOptionsItemSelected(item);


    }
    */



    @Override
    public void onMascotaClick(long id) {
        DescriptionPetActivity.abrir(this, id);
    }

    @Override
    public void onMascotaLongClick(long id) {
        startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                new MenuInflater(MascotasActivity.this).inflate(R.menu.gestion_macotas, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menuItemBorrar) {

                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.salirDeSeleccion();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(PethelpContentProvider.getUriMascotas());
        loader.setProjection(MascotasAdapter.PROYECCION_MASCOTAS);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.switchCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.switchCursor(null);
    }
}
