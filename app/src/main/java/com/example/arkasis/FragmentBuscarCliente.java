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
import android.widget.Toast;

import com.example.arkasis.adapters.AdaptadorListaClientes;
import com.example.arkasis.componentes.DialogBuscadorMunicipios;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APICatalogosInterface;
import com.example.arkasis.interfaces.APIClientesInterface;
import com.example.arkasis.models.Actividad;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.SaldoCliente;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentBuscarCliente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String LIMPIAR_CAMPO = "Limpiar campo";
    private final String SELECCIONAR_CIUDAD = "Seleccionar ciudad";

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

    //
    BottomBarActivity parent;

    public FragmentBuscarCliente() {
        // Required empty public constructor
    }

    public FragmentBuscarCliente(BottomBarActivity parent) {
        // Required empty public constructor
        this.parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private Boolean getEstatusConexionInternet(){
        if(parent != null) {
            return parent.getEstatusConexionInternet();
        }
        return false;
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

                    consultarSaldosDeClientes (adaptadorListaClientes.getItem(position));
                }
            }
        });
    }

    public void consultarSaldosDeClientes(Cliente cliente) {

        BottomBarActivity.abrirLoading("Consultando saldos");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIClientesInterface api = retrofit.create(APIClientesInterface.class);
        Call<ResponseAPI> apiCall = api.getSaldos(cliente);

        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body() == null || response.body().getResultado() == null) {
                    // Si no hay datos solo abrimos el dialogo de info
                    abrirActivity_verInfoCliente(cliente);
                } else {
                    List<SaldoCliente> listaSaldos = new ArrayList<>();
                    if(response.body().getSuccess()) {
                        for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                            try {
                                listaSaldos.add(new SaldoCliente(treeMap));
                            } catch (Exception e) {
                                Toast.makeText(parent, "Error al obtener saldos de clientes", Toast.LENGTH_SHORT).show();
                            }
                        }

                        cliente.setListaSaldos(listaSaldos);
                    }

                    abrirActivity_verInfoCliente(cliente);
                }

                BottomBarActivity.cerrarLoading();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Toast.makeText(parent, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                BottomBarActivity.cerrarLoading();
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
            txtBuscarCiudad.setText(municipioSeleccionado.getStrNombreMunicipioEstado());
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
                    BottomBarActivity.setFragmentFormularioRegistro(new FragmentFormularioRegistro(parent, cliente));
                    BottomBarActivity.setSelectedItem(BottomBarActivity.ITEM_ADD);
                }
            });
            dialogFragmentInfoCliente.show(getActivity().getSupportFragmentManager(), "fragment");
        } catch (Exception e) {
            //chale
        }
    }

    public void setHabilitarBusqueda(boolean bitActivo) {
        if(btnBuscarCliente != null) {
            btnBuscarCliente.setEnabled(bitActivo);
        }
    }
}