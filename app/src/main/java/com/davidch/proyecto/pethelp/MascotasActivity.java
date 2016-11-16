package com.davidch.proyecto.pethelp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.adaptadores.MascotasAdapter;
import com.davidch.proyecto.pethelp.datos.acciones.AccionesMascota;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.servicio.IntentServicemusicaanimalia;
import com.davidch.proyecto.pethelp.sincronizacion.SincronizacionService;

import java.util.ArrayList;
import java.util.Random;

public class MascotasActivity extends AppCompatActivity
        implements
            LoaderManager.LoaderCallbacks<Cursor>,
            MascotasAdapter.OnMascotaClickListener {

    public static final int LOADER_MASCOTAS = 1;

    protected static AudioManager am;

    private int volumenActual, volumenMaximo;

    private MascotasAdapter adapter;

    private AccionesMascota accionesMascota;

    private SoundPool mSound;

    private int idSonido;

    private static int aleatorio;

    private static String[] longitudsonidosanimales;

    String cadena;

    private boolean canPlaySound = false; //me dice si tengo el auto focus

    public static void abrirMascotasActivity(Context context) {
        Intent intent = new Intent(context, MascotasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mascotas);

        //averiguo volumen actual y máximo
        volumenActual = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumenMaximo = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);


        //cargo los diferentes sonidos. En este caso sólo hay uno

        idSonido=mSound.load(this,R.raw.lion,1);

        accionesMascota = new AccionesMascota(this.getContentResolver());

        IntentServicemusicaanimalia.startMusica(getBaseContext());

        FloatingActionButton botonflotantemascotas = (FloatingActionButton)findViewById(R.id.buttonfloatingmascotas);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclicerview);

        adapter = new MascotasAdapter(null, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canPlaySound){
                    //puedo reproducir sonido
                    am.playSoundEffect(AudioManager.FX_KEY_CLICK, volumenActual);
                    mSound.play(idSonido,
                            (float)volumenActual/volumenMaximo,
                            (float)volumenActual/volumenMaximo,
                            1,3,1);//prioridad 1, repito 3 veces y velocidad 1
                }
                Intent intentmascotadescrip = new Intent (getBaseContext(),AniadirMascotaActivity.class);
                startActivity(intentmascotadescrip);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        });

        SincronizacionService.startService(this);
        getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }


    //libero recursos
    @Override
    protected void onPause() {
        super.onPause();
        if (mSound !=null) {
        //descargo de memoria el sonido
            mSound.unload(idSonido);
            //libero el SoundPool
            mSound.release();
            mSound = null;
        }
        //descargo los efectos de sonido de las teclas de memoria
        am.unloadSoundEffects();
    }

    /** Método que crea una instancia de SoundPool adaptada a la versión de SO que
     * tengamos
     *
     */

    private void crearInstanciaSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSound = new SoundPool.Builder()
                    .setAudioAttributes(audioAttrib)
                    .setMaxStreams(6)
                    .build();
        } else {
            mSound = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
    }

    protected void onResume() {
        super.onResume();
        am.loadSoundEffects();
        if (mSound !=null) {
            //cargo los sonidos
            idSonido=mSound.load(this,R.raw.lion,1);
        }else{
            crearInstanciaSoundPool();
            //cargo los sonidos
            idSonido=mSound.load(this,R.raw.lion,1);
        }
    }

    // Oyente que escucha cambios en la adquisición del Audio Focus
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback
                        canPlaySound=false;
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        canPlaySound=true;
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        am.abandonAudioFocus(this);
                        // Stop playback
                        canPlaySound=false;
                    }
                }
            };



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
                new MenuInflater(MascotasActivity.this).inflate(R.menu.gestion_mascotas, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menuItemBorrar) {
                    if(canPlaySound){
                        //puedo reproducir sonido
                        am.playSoundEffect(AudioManager.FX_KEY_CLICK, volumenActual);
                        mSound.play(idSonido,
                                (float)volumenActual/volumenMaximo,
                                (float)volumenActual/volumenMaximo,
                                1,3,1);//prioridad 1, repito 3 veces y velocidad 1
                    }
                    accionesMascota.borrar(adapter.getIdsSeleccionados());
                    mode.finish();
                    return true;
                }
                return false;
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
        loader.setSelection(Mascotas.BORRADO + " = 0");
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
