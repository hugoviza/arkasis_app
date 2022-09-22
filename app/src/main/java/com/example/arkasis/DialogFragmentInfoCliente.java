package com.example.arkasis;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.adapters.AdaptadorListaClientes;
import com.example.arkasis.adapters.AdaptadorListaSaldoCliente;
import com.example.arkasis.models.Cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link DialogFragmentInfoCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentInfoCliente extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Cliente cliente);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DialogFragmentInfoCliente() {
        // Required empty public constructor
    }

    View view ;
    ImageButton btnClose;
    Button btnNuevaSolicitud;

    private Cliente cliente;

    private TextView tvNombreCompleto, tvCurp, tvClaveElector, tvGenero, tvFechaNacimiento, tvEstadoCivil, tvNacionalidad, tvSinSaldos;
    private TextView tvCelular, tvTelefono, tvMail, tvDomicilio, tvLugarNacimiento, tvNombreConyuge, tvFechaNacimientoConyuge, tvLugarNacimientoConyuge;

    private RecyclerView rvList_saldos;
    private AdaptadorListaSaldoCliente adaptadorListaSaldoCliente;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogFragmentInfoCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFragmentInfoCliente newInstance(String param1, String param2) {
        DialogFragmentInfoCliente fragment = new DialogFragmentInfoCliente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DialogFragmentInfoCliente newInstance(Cliente cliente) {
        DialogFragmentInfoCliente fragment = new DialogFragmentInfoCliente();
        Bundle args = new Bundle();
        args.putSerializable("cliente", cliente);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_DialogWhenLarge);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            cliente = (Cliente) getArguments().getSerializable("cliente");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog_info_cliente, container, false);
        btnClose = view.findViewById(R.id.btnClose);

        tvNombreCompleto = view.findViewById(R.id.tvNombreCompleto);
        tvCurp = view.findViewById(R.id.tvCurp);
        tvClaveElector = view.findViewById(R.id.tvClaveElector);
        tvGenero = view.findViewById(R.id.tvGenero);
        tvEstadoCivil = view.findViewById(R.id.tvEstadoCivil);
        tvNacionalidad = view.findViewById(R.id.tvNacionalidad);
        tvCelular = view.findViewById(R.id.tvCelular);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        tvMail = view.findViewById(R.id.tvMail);
        tvDomicilio = view.findViewById(R.id.tvDomicilio);
        tvLugarNacimiento = view.findViewById(R.id.tvLugarNacimiento);
        tvNombreConyuge = view.findViewById(R.id.tvNombreConyuge);
        tvFechaNacimientoConyuge = view.findViewById(R.id.tvFechaNacimientoConyuge);;
        tvFechaNacimiento = view.findViewById(R.id.tvFechaNacimiento);
        btnNuevaSolicitud = view.findViewById(R.id.btnNuevaSolicitud);
        tvSinSaldos = view.findViewById(R.id.tvSinSaldos);
        tvLugarNacimientoConyuge = view.findViewById(R.id.tvLugarNacimientoConyuge);

        rvList_saldos =  view.findViewById(R.id.rvList_saldos);

        initData();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnNuevaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(cliente);
                dismiss();
            }
        });

        return view;
    }

    public void inicializarListaSaldos() {

        if(cliente.getListaSaldos().size() == 0) {
            tvSinSaldos.setVisibility(View.VISIBLE);
            btnNuevaSolicitud.setVisibility(View.VISIBLE);
        } else {
            adaptadorListaSaldoCliente = new AdaptadorListaSaldoCliente(cliente.getListaSaldos(), getContext());
            rvList_saldos.setHasFixedSize(true);
            rvList_saldos.setLayoutManager(new LinearLayoutManager(view.getContext()));
            rvList_saldos.setAdapter(adaptadorListaSaldoCliente);

            if(cliente.getSaldoDeudor() > 0) {
                btnNuevaSolicitud.setVisibility(View.GONE);
            } else {
                btnNuevaSolicitud.setVisibility(View.VISIBLE);
            }
        }
    }

    public void initData() {
        tvNombreCompleto.setText(cliente.getStrNombreCompleto());
        tvCurp.setText(cliente.getStrCurp());
        tvClaveElector.setText(cliente.getStrClaveElector());
        tvGenero.setText(cliente.getStrGenero());
        tvEstadoCivil.setText(cliente.getStrEdoCivil());
        tvNacionalidad.setText(cliente.getStrNacionalidad());
        tvCelular.setText(cliente.getStrCelular());
        tvTelefono.setText(cliente.getStrTelefono());
        tvMail.setText(cliente.getStrEmail());
        tvDomicilio.setText(cliente.getStrDomicilioCompleto());
        tvLugarNacimiento.setText(cliente.getStrLugarNacimiento());

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
        try {
            if(cliente.getDatFechaNacimiento() != null && cliente.getDatFechaNacimiento() != "") {
                Date date = sdf.parse(cliente.getDatFechaNacimiento());
                tvFechaNacimiento.setText(dateFormat.format(date));
            } else {
                tvFechaNacimiento.setText("");
            }
        } catch (Exception e) {
            tvFechaNacimiento.setText("");
        }

        try {
            if(cliente.getDatFechaNacimientoConyuge() != null && cliente.getDatFechaNacimientoConyuge() != "") {
                Date date2 = sdf.parse(cliente.getDatFechaNacimientoConyuge());
                tvFechaNacimientoConyuge.setText(dateFormat.format(date2));
            } else {
                tvFechaNacimientoConyuge.setText("");
            }
        } catch (Exception e) {
            tvFechaNacimientoConyuge.setText("");
        }

        tvLugarNacimientoConyuge.setText(cliente.getStrLugarNacimientoConyuge());
        tvNombreConyuge.setText(cliente.getStrNombreConyuge());
        inicializarListaSaldos();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}