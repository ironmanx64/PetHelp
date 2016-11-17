package com.davidch.proyecto.pethelp.servicio;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.davidch.proyecto.pethelp.R;

/**
 * Created by hp on 17/11/2016.
 */

public class ServicioMusicaAnimalia extends IntentService {
    final static String ACCION_ENVIAR_POS_ACTUAL_PLAYER = "ACCION_ENVIAR_POS_ACTUAL_PLAYER";
    MediaPlayer player;
    private int pos;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServicioMusicaAnimalia(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        pos=intent.getIntExtra("pos",0);
        player.seekTo(pos);
        player.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.musicanimaliaogg);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return flags;
    }

    @Override
    public void onStart(Intent intent, int startId) {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        if(player!=null&&player.isPlaying()){
            pos=player.getCurrentPosition();
            player.pause();

        }
        Intent intent = new Intent();
        intent.setAction(ACCION_ENVIAR_POS_ACTUAL_PLAYER);
        intent.putExtra("pos",pos);
        sendBroadcast(intent);
    }

    @Override
    public void onLowMemory() {

    }

}
