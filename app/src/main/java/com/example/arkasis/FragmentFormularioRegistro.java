package com.example.arkasis;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.arkasis.componentes.DialogBuscadorSucursales;
import com.example.arkasis.componentes.DialogBuscarCoordinador;
import com.example.arkasis.config.Config;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.EstadoCivil;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.Usuario;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFormularioRegistro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFormularioRegistro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String LIMPIAR_CAMPO = "Limpiar campo";
    private final String SELECCIONAR = "Seleccione";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentFormularioRegistro() {
        // Required empty public constructor
    }

    //Componentes
    View view;
    MaterialDatePicker dpFechaNacimiento, dpFechaNacimientoConyuge;
    TextInputEditText txtFechaNacimiento, txtSucursal, txtPromotor, txtCoordinador;
    TextInputLayout layoutSucursal, layoutCoordinador;
    AutoCompleteTextView txtEstadoCivil;
    DialogBuscadorSucursales dialogBuscadorSucursales;
    DialogBuscarCoordinador dialogBuscarCoordinador;

    //Data
    Date datFechaNacimiento;
    ArrayAdapter<String> adaptadorEstadoCivil;
    Usuario usuario;

    //default data
    EstadoCivil[] arrayEstadoCivil =
            {
                    new EstadoCivil(1, "SOLTERA(O)"),
                    new EstadoCivil(2, "CASADA(O) (BIENES MANCUMUNADOS)"),
                    new EstadoCivil(3, "CASADA(O) (BIENES SEPARADOS)"),
                    new EstadoCivil(4, "UNIÓN LIBRE"),
                    new EstadoCivil(5, "VIUDA(O)"),
                    new EstadoCivil(6, "DIVORCIADA(O)"),
            };


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFormularioRegistro.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFormularioRegistro newInstance(String param1, String param2) {
        FragmentFormularioRegistro fragment = new FragmentFormularioRegistro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_formulario_registro, container, false);

        txtFechaNacimiento = view.findViewById(R.id.txtFechaNacimiento);
        txtEstadoCivil = view.findViewById(R.id.txtEstadoCivil);
        txtSucursal = view.findViewById(R.id.txtSucursal);
        layoutSucursal = view.findViewById(R.id.layoutSucursal);
        txtPromotor = view.findViewById(R.id.txtPromotor);
        layoutCoordinador = view.findViewById(R.id.layoutCoordinador);
        txtCoordinador = view.findViewById(R.id.txtCoordinador);

        usuario = Config.USUARIO_SESION;

        txtPromotor.setText(usuario.getNombre());

        inicializarDatPickers();
        inicializarSelectorEstadoCivil();
        inicializarSelectorSucursales();
        inicializarSelectorCoordinador();
        return view;
    }


    public void inicializarDatPickers() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Seleccione fecha");

        dpFechaNacimiento = builder.build();
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpFechaNacimiento.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });
        txtFechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dpFechaNacimiento.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
            }
        });
        dpFechaNacimiento.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;

                datFechaNacimiento = new Date((Long) selection + offsetFromUTC);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                txtFechaNacimiento.setText(dateFormat.format(datFechaNacimiento));
            }
        });
    }

    public void inicializarSelectorEstadoCivil() {
        List<String> lista = new ArrayList<>();
        for (EstadoCivil estadoCivil : arrayEstadoCivil) {
            lista.add(estadoCivil.getStrEstadoCivil());
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_dropdown, lista);
        txtEstadoCivil.setAdapter(adapter);
    }

    public void inicializarSelectorSucursales() {
        txtSucursal.setText("");
        dialogBuscadorSucursales = new DialogBuscadorSucursales(getContext(), view);

        txtSucursal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorSucursales.show();
            }
        });

        txtSucursal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscadorSucursales.show();
                }
            }
        });

        dialogBuscadorSucursales.setOnItemClickListener(new DialogBuscadorSucursales.OnItemClickListener() {
            @Override
            public void onItemClick(Sucursal sucursal) {
                if(sucursal != null) {
                    txtSucursal.setText(sucursal.getStrSucursal());
                    layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
                    layoutSucursal.setEndIconContentDescription(LIMPIAR_CAMPO);
                    dialogBuscadorSucursales.close();

                    limpiarSelectorCoordinador();
                    dialogBuscarCoordinador.setIdSucursal(sucursal.getIdSucursal() + "");

                } else {
                    txtSucursal.setText("");
                    limpiarSelectorCoordinador();
                }
            }
        });

        layoutSucursal.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutSucursal.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    txtSucursal.setText("");
                    dialogBuscadorSucursales.limpiar();

                    layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
                    layoutSucursal.setEndIconContentDescription(SELECCIONAR);

                    limpiarSelectorCoordinador();

                } else {
                    dialogBuscadorSucursales.show();
                }
            }
        });
    }

    public void inicializarSelectorCoordinador() {
        txtCoordinador.setText("");
        dialogBuscarCoordinador = new DialogBuscarCoordinador(getContext(), view);

        txtCoordinador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscarCoordinador.show();
            }
        });

        txtCoordinador.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscarCoordinador.show();
                }
            }
        });

        dialogBuscarCoordinador.setOnItemClickListener(new DialogBuscarCoordinador.OnItemClickListener() {
            @Override
            public void onItemClick(Coordinador coordinador) {
                if(coordinador != null) {
                    txtCoordinador.setText(coordinador.getStrNombre());
                    layoutCoordinador.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
                    layoutCoordinador.setEndIconContentDescription(LIMPIAR_CAMPO);
                    dialogBuscarCoordinador.close();
                } else {
                    txtCoordinador.setText("");
                }
            }
        });

        layoutCoordinador.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutCoordinador.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    limpiarSelectorCoordinador();
                } else {
                    dialogBuscarCoordinador.show();
                }
            }
        });
    }

    public void limpiarSelectorCoordinador() {
        txtCoordinador.setText("");
        dialogBuscarCoordinador.limpiar();
        dialogBuscarCoordinador.setIdSucursal("");
        layoutCoordinador.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
        layoutCoordinador.setEndIconContentDescription(SELECCIONAR);
    }
}