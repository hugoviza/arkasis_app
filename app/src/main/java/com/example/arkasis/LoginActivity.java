package com.example.arkasis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.LoginApiInterface;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Console;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario usuario = obtenerSesion();
        if(usuario != null) {
            if(usuario.getPassword() != "" && usuario.getUser() != "") {
                abrirDashboard();
            }
        }
    }

    public void fnIniciarSesion(View view) {

        try {

            TextInputEditText txtUsuario = findViewById(R.id.txtUsuario);
            TextInputEditText txtPassword = findViewById(R.id.txtPassword);
            MaterialButton btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

            String strUsuario = txtUsuario.getText().toString().trim();
            String strPassword = txtPassword.getText().toString().trim();

            if(strUsuario.length() == 0) {
                txtUsuario.setError("Ingrese nombre de usuario");
                return;
            } else {
                txtUsuario.setError(null);
            }

            if(strPassword.length() == 0) {
                txtPassword.setError("Ingrese contraseña");
                return;
            } else {
                txtPassword.setError(null);
            }

            btnIniciarSesion.setEnabled(false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Usuario usuario = new Usuario();
            usuario.setUser(strUsuario);
            usuario.setPassword(strPassword);

            LoginApiInterface api = retrofit.create(LoginApiInterface.class);
            Call<ResponseAPI> call = api.login(usuario);
            call.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    btnIniciarSesion.setEnabled(true);

                    if(response.isSuccessful()) {
                        if(response.body() == null || response.body().getResultado() == null) {
                            Toast.makeText(LoginActivity.this, "No se puede iniciar sesión", Toast.LENGTH_SHORT).show();
                        } else {
                            LinkedTreeMap<Object, Object> treeMap = (LinkedTreeMap)response.body().getResultado();
                            guardarSesionUsuario(new Usuario(treeMap));
                            abrirDashboard();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "error al conectar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    btnIniciarSesion.setEnabled(true);
                    System.out.println(t.getMessage());
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void guardarSesionUsuario(Usuario usuario) {
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor sesion = sharedPreferences.edit();
        sesion.putString("user", usuario.getUser());
        sesion.putString("password", usuario.getPassword());
        sesion.putString("name", usuario.getNombre());
        sesion.commit();
    }

    public Usuario obtenerSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);

        Usuario usuario = new Usuario();
        usuario.setUser(sharedPreferences.getString("user", null));
        usuario.setPassword(sharedPreferences.getString("password", null));
        usuario.setNombre(sharedPreferences.getString("name", null));

        if(usuario.getUser() != null) {
            return usuario;
        } else {
            return null;
        }
    }

    public void abrirDashboard() {
        Intent intent = new Intent(LoginActivity.this, BottomBarActivity.class);
        startActivity(intent);
        finish();
    }
}