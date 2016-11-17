package com.davidch.proyecto.pethelp.servicio;

import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.modelo.Cuidador;
import com.davidch.proyecto.pethelp.modelo.Especie;
import com.davidch.proyecto.pethelp.modelo.Login;
import com.davidch.proyecto.pethelp.modelo.Mascota;
import com.davidch.proyecto.pethelp.modelo.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by adeka on 18/08/2016.
 */
public interface PetHelpServicio {

    @POST("cuidadores/registrar")
    Call<Cuidador> registrar(@Body Cuidador cuidador);

    @POST("usuarios/registrar")
    Call<Void> registrar(@Body Usuario usuario);

    @GET("mascotas")
    Call<List<Mascota>> getMisMascotas();

    @POST("mascotas/sincronizar")
    Call<List<Mascota>> sincronizarMascotas(@Body Map<String, List<Mascota>> mascotas);

    @POST("cuidadores/sincronizar")
    Call<List<Cuidador>> sincronizarCuidadores(@Body List<Cuidador> borrables);

    @GET("especies")
    Call<List<Especie>> getEspecies();

}
