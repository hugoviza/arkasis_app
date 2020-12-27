package com.example.arkasis;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkasis.adapters.AdaptadorListaClientes;
import com.example.arkasis.adapters.AdaptadorListaMunicipios;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Municipio;
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Componentes
    private View viewFragmentBuscarCliente;
    private TextInputEditText txtBuscarCiudad, txtCURP;
    private Button btnBuscarCliente;
    private Dialog dialog;
    private Dialog dialogInfoCliente;
    private AdaptadorListaMunicipios adaptadorListaMunicipios;
    private EditText txtBuscarCiudadDialog;

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

        // Inflate the layout for this fragment
        viewFragmentBuscarCliente = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);
        // inicializarListaMunicipios(viewFragmentBuscarCliente);

        txtBuscarCiudad = viewFragmentBuscarCliente.findViewById(R.id.txtBuscarCiudad);
        btnBuscarCliente = viewFragmentBuscarCliente.findViewById(R.id.btnBuscarCliente);
        txtCURP = viewFragmentBuscarCliente.findViewById(R.id.txtCURP);
        rvList_clientes = viewFragmentBuscarCliente.findViewById(R.id.rvList_clientes);

        incializarListaClientes();

        txtBuscarCiudad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    abrirDialogSeleccionMunicipio();
                }
            }
        });

        txtBuscarCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogSeleccionMunicipio();
            }
        });

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

    public void abrirDialogSeleccionMunicipio() {
        if(dialog != null) {
            if(!dialog.isShowing()) {
                //adaptadorListaMunicipios.reiniciar();
                txtBuscarCiudadDialog.setText("");
                dialog.show();
                return;
            }
        }

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_buscar_ciudad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(viewFragmentBuscarCliente.getMeasuredWidth() - 32, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtBuscarCiudadDialog = dialog.findViewById(R.id.txtBuscarCiudad);
        Button btnCerrar = dialog.findViewById(R.id.btnCerrar);

        adaptadorListaMunicipios = new AdaptadorListaMunicipios(dialog.getContext());
        RecyclerView list_ciudades = dialog.findViewById(R.id.list_ciudades);
        list_ciudades.setHasFixedSize(true);
        list_ciudades.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        list_ciudades.setAdapter(adaptadorListaMunicipios);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtBuscarCiudadDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptadorListaMunicipios.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adaptadorListaMunicipios.setOnItemClickListener(new AdaptadorListaMunicipios.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    municipioSeleccionado = adaptadorListaMunicipios.getItem(position);
                    txtBuscarCiudad.setText(municipioSeleccionado.getStrMunicipio());
                    dialog.dismiss();
                } else {
                    municipioSeleccionado = null;
                }
            }
        });
    }

    public void abrirActivity_verInfoCliente(Cliente cliente) {
/*
        dialogInfoCliente = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_buscar_ciudad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(viewFragmentBuscarCliente.getMeasuredWidth() - 32, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtBuscarCiudadDialog = dialog.findViewById(R.id.txtBuscarCiudad);
        Button btnCerrar = dialog.findViewById(R.id.btnCerrar);*/
        try {
            DialogFragmentInfoCliente dialogFragmentInfoCliente = DialogFragmentInfoCliente.newInstance(cliente);
            dialogFragmentInfoCliente.show(getActivity().getSupportFragmentManager(), "fragment");
        } catch (Exception e) {
            //chale
        }

    }
}