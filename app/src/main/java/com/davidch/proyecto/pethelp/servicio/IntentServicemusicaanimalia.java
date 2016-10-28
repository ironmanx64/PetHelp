package com.davidch.proyecto.pethelp.servicio;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;

import com.davidch.proyecto.pethelp.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServicemusicaanimalia extends IntentService {
    MediaPlayer mediaPlayer;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.davidch.proyecto.pethelp.servicio.action.FOO";
    private static final String ACTION_BAZ = "com.davidch.proyecto.pethelp.servicio.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.davidch.proyecto.pethelp.servicio.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.davidch.proyecto.pethelp.servicio.extra.PARAM2";

    public IntentServicemusicaanimalia() {
        super("IntentServicemusicaanimalia");
    }


    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startMusica(Context context) {
        Intent intent = new Intent(context, IntentServicemusicaanimalia.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            mediaPlayer = MediaPlayer.create(this, R.raw.musicanimaliaogg);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.start();
    }

}
