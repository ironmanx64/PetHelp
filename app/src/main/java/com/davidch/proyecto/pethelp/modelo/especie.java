package com.davidch.proyecto.pethelp.modelo;

/**
 * Created by adeka on 07/10/2016.
 */

public class Especie {

    private int id_especie;

    private String especie;

    public Especie(int id_especie, String nombre) {
        this.id_especie = id_especie;
        this.especie = especie;
    }


    public int getId_especie() {
        return id_especie;
    }

    public void setId_especie(int id_especie) {
        this.id_especie = id_especie;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String nombre) {
        this.especie = especie;
    }
}
