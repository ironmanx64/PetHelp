package com.davidch.proyecto.pethelp.modelo;

/**
 * Created by adeka on 07/10/2016.
 */

public class Especie {

    int id_especie;

    String nombre;

    public Especie(int id_especie, String nombre) {
        this.id_especie = id_especie;
        this.nombre = nombre;
    }


    public int getId_especie() {
        return id_especie;
    }

    public void setId_especie(int id_especie) {
        this.id_especie = id_especie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
