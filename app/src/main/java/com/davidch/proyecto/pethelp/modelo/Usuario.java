package com.davidch.proyecto.pethelp.modelo;

/**
 * Created by adeka on 18/08/2016.
 */
public class Usuario {

    private String nick;

    private String password;

    private String email;

    public Usuario(String nick, String password, String email) {
        this.nick = nick;
        this.password = password;
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
