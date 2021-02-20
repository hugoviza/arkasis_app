package com.example.arkasis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableActividades;
import com.example.arkasis.DB.tablas.TableCoordinadores;
import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.DB.tablas.TableSucursal;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APICatalogosInterface;
import com.example.arkasis.interfaces.APISolicitudDispersion;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.EstatusSincronizacionSolicitud;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.SolicitudDispersion;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.Usuario;
import com.example.arkasis.utilerias.CustomReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

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

    private TextView tvEstatusConexion;

    private CustomReceiver myReceiver = null;
    private Boolean bitConexionIntenet = false;
    private Boolean bitActualizarCatalogo = true;

    public static final int ITEM_HOME = R.id.itemHome;
    public static final int ITEM_FIND = R.id.itemBuscar;
    public static final int ITEM_ADD = R.id.itemRegistrar;

    public BottomBarActivity () {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        setContentView(R.layout.activity_bottom_bar);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        tvEstatusConexion = findViewById(R.id.tvEstatusConexion);

        try {
            getUsuarioSesion();

            myReceiver = new CustomReceiver();
            myReceiver.setOnItemClickListener(new CustomReceiver.OnChangeStatus() {
                @Override
                public void onChange(String status) {
                    fragmentBuscarCliente.setHabilitarBusqueda(status == "");
                    if(status != "") {
                        tvEstatusConexion.setVisibility(View.VISIBLE);
                        tvEstatusConexion.setText(status);
                        bitConexionIntenet = false;
                    } else {
                        tvEstatusConexion.setVisibility(View.GONE);
                        bitConexionIntenet = true;
                        if(hayRegistrosPorSincronizar()) {
                            sincronizarSolicitudes();
                        } else if(bitActualizarCatalogo) {
                            actualizarCatalogos();
                        }
                    }
                }
            });

            fragmentManager = this.getSupportFragmentManager();

            fragmentDashboard = new FragmentDashboard(this);
            fragmentBuscarCliente = new FragmentBuscarCliente(this);
            fragmentFormularioRegistro = new FragmentFormularioRegistro(this);

            cargarFragmento(fragmentDashboard);
            comprobarConexionInternet();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        fragmentFormularioRegistro.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        fragmentFormularioRegistro.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean hayRegistrosPorSincronizar() {
        TableSolicitudesDispersion table = new TableSolicitudesDispersion(this);
        int totalRegistros = table.getCount();
        return totalRegistros > 0;
    }

    public void actualizarCatalogos(){
        if(bitActualizarCatalogo) {
            validarCatalogoActividades();
            bitActualizarCatalogo = false;
        }
    }

    public void sincronizarSolicitudes() {
        TableSolicitudesDispersion table = new TableSolicitudesDispersion(this);
        List<SolicitudDispersion> listaSolicitudes = (List<SolicitudDispersion>)(Object)table.findAll("", 10000);
        List<Integer> listIdSolicitudes = new ArrayList<>();

        for (SolicitudDispersion solicitudDispersion: listaSolicitudes) {
            listIdSolicitudes.add(solicitudDispersion.getIdSolicitud());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APISolicitudDispersion api = retrofit.create(APISolicitudDispersion.class);
        Call<ResponseAPI> apiCall = api.batchAddSolicitud(listaSolicitudes);
        abrirLoading("Sincronizando " + listaSolicitudes.size() + " solicitudes de dispersión");
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body() == null || response.body().getResultado() == null) {
                    //No hay nada por hacer
                    Toast.makeText(BottomBarActivity.this, "Ha surgido un error al sincronizar registros", Toast.LENGTH_SHORT).show();
                } else {
                    if(response.body().getSuccess()) {
                        int contadorRegistrosFallidos = 0;
                        for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                            try {
                                EstatusSincronizacionSolicitud estatus = new EstatusSincronizacionSolicitud(treeMap);
                                if(estatus.isBitRegistrado()) {
                                    table.eliminar(estatus.getIdSolicitud());
                                } else {
                                    int index = listIdSolicitudes.indexOf(estatus.getIdSolicitud());
                                    SolicitudDispersion solicitudDispersion = listaSolicitudes.get(index);
                                    solicitudDispersion.setStrEstatusInserccionServidor(estatus.getStrMensaje());
                                    table.actualizarEstatusInserccionServidor(solicitudDispersion);
                                    contadorRegistrosFallidos++;
                                }
                            } catch (Exception e) {
                                //Errorcito
                            }
                        }
                        if(contadorRegistrosFallidos > 0) {
                            Toast.makeText(BottomBarActivity.this, "Error al sincronizar "+contadorRegistrosFallidos+ (contadorRegistrosFallidos != 1 ? " solicitudes" : " solicitud"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BottomBarActivity.this, "Solicitudes sincronizadas correctamente", Toast.LENGTH_SHORT).show();
                        }

                        if(fragmentDashboard != null) {
                            fragmentDashboard.actualizarListaSolicitudesLocales();
                        }
                    }
                }

                cerrarLoading();
                actualizarCatalogos();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                cerrarLoading();
                actualizarCatalogos();
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
        if(dialogFragmentLoading != null) {
            dialogFragmentLoading.dismiss();
        }

        dialogFragmentLoading = new DialogFragmentLoading(mensaje);
        dialogFragmentLoading.show(fragmentManager, "loading");

    }

    public static void cerrarLoading() {
        if(dialogFragmentLoading != null) {
            dialogFragmentLoading.dismiss();
        }
    }

    private void comprobarConexionInternet() {
        registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public boolean getEstatusConexionInternet() {
        return bitConexionIntenet;
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
                if(response.body() == null || response.body().getResultado() == null) {
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
                Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                    cerrarLoading();
                    validarCatalogoMunicipios();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            cerrarLoading();
            validarCatalogoMunicipios();
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
                if(response.body() == null || response.body().getResultado() == null) {
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
                Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void descargarCatalogoMunicipios() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                            cerrarLoading();
                            validarCatalogoSucursales();
                            Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    cerrarLoading();
                    validarCatalogoSucursales();
                    Toast.makeText(BottomBarActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, 300);
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
                if(response.body() == null || response.body().getResultado() == null) {
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
                Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void descargarCatalogoSucursales() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                            cerrarLoading();
                            validarCatalogoCoordinadores();
                            Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    cerrarLoading();
                    validarCatalogoCoordinadores();
                    Toast.makeText(BottomBarActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, 300);
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
                if(response.body() == null || response.body().getResultado() == null) {
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
                Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void descargarCatalogoCoordinadores() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                            cerrarLoading();
                            Toast.makeText(BottomBarActivity.this, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    cerrarLoading();
                    Toast.makeText(BottomBarActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, 300);

    }

}