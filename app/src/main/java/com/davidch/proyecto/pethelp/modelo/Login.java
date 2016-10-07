package com.davidch.proyecto.pethelp.modelo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adeka on 19/08/2016.
 */
public class Login {

    private String nick;
    private String clave;

    public Login(Context context, String nick, String clave) {
        SharedPreferences preferencias = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        preferencias.edit()
                .putString("nick", nick)
                .putString("clave", clave)
                .commit();

        this.nick = nick;
        this.clave = clave;
    }

    public Login(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        nick = preferencias.getString("nick", "");
        clave = preferencias.getString("clave", "");
    }

    public String getClave() {
        return clave;
    }

    public String getNick() {
        return nick;
    }
}
