package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.ResponseAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APICatalogosInterface {
    // ACTIVIDADES
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/actividades")
    Call<ResponseAPI> getAllActividades();

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/actividades/total")
    Call<ResponseAPI> getTotalActividades();


    // SUCURSALES
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/sucursales")
    Call<ResponseAPI> getAllSucursales();

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/sucursales/total")
    Call<ResponseAPI> getTotalSucursales();


    // MUNICIPIOS
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/municipios")
    Call<ResponseAPI> getAllMunicipios();

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/municipios/total")
    Call<ResponseAPI> getTotalMunicipios();

    // COORDINADORES
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/coordinadores")
    Call<ResponseAPI> getAllCoordinadores();

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/coordinadores/total")
    Call<ResponseAPI> getTotalCoordinadores();

    // TIPO DE VENCIMIENTO
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/tipovencimiento")
    Call<ResponseAPI> getAllTipoVencimiento();

    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @GET("api/catalogos/tipovencimiento/total")
    Call<ResponseAPI> getTotalTipoVencimiento();



}
