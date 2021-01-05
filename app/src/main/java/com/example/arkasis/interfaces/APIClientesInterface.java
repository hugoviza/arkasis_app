package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIClientesInterface {
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/clientes")
    Call<ResponseAPI> getClientes(@Body Cliente cliente);

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/clientes/curp")
    Call<ResponseAPI> getClienteByCurp(@Body Cliente cliente);
}
