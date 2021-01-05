package com.example.arkasis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableActividades;
import com.example.arkasis.DB.tablas.TableCoordinadores;
import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.DB.tablas.TableSucursal;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APICatalogosInterface;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomBarActivity extends AppCompatActivity {

    FragmentDashboard fragmentDashboard;
    FragmentBuscarCliente fragmentBuscarCliente;
    private static FragmentFormularioRegistro fragmentFormularioRegistro;

    public static DialogFragmentLoading dialogFragmentLoading;
    public static FragmentManager fragmentManager;
    private static BottomNavigationView bottom_navigation;

    public static final int ITEM_HOME = R.id.itemHome;
    public static final int ITEM_FIND = R.id.itemBuscar;
    public static final int ITEM_ADD = R.id.itemRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        try {
            getUsuarioSesion();

            fragmentManager = this.getSupportFragmentManager();

            fragmentDashboard = new FragmentDashboard();
            fragmentBuscarCliente = new FragmentBuscarCliente();
            fragmentFormularioRegistro = new FragmentFormularioRegistro();

            cargarFragmento(fragmentDashboard);

            validarCatalogoActividades();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

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

    public static void setSelectedItem(int idItem) {
        bottom_navigation.setSelectedItemId(idItem);
    }

    public static void setFragmentFormularioRegistro(FragmentFormularioRegistro _fragmentFormularioRegistro) {
        fragmentFormularioRegistro = _fragmentFormularioRegistro;
    }

    public void cargarFragmento(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragmentos, fragment);
        fragmentTransaction.commit();
    }

    public static void abrirLoading(String mensaje) {
        if(dialogFragmentLoading == null) {
            dialogFragmentLoading = DialogFragmentLoading.newInstance(mensaje);
        } else {
            dialogFragmentLoading.actualizarMensaje(mensaje);
        }

        dialogFragmentLoading.show(fragmentManager, "loading");
    }

    public static void cerrarLoading() {
        if(dialogFragmentLoading != null) {
            dialogFragmentLoading.dismiss();
        }
    }

    public void getUsuarioSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);

        Usuario usuario = new Usuario();
        usuario.setUser(sharedPreferences.getString("user", null));
        usuario.setPassword(sharedPreferences.getString("password", null));
        usuario.setNombre(sharedPreferences.getString("name", null));

        Config.USUARIO_SESION = usuario;
    }

    public void validarCatalogoActividades() {

        TableActividades table = new TableActividades(BottomBarActivity.this);
        Integer totalRegistrosLocal = table.getCount();

        if(totalRegistrosLocal.intValue() == 0) {
            descargarCatalogoActividades();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
        Call<ResponseAPI> apiCall = api.getTotalActividades();
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    //No hay nada por hacer
                    validarCatalogoMunicipios();
                } else {
                    Integer totalRegistrosAPI = Integer.parseInt(response.body().getResultado().toString().trim());
                    if(totalRegistrosLocal.intValue() != totalRegistrosAPI.intValue()) {
                        descargarCatalogoActividades();
                    } else {
                        validarCatalogoMunicipios();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
            }
        });
    }

    public void descargarCatalogoActividades() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
            Call<ResponseAPI> apiCall = api.getAllActividades();
            abrirLoading("Actualizando catálogo de actividades");

            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer
                    } else {
                        //limpiamos la tabla antes de insertar nuevos datos
                        TableActividades table = new TableActividades(BottomBarActivity.this);
                        table.truncate();
                        int totalErrores = 0;

                        if(response.body().getSuccess()) {
                            for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                try {
                                    table.insertar(new Actividad(treeMap));
                                } catch (Exception e) {
                                    totalErrores++;
                                }
                            }
                        }

                        if(totalErrores > 0) {
                            Toast.makeText(BottomBarActivity.this, "Se han encontrado errores en " + totalErrores + " registros de actividades", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cerrarLoading();
                    validarCatalogoMunicipios();
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Toast.makeText(BottomBarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void validarCatalogoMunicipios() {

        TableMunicipios table = new TableMunicipios(BottomBarActivity.this);
        Integer totalRegistrosLocal = table.getCount();

        if(totalRegistrosLocal.intValue() == 0) {
            descargarCatalogoMunicipios();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
        Call<ResponseAPI> apiCall = api.getTotalMunicipios();
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    //No hay nada por hacer
                    validarCatalogoSucursales();
                } else {
                    Integer totalRegistrosAPI = Integer.parseInt(response.body().getResultado().toString().trim());
                    if(totalRegistrosAPI.intValue() != totalRegistrosLocal.intValue()) {
                        descargarCatalogoMunicipios();
                    } else {
                        validarCatalogoSucursales();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
            }
        });
    }

    public void descargarCatalogoMunicipios() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
            Call<ResponseAPI> apiCall = api.getAllMunicipios();
            abrirLoading("Actualizando catálogo de municipios");

            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer
                    } else {
                        //limpiamos la tabla antes de insertar nuevos datos
                        TableMunicipios table = new TableMunicipios(BottomBarActivity.this);
                        table.truncate();
                        int totalErrores = 0;

                        if(response.body().getSuccess()) {
                            for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                try {
                                    table.insertar(new Municipio(treeMap));
                                } catch (Exception e) {
                                    totalErrores++;
                                }
                            }
                        }

                        if(totalErrores > 0) {
                            Toast.makeText(BottomBarActivity.this, "Se han encontrado errores en " + totalErrores + " registros de municipios", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cerrarLoading();
                    validarCatalogoSucursales();
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Toast.makeText(BottomBarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void validarCatalogoSucursales() {

        TableSucursal table = new TableSucursal(BottomBarActivity.this);
        Integer totalRegistrosLocal = table.getCount();

        if(totalRegistrosLocal.intValue() == 0) {
            descargarCatalogoSucursales();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
        Call<ResponseAPI> apiCall = api.getTotalSucursales();
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    //No hay nada por hacer
                } else {
                    Integer totalRegistrosAPI = Integer.parseInt(response.body().getResultado().toString().trim());
                    if(totalRegistrosLocal.intValue() != totalRegistrosAPI.intValue()) {
                        descargarCatalogoSucursales();
                    } else {
                        validarCatalogoCoordinadores();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {

            }
        });
    }

    public void descargarCatalogoSucursales() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
            Call<ResponseAPI> apiCall = api.getAllSucursales();
            abrirLoading("Actualizando catálogo de sucursales");

            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer
                    } else {
                        //limpiamos la tabla antes de insertar nuevos datos
                        TableSucursal table = new TableSucursal(BottomBarActivity.this);
                        table.truncate();
                        int totalErrores = 0;

                        if(response.body().getSuccess()) {
                            for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                try {
                                    table.insertar(new Sucursal(treeMap));
                                } catch (Exception e) {
                                    totalErrores++;
                                }
                            }
                        }

                        if(totalErrores > 0) {
                            Toast.makeText(BottomBarActivity.this, "Se han encontrado errores en " + totalErrores + " registros de sucursal", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cerrarLoading();
                    validarCatalogoCoordinadores();
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Toast.makeText(BottomBarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void validarCatalogoCoordinadores() {

        TableCoordinadores table = new TableCoordinadores(BottomBarActivity.this);
        Integer totalRegistrosLocal = table.getCount();

        if(totalRegistrosLocal.intValue() == 0) {
            descargarCatalogoCoordinadores();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
        Call<ResponseAPI> apiCall = api.getTotalCoordinadores();
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    //No hay nada por hacer
                } else {
                    Integer totalRegistrosAPI = Integer.parseInt(response.body().getResultado().toString().trim());
                    if(totalRegistrosLocal.intValue() != totalRegistrosAPI.intValue()) {
                        descargarCatalogoCoordinadores();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {

            }
        });
    }

    public void descargarCatalogoCoordinadores() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APICatalogosInterface api = retrofit.create(APICatalogosInterface.class);
            Call<ResponseAPI> apiCall = api.getAllCoordinadores();
            abrirLoading("Actualizando catálogo de coordinadores");

            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer
                    } else {
                        //limpiamos la tabla antes de insertar nuevos datos
                        TableCoordinadores table = new TableCoordinadores(BottomBarActivity.this);
                        table.truncate();
                        int totalErrores = 0;

                        if(response.body().getSuccess()) {
                            for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                try {
                                    table.insertar(new Coordinador(treeMap));
                                } catch (Exception e) {
                                    totalErrores++;
                                }
                            }
                        }

                        if(totalErrores > 0) {
                            Toast.makeText(BottomBarActivity.this, "Se han encontrado errores en " + totalErrores + " registros de coordinadores", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cerrarLoading();
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Toast.makeText(BottomBarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}