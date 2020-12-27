package com.example.arkasis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIMunicipiosInterface;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.ResponseAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomBarActivity extends AppCompatActivity {

    FragmentDashboard fragmentDashboard = new FragmentDashboard();
    FragmentBuscarCliente fragmentBuscarCliente = new FragmentBuscarCliente();
    FragmentFormularioRegistro fragmentFormularioRegistro = new FragmentFormularioRegistro();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);

        cargarFragmento(fragmentDashboard);
        validarListaMunicipios();

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        cargarFragmento(fragmentDashboard);
                        return true;

                    case R.id.itemBuscar:
                        cargarFragmento(fragmentBuscarCliente);
                        return true;

                    case R.id.itemRegistrar:
                        cargarFragmento(fragmentFormularioRegistro);
                        return true;

                }
                return false;
            }
        });
    }

    public void cargarFragmento(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragmentos, fragment);
        fragmentTransaction.commit();
    }

    public void validarListaMunicipios() {

        TableMunicipios tableMunicipios = new TableMunicipios(BottomBarActivity.this);
        Integer totalMunicipiosLocal = tableMunicipios.getCount();

        if(totalMunicipiosLocal == 0) {
            descargarListaMunicipios();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIMunicipiosInterface apiMunicipiosInterface = retrofit.create(APIMunicipiosInterface.class);
        Call<ResponseAPI> apiCall = apiMunicipiosInterface.getTotalMunicipios();
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    //No hay nada por hacer
                } else {
                    Integer totalMunicipiosAPI = Integer.parseInt(response.body().getResultado().toString().trim());
                    //Toast.makeText(BottomBarActivity.this, "Total municipios: " + totalMunicipiosAPI + " : " + totalMunicipiosLocal, Toast.LENGTH_SHORT).show();

                    if(totalMunicipiosAPI.intValue() != totalMunicipiosLocal.intValue()) {
                        descargarListaMunicipios();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {

            }
        });
    }

    public void descargarListaMunicipios() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIMunicipiosInterface apiMunicipiosInterface = retrofit.create(APIMunicipiosInterface.class);
            Call<ResponseAPI> apiCall = apiMunicipiosInterface.getAllMunicipios();
            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer
                    } else {
                        //limpiamos la tabla antes de insertar nuevos datos
                        TableMunicipios tableMunicipios = new TableMunicipios(BottomBarActivity.this);
                        tableMunicipios.truncate();

                        if(response.body().getSuccess()) {
                            for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                tableMunicipios.Insertar(new Municipio(treeMap));
                            }
                        }

                        Integer totalRegistrosInsertados = tableMunicipios.getCount();
                        Toast.makeText(BottomBarActivity.this, "Municipios actualizados: "+ totalRegistrosInsertados, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}