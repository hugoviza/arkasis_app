package com.example.arkasis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIDispositivosInterface;
import com.example.arkasis.interfaces.LoginApiInterface;
import com.example.arkasis.models.Dispositivo;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Usuario;
import com.example.arkasis.utilerias.SharedPreferencesData;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Console;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText txtCodigo1, txtCodigo2, txtCodigo3;
    TextView tvAgregarCodigo, tvResultCodigo;
    Dispositivo dispositivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtCodigo1 = findViewById(R.id.txtCodigo1);
        txtCodigo2 = findViewById(R.id.txtCodigo2);
        txtCodigo3 = findViewById(R.id.txtCodigo3);

        tvAgregarCodigo = findViewById(R.id.tvAgregarCodigo);
        tvResultCodigo = findViewById(R.id.tvResultCodigo);

        // Revisamos si la app está registrada
        dispositivo = SharedPreferencesData.getDispositivo(getApplicationContext());

        // Si la app ya está registrada => validamos que el token esté activo
        if(dispositivo != null && dispositivo.getEstatusTokenAcivacion() == 1) {
            abrirLogin();
            return;
        }

        if(dispositivo != null) {
            // seteamos el token de activacion
            String[] token = dispositivo.getTokenActivacion().split("-");
            if(token.length > 0) txtCodigo1.setText(token[0]);
            if(token.length > 1) txtCodigo2.setText(token[1]);
            if(token.length > 2) txtCodigo3.setText(token[2]);

            if(dispositivo.getEstatusTokenAcivacion() == 0) tvResultCodigo.setText("Dispositivo bloqueado");
        }

        txtCodigo3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registrarDispositivo();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtCodigo1, InputMethodManager.SHOW_FORCED);
        if(txtCodigo3.getText().toString().length() > 0) txtCodigo3.requestFocus();
        else if(txtCodigo2.getText().toString().length() > 0) txtCodigo2.requestFocus();
        else txtCodigo1.requestFocus();

    }

    private void registrarDispositivo() {

        String token1 = txtCodigo1.getText().toString();
        String token2 = txtCodigo2.getText().toString();
        String token3 = txtCodigo3.getText().toString();

        if(token1.length() != 4) {
            Toast.makeText(this, "Cada código debe de contener 4 dígitos", Toast.LENGTH_SHORT).show();
            txtCodigo1.requestFocus();
            return;
        }

        if(token2.length() != 4) {
            Toast.makeText(this, "Cada código debe de contener 4 dígitos", Toast.LENGTH_SHORT).show();
            txtCodigo2.requestFocus();
            return;
        }
        if(token3.length() != 4) {
            Toast.makeText(this, "Cada código debe de contener 4 dígitos", Toast.LENGTH_SHORT).show();
            txtCodigo3.requestFocus();
            return;
        }

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setTokenActivacion(token1 + "-" + token2 + "-"+ token3);
        dispositivo.setUUIDDispositivo(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        dispositivo.setPlataforma("Android");
        // Guardamos el registro del dispositivo

        bloquearInputCodigo(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Context context = getApplicationContext();
        APIDispositivosInterface api = retrofit.create(APIDispositivosInterface.class);
        Call<ResponseAPI> call = api.guardarDispositivos(dispositivo);
        call.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                String mensaje = "Error al activar dispositivo";
                // Si la api nos rertona error => entonces mostramos el error al usuario
                if(!response.isSuccessful()) {
                    try {
                        mensaje = response.errorBody().string();
                    } catch (IOException e) {
                    }
                    tvResultCodigo.setText(mensaje);
                    bloquearInputCodigo(false);
                    return;
                }

                // Si la api no nos retorna mensajes
                if(response.body() == null || response.body().getResultado() == null) {
                    // Error
                    tvResultCodigo.setText(mensaje);
                    bloquearInputCodigo(false);
                    return;
                }

                // El dispositivo ya está registrado en la api
                LinkedTreeMap<Object, Object> treeMap = (LinkedTreeMap)response.body().getResultado();
                Dispositivo dispositivoRegistrado = new Dispositivo(treeMap);
                // Si el dispositivo ya ha sido registrado, entonces actualizamos la data
                SharedPreferencesData.setDispositivo(dispositivoRegistrado, context);
                abrirLogin();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                bloquearInputCodigo(false);
            }
        });
    }

    private void bloquearInputCodigo(Boolean bitBloqueo) {
        txtCodigo1.setEnabled(!bitBloqueo);
        txtCodigo2.setEnabled(!bitBloqueo);
        txtCodigo3.setEnabled(!bitBloqueo);
    }

    private void abrirLogin() {
        // Instanciamos el login
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}