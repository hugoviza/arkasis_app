package com.example.arkasis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkasis.DB.ConexionSQLiteHelper;
import com.example.arkasis.config.Config;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // La app siempre inicia con el activity main

        //Animaciones para el splash
        Animation desplazamiento_abajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        Animation desplazamiento_arriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        //Vista
        TextView tvLogo = findViewById(R.id.tvLogo);
        TextView tvPor = findViewById(R.id.tvPor);
        ImageView ivLogoSecsa = findViewById(R.id.ivLogoSecsa);

        tvLogo.setAnimation(desplazamiento_abajo);
        tvPor.setAnimation(desplazamiento_arriba);
        ivLogoSecsa.setAnimation(desplazamiento_arriba);

        //Revisamos si en la app ya hay alguna sesion activa, en caso de que si exista, entonces omitimos el login

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getApplicationContext(), Config.DB_NOMBRE, null, Config.DB_VERSION);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(tvLogo, "logoImageTrans");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());
                finish();
            }
        }, 4000);
    }
}