package com.davidch.proyecto.pethelp.servicio;

import com.davidch.proyecto.pethelp.modelo.Especie;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by adeka on 18/08/2016.
 */
public interface PetHelpServicio {

    @POST("registrar")
    Call<Void> registrar(@Body Usuario usuario);

    @POST("mis-mascotas")
    Call<List<Mascota>> getMisMascotas(@Body Login login);

    @POST("obtener-especies")
    Call<List<Especie>> getEspecies(@Body Login login);
}
