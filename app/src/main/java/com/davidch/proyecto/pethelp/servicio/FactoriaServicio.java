package com.davidch.proyecto.pethelp.servicio;

import com.davidch.proyecto.pethelp.modelo.Login;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adeka on 18/08/2016.
 */
public class FactoriaServicio {

    public static final String BASE_URL = "http://mascotasserver.dynu.com:7070/";

    public static PetHelpServicio getPetHelpServicio() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PetHelpServicio.class);
    }

    public static PetHelpServicio getPetHelpServicio(final Login login) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient cliente = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .addHeader("Authorization", login.getBasicAuthToken())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .client(cliente)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PetHelpServicio.class);
    }
}
