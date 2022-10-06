package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.Dispositivo;
import com.example.arkasis.models.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIDispositivosInterface {
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/dispositivos")
    Call<ResponseAPI> getDispositivos(@Body Dispositivo dispositivo);

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @PUT("api/dispositivos/UUID")
    Call<ResponseAPI> guardarDispositivos(@Body Dispositivo dispositivo);
}
