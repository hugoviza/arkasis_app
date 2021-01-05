package com.example.arkasis;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.arkasis.adapters.AdaptadorListaClientes;
import com.example.arkasis.componentes.DialogBuscadorMunicipios;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Municipio;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBuscarCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuscarCliente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String LIMPIAR_CAMPO = "Limpiar campo";
    private final String SELECCIONAR_CIUDAD = "Seleccionar ciudad";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String CANAL_DATA_CLIENTE = "data_cliente";

    //Componentes
    private View viewFragmentBuscarCliente;
    private TextInputLayout layoutCiudadOrigen;
    private TextInputEditText txtBuscarCiudad, txtCURP;
    private Button btnBuscarCliente;
    private DialogBuscadorMunicipios dialogBuscadorMunicipios;

    //Lista clientes
    private AdaptadorListaClientes adaptadorListaClientes;
    private RecyclerView rvList_clientes;

    //Variables
    private Municipio municipioSeleccionado;

    public FragmentBuscarCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBuscarCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBuscarCliente newInstance(String param1, String param2) {
        FragmentBuscarCliente fragment = new FragmentBuscarCliente();
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

        if(viewFragmentBuscarCliente == null) {
            // Inflate the layout for this fragment
            viewFragmentBuscarCliente = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);
            // inicializarListaMunicipios(viewFragmentBuscarCliente);

            txtBuscarCiudad = viewFragmentBuscarCliente.findViewById(R.id.txtBuscarCiudad);
            btnBuscarCliente = viewFragmentBuscarCliente.findViewById(R.id.btnBuscarCliente);
            txtCURP = viewFragmentBuscarCliente.findViewById(R.id.txtCURP);
            rvList_clientes = viewFragmentBuscarCliente.findViewById(R.id.rvList_clientes);
            layoutCiudadOrigen = viewFragmentBuscarCliente.findViewById(R.id.layoutCiudadOrigen);

            incializarListaClientes();
            inicializarSelectorMunicipio();

            btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cliente cliente =  new Cliente();
                    cliente.setStrMunicipio(municipioSeleccionado != null ? municipioSeleccionado.getStrMunicipio() : "");
                    cliente.setStrEstado(municipioSeleccionado != null ? municipioSeleccionado.getStrEstado() : "");
                    cliente.setStrNombre1(txtCURP.getText().toString().trim());

                    adaptadorListaClientes.buscarClientes(cliente);
                    closeKeyBoard();
                }
            });
        }

        return viewFragmentBuscarCliente;
    }

    private void closeKeyBoard(){
        View view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void incializarListaClientes() {
        adaptadorListaClientes = new AdaptadorListaClientes(viewFragmentBuscarCliente.getContext());
        rvList_clientes = viewFragmentBuscarCliente.findViewById(R.id.rvList_clientes);
        rvList_clientes.setHasFixedSize(true);
        rvList_clientes.setLayoutManager(new LinearLayoutManager(viewFragmentBuscarCliente.getContext()));
        rvList_clientes.setAdapter(adaptadorListaClientes);
        adaptadorListaClientes.setOnItemClickListener(new AdaptadorListaClientes.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == RecyclerView.NO_POSITION) {
                    //No hay nada por hacer
                } else {
                    abrirActivity_verInfoCliente(adaptadorListaClientes.getItem(position));
                }
            }
        });
    }

    public void setMunicipioSeleccionado(Municipio municipioSeleccionado) {
        this.municipioSeleccionado = municipioSeleccionado;
        if(municipioSeleccionado == null) {
            txtBuscarCiudad.setText("");
            dialogBuscadorMunicipios.limpiar();
            layoutCiudadOrigen.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutCiudadOrigen.setEndIconContentDescription(SELECCIONAR_CIUDAD);
        } else {
            txtBuscarCiudad.setText(municipioSeleccionado.getStrMunicipio());
            layoutCiudadOrigen.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutCiudadOrigen.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        closeKeyBoard();
    }

    public void inicializarSelectorMunicipio() {
        txtBuscarCiudad.setText("");
        dialogBuscadorMunicipios = new DialogBuscadorMunicipios(getContext(), viewFragmentBuscarCliente);

        txtBuscarCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorMunicipios.show();
            }
        });

        txtBuscarCiudad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscadorMunicipios.show();
                }
            }
        });

        dialogBuscadorMunicipios.setOnItemClickListener(new DialogBuscadorMunicipios.OnItemClickListener() {
            @Override
            public void onItemClick(Municipio municipio) {
                setMunicipioSeleccionado(municipio);
                dialogBuscadorMunicipios.close();
            }
        });

        layoutCiudadOrigen.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutCiudadOrigen.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    setMunicipioSeleccionado(null);
                } else {
                    dialogBuscadorMunicipios.show();
                }
            }
        });
    }

    public void abrirActivity_verInfoCliente(Cliente cliente) {
        try {
            DialogFragmentInfoCliente dialogFragmentInfoCliente = DialogFragmentInfoCliente.newInstance(cliente);
            dialogFragmentInfoCliente.setOnItemClickListener(new DialogFragmentInfoCliente.OnItemClickListener() {
                @Override
                public void onItemClick(Cliente cliente) {
                    BottomBarActivity.setFragmentFormularioRegistro(new FragmentFormularioRegistro(cliente));
                    BottomBarActivity.setSelectedItem(BottomBarActivity.ITEM_ADD);
                }
            });
            dialogFragmentInfoCliente.show(getActivity().getSupportFragmentManager(), "fragment");
        } catch (Exception e) {
            //chale
        }
    }
}