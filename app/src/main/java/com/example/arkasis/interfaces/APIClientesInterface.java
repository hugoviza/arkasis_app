package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Usuario;

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

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/clientes/saldos")
    Call<ResponseAPI> getSaldos(@Body Cliente cliente);

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/clientes/solicitudes")
    Call<ResponseAPI> getSolicitudes(@Body Cliente cliente);

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/clientes/ultimas-solicitudes")
    Call<ResponseAPI> getUltimasSolicitudes(@Body Usuario usuario);
}
