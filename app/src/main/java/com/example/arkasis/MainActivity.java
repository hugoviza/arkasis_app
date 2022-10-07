package com.example.arkasis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkasis.DB.ConexionSQLiteHelper;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIDispositivosInterface;
import com.example.arkasis.models.Dispositivo;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.Usuario;
import com.example.arkasis.utilerias.SharedPreferencesData;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // La app siempre inicia con el activity main

        //Animaciones para el splash
        Animation desplazamiento_abajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        Animation desplazamiento_arriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        //Vista
        tvLogo = findViewById(R.id.tvLogo);
        TextView tvPor = findViewById(R.id.tvPor);
        ImageView ivLogoSecsa = findViewById(R.id.ivLogoSecsa);

        tvLogo.setAnimation(desplazamiento_abajo);
        tvPor.setAnimation(desplazamiento_arriba);
        ivLogoSecsa.setAnimation(desplazamiento_arriba);

        // Iniciamos la base de datos
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getApplicationContext(), Config.DB_NOMBRE, null, Config.DB_VERSION);

        // actualizamos el registro del dispositivo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actualizarRegistroDispositivo();
                    }
                });
            }
        }, 2000);
    }

    private void abrirSiguienteVista(Context context) {
        // Revisamos si la app está registrada
        Dispositivo dispositivo = SharedPreferencesData.getDispositivo(context);
        // Si la app no está registrada => mostramos la vista de registro
        if(dispositivo == null) {
            abrirRegistroDispositivo();
            return;
        }

        if(dispositivo.getEstatusTokenAcivacion() == 0) {
            // Toast.makeText(context, "Dispositivo bloqueado", Toast.LENGTH_LONG).show();
            abrirRegistroDispositivo();
            // cerrarApp();
            return;
        }

        //Revisamos si en la app ya hay alguna sesion activa, en caso de que si exista, entonces omitimos el login
        Usuario usuario = SharedPreferencesData.getUsuario(context);
        // Si no hay sesión activa => abrimos el login
        if(usuario == null) {
            abrirLogin();
            return;
        }

        abrirDashboard();
    }

    private void abrirDashboard() {
        Intent intent = new Intent(MainActivity.this, BottomBarActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirLogin() {
        // Instanciamos el login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(tvLogo, "logoImageTrans");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }

    private void abrirRegistroDispositivo() {
        // Instanciamos el login
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(tvLogo, "logoImageTrans");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }

    private void actualizarRegistroDispositivo() {
        // Revisamos si el dispositivo ya ha sido registrado anteriormente
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setUUIDDispositivo(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Context context = getApplicationContext();
        APIDispositivosInterface api = retrofit.create(APIDispositivosInterface.class);
        Call<ResponseAPI> call = api.getDispositivos(dispositivo);
        call.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.isSuccessful()) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        // El dispositivo no está registrado en la API
                        // abrirSiguienteVista(context, scope);
                        Dispositivo dispositivoRegistrado = new Dispositivo();
                        SharedPreferencesData.setDispositivo(dispositivoRegistrado, context);
                    } else {
                        // El dispositivo ya está registrado en la api
                        for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                            try {
                                // Si el dispositivo ya ha sido registrado, entonces actualizamos la data
                                Dispositivo dispositivoRegistrado = new Dispositivo(treeMap);
                                SharedPreferencesData.setDispositivo(dispositivoRegistrado, context);
                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        // abrirSiguienteVista(context, scope);
                    }
                } else {
                    Toast.makeText(context, "Error al obtener información de registro de dispositivo", Toast.LENGTH_SHORT).show();
                    // cerrarApp();
                }

                abrirSiguienteVista(context);
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                abrirSiguienteVista(context);
                // cerrarApp();
            }
        });
    }

    private void cerrarApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.finish();
                System.exit(0);
            }
        }, 2000);
    }
}