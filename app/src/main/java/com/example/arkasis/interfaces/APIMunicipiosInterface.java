package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIMunicipiosInterface {

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/municipios")
    Call<ResponseAPI> getAllMunicipios();


    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/municipios/total")
    Call<ResponseAPI> getTotalMunicipios();
}
