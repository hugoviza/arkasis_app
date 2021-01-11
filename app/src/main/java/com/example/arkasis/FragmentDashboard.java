package com.example.arkasis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.adapters.AdaptadorListaSolicitudes;
import com.example.arkasis.config.Config;
import com.example.arkasis.models.SolicitudDispersion;
import com.example.arkasis.models.Usuario;

import java.util.List;

public class FragmentDashboard extends Fragment {

    private BottomBarActivity parent;
    private AdaptadorListaSolicitudes adaptadorListaSolicitudes;
    private RecyclerView rvList_solicitudes;

    private View fragmentDashboardView;
    private Usuario usuario;

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
        tvBienvenida.setText("Bienvenido " + usuario.getNombre());
        inicializarListaSolicitudesLocales();
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
}