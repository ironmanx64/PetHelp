package com.davidch.proyecto.pethelp.servicio;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adeka on 18/08/2016.
 */
public class FactoriaServicio {

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://mascotasserver.dynu.com:7070/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static PetHelpServicio getPetHelpServicio() {
        return RETROFIT.create(PetHelpServicio.class);
    }
}
