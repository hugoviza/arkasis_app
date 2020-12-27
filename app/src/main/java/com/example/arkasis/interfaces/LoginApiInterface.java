package com.example.arkasis.interfaces;

import com.example.arkasis.config.Config;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApiInterface {
    @Headers({
            "x-api-key: " + Config.API_KEY,
            "Content-Type: application/json"
    })
    @POST("api/login")
    Call<ResponseAPI> login(@Body Usuario user);
}
