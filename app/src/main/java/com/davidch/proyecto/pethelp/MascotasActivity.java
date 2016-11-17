package com.davidch.proyecto.pethelp;

import android.app.IntentService;
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
import com.davidch.proyecto.pethelp.servicio.ServicioMusicaAnimalia;
import com.davidch.proyecto.pethelp.sincronizacion.SincronizacionService;

import static com.davidch.proyecto.pethelp.LoginActivity.canLoadSound;
import static com.davidch.proyecto.pethelp.LoginActivity.canPlaySound;
import static com.davidch.proyecto.pethelp.LoginActivity.idSonido;
import static com.davidch.proyecto.pethelp.LoginActivity.mSound;
import static com.davidch.proyecto.pethelp.LoginActivity.savepos;
import static com.davidch.proyecto.pethelp.LoginActivity.volumenActual;
import static com.davidch.proyecto.pethelp.LoginActivity.volumenMaximo;

public class MascotasActivity extends AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MascotasAdapter.OnMascotaClickListener {

    public static final int LOADER_MASCOTAS = 1;

    private MascotasAdapter adapter;

    private AccionesMascota accionesMascota;

    protected static Intent serviciomusicaanimalia;

    public static void abrirMascotasActivity(Context context) {
        Intent intent = new Intent(context, MascotasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        if (canPlaySound && canLoadSound) {
            //reproducir el sonido
            LoginActivity.AudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volumenActual);
            mSound.play(idSonido, (float) volumenActual / volumenMaximo, (float) volumenActual / volumenMaximo, 1, 3, 1);//prioridad 1, repito 3 veces y velocidad 1
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mascotas);
        //musica de fondo animalia
        serviciomusicaanimalia=new Intent(this,ServicioMusicaAnimalia.class);
        serviciomusicaanimalia.putExtra("pos", savepos);
        startService(serviciomusicaanimalia);

        //creo la instancia del soundpool

        //obtengo el audiomanager
        LoginActivity.AudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //aviso que voy a controlar el volumen de la musica
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //averiguo volumen actual y maximo
        volumenActual = LoginActivity.AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumenMaximo = LoginActivity.AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        crearInstanciaSoundPool();
        //cargo los diferentes sonidos , en este caso solo hay uno
        idSonido = mSound.load(this, R.raw.lion, 1);
        // Asigno el listener OnLoadCompleteListener al SoundPool
        //para saber cuándo están cargados los sonidos
        mSound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                // Si el sonido ha sido cargado ya
                if (status == 0) {
                    //activo el botón play
                    canLoadSound = true;
                } else {
                    canLoadSound = false;
                    Log.i("MIAPLI", "imposible cargar el sonido");
                    finish();
                }
            }
        });

        // Oyente que escucha cambios en la adquisición del Audio Focus
        AudioManager.OnAudioFocusChangeListener afChangeListener =
                new AudioManager.OnAudioFocusChangeListener() {
                    public void onAudioFocusChange(int focusChange) {
                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                            // Pause playback
                            canPlaySound = false;
                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                            // Resume playback
                            canPlaySound = true;
                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            //desregistro el oyente de cambios en el audio focus
                            LoginActivity.AudioManager.abandonAudioFocus(this);
                            // Stop playback
                            canPlaySound = false;
                        }
                    }
                };

        //PIDO EL AUDIO FOCUS O FOCO DEL CANAL DE AUDIO
        int result = LoginActivity.AudioManager.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request transient focus (temporal).
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        // Start playback.
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //puedo reproducir sonidos
            canPlaySound = true;
        }


        accionesMascota = new AccionesMascota(this.getContentResolver());


        FloatingActionButton botonflotantemascotas = (FloatingActionButton) findViewById(R.id.buttonfloatingmascotas);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reclicerview);

        adapter = new MascotasAdapter(null, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        botonflotantemascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmascotadescrip = new Intent(getBaseContext(), AniadirMascotaActivity.class);
                startActivity(intentmascotadescrip);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        });

        SincronizacionService.startService(this);
        getSupportLoaderManager().initLoader(LOADER_MASCOTAS, null, this);
    }

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


    //libero recursos
    @Override
    protected void onPause() {
        super.onPause();
        if (mSound != null) {
            //descargo de memoria el sonido
            mSound.unload(idSonido);
            //libero el SoundPool
            mSound.release();
            mSound = null;
        }
        //descargo los efectos de sonido de las teclas de memoria
        LoginActivity.AudioManager.unloadSoundEffects();
        //pauso la musica
        stopService(serviciomusicaanimalia);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Musica de fondo arrancado
        serviciomusicaanimalia=new Intent(this,ServicioMusicaAnimalia.class);
        serviciomusicaanimalia.putExtra("pos",savepos);
        startService(serviciomusicaanimalia);
    }

    protected void onResume() {
        super.onResume();
        super.onResume();
        LoginActivity.AudioManager.loadSoundEffects();
        if (mSound != null) {
            //cargo los sonidos
            idSonido = mSound.load(this, R.raw.lion, 1);
        } else {
            crearInstanciaSoundPool();
            //cargo los sonidos
            idSonido = mSound.load(this, R.raw.lion, 1);
        }
        //Musica de fondo arrancado
        serviciomusicaanimalia=new Intent(this,ServicioMusicaAnimalia.class);
        serviciomusicaanimalia.putExtra("pos",savepos);
        startService(serviciomusicaanimalia);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(serviciomusicaanimalia);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviciomusicaanimalia);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //opcion quitada de menu puesto floating button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.aniadirmascota,menu);
        return super.onCreateOptionsMenu(menu);
        */
        getMenuInflater().inflate(R.menu.nav_menu, menu);
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
