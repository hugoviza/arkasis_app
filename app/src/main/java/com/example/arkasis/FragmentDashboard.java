package com.example.arkasis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.adapters.AdaptadorListaSolicitudes;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIClientesInterface;
import com.example.arkasis.interfaces.APISolicitudDispersion;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.ResumenSolicitudes;
import com.example.arkasis.models.SolicitudDispersion;
import com.example.arkasis.models.Usuario;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentDashboard extends Fragment {

    private BottomBarActivity parent;
    private AdaptadorListaSolicitudes adaptadorListaSolicitudes;
    private RecyclerView rvList_solicitudes;

    private View fragmentDashboardView;
    private Usuario usuario;

    //Graficas
    private PieChartView pieChartResumenSolicitudesMensuales;

    public FragmentDashboard() {
        // Required empty public constructor
    }

    public FragmentDashboard(BottomBarActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        usuario = Config.USUARIO_SESION;
        rvList_solicitudes = fragmentDashboardView.findViewById(R.id.rvList_solicitudes);
        TextView tvBienvenida = fragmentDashboardView.findViewById(R.id.tvBienvenida);

        pieChartResumenSolicitudesMensuales = fragmentDashboardView.findViewById(R.id.pieChartResumenSolicitudesMensuales);

        tvBienvenida.setText("Bienvenido " + usuario.getNombre());
        inicializarListaSolicitudesLocales();
        obtenerResumenSolicitudes();
        return fragmentDashboardView;
    }

    private void inicializarListaSolicitudesLocales() {
        TableSolicitudesDispersion table = new TableSolicitudesDispersion(fragmentDashboardView.getContext());
        List<SolicitudDispersion> listaSolicitudes = (List<SolicitudDispersion>)(Object)table.findAll("", 1000);

        adaptadorListaSolicitudes = new AdaptadorListaSolicitudes(listaSolicitudes, fragmentDashboardView.getContext());
        rvList_solicitudes.setHasFixedSize(true);
        rvList_solicitudes.setLayoutManager(new LinearLayoutManager(fragmentDashboardView.getContext()));
        rvList_solicitudes.setAdapter(adaptadorListaSolicitudes);

        adaptadorListaSolicitudes.setOnItemClickListener(position -> {
            if(position != RecyclerView.NO_POSITION) {
                //Selected data
                SolicitudDispersion solicitudDispersion = adaptadorListaSolicitudes.getItem(position);
            }
        });
    }

    public void actualizarListaSolicitudesLocales() {
        if(adaptadorListaSolicitudes != null) {
            TableSolicitudesDispersion table = new TableSolicitudesDispersion(fragmentDashboardView.getContext());
            List<SolicitudDispersion> listaSolicitudes = (List<SolicitudDispersion>)(Object)table.findAll("", 1000);
            adaptadorListaSolicitudes.setListaSolicitudes(listaSolicitudes);
        }
    }

    private void obtenerResumenSolicitudes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APISolicitudDispersion api = retrofit.create(APISolicitudDispersion.class);
        Call<ResponseAPI> apiCall = api.obtenerResumenSolicitudesPorUsuario(usuario);

        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {

                } else {
                    try {
                        if(response.body().getSuccess()) {
                            LinkedTreeMap<Object, Object> treeMap = (LinkedTreeMap)response.body().getResultado();
                            inicializarGraficaPie(new ResumenSolicitudes(treeMap));
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Error al cargar datos de gráfica", Toast.LENGTH_SHORT).show();
                    }
                }
                BottomBarActivity.cerrarLoading();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                BottomBarActivity.cerrarLoading();
                Toast.makeText(getContext(), getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarGraficaPie(ResumenSolicitudes rs) {
        List pieData = new ArrayList();
        pieData.add(new SliceValue(rs.getPorcentajeTramite(), Color.parseColor("#DAF7A6")).setLabel("TRÁMITE: " + rs.getIntTotalTramite())); // verde claro
        pieData.add(new SliceValue(rs.getPorcentajeAutorizado(), Color.parseColor("#283593")).setLabel("AUTORIZADO: " + rs.getIntTotalAutorizado())); //azul
        pieData.add(new SliceValue(rs.getPorcentajeMinistrado(), Color.parseColor("#2ECC71")).setLabel("MINISTRADO: " + rs.getIntTotalMinistrado())); // verde
        pieData.add(new SliceValue(rs.getPorcentajeRechazado(), Color.parseColor("#C70039")).setLabel("RECHAZADO: " + rs.getIntTotalRechazado())); // rojo
        pieData.add(new SliceValue(rs.getPorcentajeCancelado(), Color.parseColor("#FFC300")).setLabel("MINISTRADO: " + rs.getIntTotalMinistrado())); // amarillo

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(8);
        pieChartData.setHasCenterCircle(true).setCenterText1(rs.getIntTotalRegistros() + " SOLICITUDES").setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartResumenSolicitudesMensuales.setPieChartData(pieChartData);
    }
}