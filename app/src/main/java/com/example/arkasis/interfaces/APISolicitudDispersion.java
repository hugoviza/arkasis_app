package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.SolicitudDispersion;
import com.example.arkasis.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APISolicitudDispersion {
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/dispersion/nueva")
    Call<ResponseAPI> addSolicitud(@Body SolicitudDispersion solicitudDispersion);


    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/dispersion/batch/nueva")
    Call<ResponseAPI> batchAddSolicitud(@Body List<SolicitudDispersion> arraySolicitudesDispersion);


    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/dispersion/resumen-usuario")
    Call<ResponseAPI> obtenerResumenSolicitudesPorUsuario(@Body Usuario usuario);
}
