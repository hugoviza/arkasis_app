package com.example.arkasis;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.componentes.DialogBuscadorEstados;
import com.example.arkasis.componentes.DialogBuscadorMunicipios;
import com.example.arkasis.componentes.DialogBuscadorSucursales;
import com.example.arkasis.componentes.DialogBuscarCoordinador;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APICatalogosInterface;
import com.example.arkasis.interfaces.APIClientesInterface;
import com.example.arkasis.interfaces.APISolicitudDispersion;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.Estado;
import com.example.arkasis.models.EstadoCivil;
import com.example.arkasis.models.Municipio;
import com.example.arkasis.models.ResponseAPI;
import com.example.arkasis.models.SolicitudDispersion;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.Usuario;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFormularioRegistro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String LIMPIAR_CAMPO = "Limpiar campo";
    private final String SELECCIONAR = "Seleccione";

    private BottomBarActivity parent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Cliente clienteSeleccionado;

    public FragmentFormularioRegistro() {
        // Required empty public constructor
        clienteSeleccionado = null;
    }

    public FragmentFormularioRegistro(BottomBarActivity parent) {
        clienteSeleccionado = null;
        this.parent = parent;
    }

    public FragmentFormularioRegistro(BottomBarActivity parent, Cliente clienteSeleccionado) {
        this.parent = parent;
        this.clienteSeleccionado = clienteSeleccionado;
    }

    //Componentes
    View view;
    Button btnLimpiar, btnGuardar;
    MaterialDatePicker dpFechaNacimiento, dpFechaNacimientoConyuge;
    TextInputEditText txtFechaNacimiento, txtSucursal, txtPromotor, txtCoordinador, txtCURP, txtNombre1, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtNacionalidad, txtOcupacion, txtActividad,
            txtCelular, txtTelefono, txtEmail, txtClaveElector, txtNumeroElector, txtEstadoOrigen, txtPaisOrigen,
            txtNombreConyuge, txtLugarNacimientoConyuge, txtFechaNacimientoConyuge, txtOcupacionConyuge,
            txtCodigoPostal, txtDomicilioMejora, txtDomicilioMejoraNumExt, txtDomicilioMejoraNumInt,
            txtDomicilioMejoraColonia, txtDomicilioMejoraMunicipio, txtReferenciaBancaria, txtInstitucionBancaria,
            txtIngresos, txtEgresos, txtMontoSolicitado,
            txtProductoSolicitado;
    TextInputLayout layoutSucursal, layoutCoordinador, layoutCURP,
            layoutNombre1, layoutNombre2, layoutApellidoPaterno, layoutApellidoMaterno,
            layoutFechaNacimiento, layoutEstadoCivil, layoutNacionalidad,
            layoutActividad, layoutOcupacion,
            layoutCelular, layoutTelefono, layoutEmail,
            layoutClaveElector, layoutNumeroElector,
            layoutEstadoOrigen, layoutPaisOrigen,
            layoutNombreConyuge, layoutLugarNacimientoConyuge, layoutFechaNacimientoConyuge,layoutOcupacionConyuge,
            layoutCodigoPostal, layoutDomicilioMejora, layoutDomicilioMejoraNumExt, layoutDomicilioMejoraNumInt, layoutDomicilioMejoraColonia, layoutDomicilioMejoraMunicipio,
            layoutReferenciaBancaria, layoutInstitucionBancaria,
            layoutIngresos, layoutEgresos, layoutMontoSolicitado;
    RadioGroup radioSexo, radioPlazoProducto, radioQuedateEnCasa;
    AutoCompleteTextView txtEstadoCivil;
    DialogBuscadorSucursales dialogBuscadorSucursales;
    DialogBuscarCoordinador dialogBuscarCoordinador;
    DialogBuscadorMunicipios dialogBuscadorMunicipios;
    DialogBuscadorEstados dialogBuscadorEstados;

    //Data
    Date datFechaNacimiento, datFechaNacimientoConyuge;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {

            BottomBarActivity.abrirLoading("Cargando...");
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_formulario_registro, container, false);

            btnLimpiar = view.findViewById(R.id.btnLimpiar);
            btnGuardar = view.findViewById(R.id.btnGuardar);
            txtFechaNacimiento = view.findViewById(R.id.txtFechaNacimiento);
            txtEstadoCivil = view.findViewById(R.id.txtEstadoCivil);
            txtSucursal = view.findViewById(R.id.txtSucursal);
            layoutSucursal = view.findViewById(R.id.layoutSucursal);
            txtPromotor = view.findViewById(R.id.txtPromotor);
            layoutCoordinador = view.findViewById(R.id.layoutCoordinador);
            txtCoordinador = view.findViewById(R.id.txtCoordinador);
            layoutCURP = view.findViewById(R.id.layoutCURP);
            txtCURP = view.findViewById(R.id.txtCURP);

            layoutNombre1 = view.findViewById(R.id.layoutNombre1);
            txtNombre1 = view.findViewById(R.id.txtNombre1);
            layoutNombre2 = view.findViewById(R.id.layoutNombre2);
            txtNombre2 = view.findViewById(R.id.txtNombre2);
            layoutApellidoPaterno = view.findViewById(R.id.layoutApellidoPaterno);
            txtApellidoPaterno = view.findViewById(R.id.txtApellidoPaterno);
            layoutApellidoMaterno = view.findViewById(R.id.layoutApellidoMaterno);
            txtApellidoMaterno = view.findViewById(R.id.txtApellidoMaterno);
            radioSexo = view.findViewById(R.id.radioSexo);

            layoutFechaNacimiento = view.findViewById(R.id.layoutFechaNacimiento);
            layoutEstadoCivil = view.findViewById(R.id.layoutEstadoCivil);
            layoutNacionalidad = view.findViewById(R.id.layoutNacionalidad);
            txtNacionalidad = view.findViewById(R.id.txtNacionalidad);
            layoutOcupacion = view.findViewById(R.id.layoutOcupacion);
            txtOcupacion = view.findViewById(R.id.txtOcupacion);
            layoutActividad = view.findViewById(R.id.layoutActividad);
            txtActividad = view.findViewById(R.id.txtActividad);
            layoutCelular = view.findViewById(R.id.layoutCelular);
            txtCelular = view.findViewById(R.id.txtCelular);
            layoutTelefono = view.findViewById(R.id.layoutTelefono);
            txtTelefono = view.findViewById(R.id.txtTelefono);
            layoutEmail = view.findViewById(R.id.layoutEmail);
            txtEmail = view.findViewById(R.id.txtEmail);
            layoutClaveElector = view.findViewById(R.id.layoutClaveElector);
            txtClaveElector = view.findViewById(R.id.txtClaveElector);
            layoutNumeroElector = view.findViewById(R.id.layoutNumeroElector);
            txtNumeroElector = view.findViewById(R.id.txtNumeroElector);
            layoutEstadoOrigen = view.findViewById(R.id.layoutEstadoOrigen);
            txtEstadoOrigen = view.findViewById(R.id.txtEstadoOrigen);
            layoutPaisOrigen = view.findViewById(R.id.layoutPaisOrigen);
            txtPaisOrigen = view.findViewById(R.id.txtPaisOrigen);

            layoutNombreConyuge = view.findViewById(R.id.layoutNombreConyuge);
            txtNombreConyuge = view.findViewById(R.id.txtNombreConyuge);
            layoutLugarNacimientoConyuge = view.findViewById(R.id.layoutLugarNacimientoConyuge);
            txtLugarNacimientoConyuge = view.findViewById(R.id.txtLugarNacimientoConyuge);
            layoutFechaNacimientoConyuge = view.findViewById(R.id.layoutFechaNacimientoConyuge);
            txtFechaNacimientoConyuge = view.findViewById(R.id.txtFechaNacimientoConyuge);
            layoutOcupacionConyuge = view.findViewById(R.id.layoutOcupacionConyuge);
            txtOcupacionConyuge = view.findViewById(R.id.txtOcupacionConyuge);

            layoutCodigoPostal = view.findViewById(R.id.layoutCodigoPostal);
            txtCodigoPostal = view.findViewById(R.id.txtCodigoPostal);
            layoutDomicilioMejora = view.findViewById(R.id.layoutDomicilioMejora);
            txtDomicilioMejora = view.findViewById(R.id.txtDomicilioMejora);
            layoutDomicilioMejoraNumExt = view.findViewById(R.id.layoutDomicilioMejoraNumExt);
            txtDomicilioMejoraNumExt = view.findViewById(R.id.txtDomicilioMejoraNumExt);
            layoutDomicilioMejoraNumInt = view.findViewById(R.id.layoutDomicilioMejoraNumInt);
            txtDomicilioMejoraNumInt = view.findViewById(R.id.txtDomicilioMejoraNumInt);
            layoutDomicilioMejoraColonia = view.findViewById(R.id.layoutDomicilioMejoraColonia);
            txtDomicilioMejoraColonia = view.findViewById(R.id.txtDomicilioMejoraColonia);
            layoutDomicilioMejoraMunicipio = view.findViewById(R.id.layoutDomicilioMejoraMunicipio);
            txtDomicilioMejoraMunicipio = view.findViewById(R.id.txtDomicilioMejoraMunicipio);
            layoutReferenciaBancaria = view.findViewById(R.id.layoutReferenciaBancaria);
            txtReferenciaBancaria = view.findViewById(R.id.txtReferenciaBancaria);
            layoutInstitucionBancaria = view.findViewById(R.id.layoutInstitucionBancaria);
            txtInstitucionBancaria = view.findViewById(R.id.txtInstitucionBancaria);
            layoutIngresos = view.findViewById(R.id.layoutIngresos);
            txtIngresos = view.findViewById(R.id.txtIngresos);
            layoutEgresos = view.findViewById(R.id.layoutEgresos);
            txtEgresos = view.findViewById(R.id.txtEgresos);
            layoutMontoSolicitado = view.findViewById(R.id.layoutMontoSolicitado);
            txtMontoSolicitado = view.findViewById(R.id.txtMontoSolicitado);
            txtProductoSolicitado = view.findViewById(R.id.txtProductoSolicitado);
            radioPlazoProducto = view.findViewById(R.id.radioPlazoProducto);
            radioQuedateEnCasa = view.findViewById(R.id.radioQuedateEnCasa);

            usuario = Config.USUARIO_SESION;
            txtPromotor.setText(usuario.getNombre());

            inicializarDatPickers();
            inicializarSelectorEstadoCivil();
            inicializarSelectorSucursales();
            inicializarSelectorCoordinador();
            inicializarSelectorEstadoOrigen();
            inicializarSelectorCiudadMejora();
            inicializarBuscadorCurp();

            BottomBarActivity.cerrarLoading();

            btnLimpiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limpiarVista();
                }
            });

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        guadarSolicitudDispersion();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(clienteSeleccionado != null) {
            inicializarFormulario(clienteSeleccionado);
        }

        return view;
    }

    private Boolean getEstatusConexionInternet(){
        if(parent != null) {
            return parent.getEstatusConexionInternet();
        }
        return false;
    }

    public void setCliente(Cliente cliente) {
        this.clienteSeleccionado = cliente;
    }

    private void inicializarDatPickers() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Seleccione fecha");

        //Fecha nacimiento cliente
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
                closeKeyBoard();
            }
        });

        //Fecha nacimiento conyuge
        dpFechaNacimientoConyuge = builder.build();
        txtFechaNacimientoConyuge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpFechaNacimientoConyuge.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });
        txtFechaNacimientoConyuge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dpFechaNacimientoConyuge.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
            }
        });
        dpFechaNacimientoConyuge.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;

                datFechaNacimientoConyuge = new Date((Long) selection + offsetFromUTC);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                txtFechaNacimientoConyuge.setText(dateFormat.format(datFechaNacimientoConyuge));
                closeKeyBoard();
            }
        });
    }

    private void inicializarSelectorEstadoCivil() {
        List<String> lista = new ArrayList<>();
        for (EstadoCivil estadoCivil : arrayEstadoCivil) {
            lista.add(estadoCivil.getStrEstadoCivil());
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_dropdown, lista);
        txtEstadoCivil.setAdapter(adapter);
    }

    private void inicializarSelectorSucursales() {
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
                    limpiarSelectorSucursal();
                    limpiarSelectorCoordinador();
                }
                closeKeyBoard();
            }
        });

        layoutSucursal.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutSucursal.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    limpiarSelectorSucursal();
                    limpiarSelectorCoordinador();
                    closeKeyBoard();
                } else {
                    dialogBuscadorSucursales.show();
                }
            }
        });
    }

    private void inicializarSelectorCoordinador() {
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
                closeKeyBoard();
            }
        });

        layoutCoordinador.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutCoordinador.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    limpiarSelectorCoordinador();
                    closeKeyBoard();
                } else {
                    dialogBuscarCoordinador.show();
                }
            }
        });
    }

    private void limpiarSelectorCoordinador() {
        txtCoordinador.setText("");
        dialogBuscarCoordinador.limpiar();
        dialogBuscarCoordinador.setIdSucursal("");
        layoutCoordinador.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
        layoutCoordinador.setEndIconContentDescription(SELECCIONAR);
    }

    private void limpiarSelectorSucursal() {
        txtSucursal.setText("");
        dialogBuscadorSucursales.limpiar();
        layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
        layoutSucursal.setEndIconContentDescription(SELECCIONAR);
    }

    private void inicializarSelectorCiudadMejora() {
        dialogBuscadorMunicipios = new DialogBuscadorMunicipios(getContext(), view);
        txtDomicilioMejoraMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorMunicipios.show();
            }
        });

        txtDomicilioMejoraMunicipio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                if(municipio != null) {
                    seleccionarDomicilioMejoraMunicipio(municipio.getStrNombreMunicipioEstado());
                    dialogBuscadorMunicipios.close();
                } else {
                    seleccionarDomicilioMejoraMunicipio(null);
                }
                closeKeyBoard();
            }
        });

        layoutDomicilioMejoraMunicipio.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDomicilioMejoraMunicipio.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarDomicilioMejoraMunicipio(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorMunicipios.show();
                }
            }
        });
    }

    private void inicializarSelectorEstadoOrigen() {
        dialogBuscadorEstados = new DialogBuscadorEstados(getContext(), view);
        txtEstadoOrigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorEstados.show();
            }
        });

        txtEstadoOrigen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscadorEstados.show();
                }
            }
        });

        dialogBuscadorEstados.setOnItemClickListener(new DialogBuscadorEstados.OnItemClickListener() {
            @Override
            public void onItemClick(Estado estado) {
                if(estado != null) {
                    seleccionarEstadoOrigen(estado.getStrEstado());
                    dialogBuscadorEstados.close();
                } else {
                    seleccionarEstadoOrigen(null);
                }
                closeKeyBoard();
            }
        });

        layoutEstadoOrigen.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutEstadoOrigen.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarEstadoOrigen(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorEstados.show();
                }
            }
        });
    }

    private void seleccionarDomicilioMejoraMunicipio(String strMunicipio) {
        if(strMunicipio == null) {
            txtDomicilioMejoraMunicipio.setText("");
            dialogBuscadorMunicipios.limpiar();
            layoutDomicilioMejoraMunicipio.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutDomicilioMejoraMunicipio.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtDomicilioMejoraMunicipio.setText(strMunicipio);
            layoutDomicilioMejoraMunicipio.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutDomicilioMejoraMunicipio.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
    }

    private void seleccionarEstadoOrigen(String strEstado) {
        if(strEstado == null) {
            txtEstadoOrigen.setText("");
            dialogBuscadorEstados.limpiar();
            layoutEstadoOrigen.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutEstadoOrigen.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtEstadoOrigen.setText(strEstado);
            layoutEstadoOrigen.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutEstadoOrigen.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
    }

    private void inicializarBuscadorCurp() {
        txtCURP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtCURP.getText().toString().trim().length() == 18) {
                    validarCURP();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        layoutCURP.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(layoutCURP.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_search_24);
                    layoutCURP.setEndIconContentDescription("Buscar");
                    limpiarVista();
                } else {
                    if(validarFormatoCURP()) {
                        layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
                        layoutCURP.setEndIconContentDescription(LIMPIAR_CAMPO);
                        layoutCURP.setError(null);
                        buscarCURP();

                    } else {
                        layoutCURP.setError("CURP no válida");
                    }
                }
            }
        });
    }

    private boolean validarCURP() {
        if(txtCURP.getText().toString().trim().length() == 18) {
            layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_search_24);
            layoutCURP.setEndIconContentDescription("Buscar");

            if(validarFormatoCURP()) {
                layoutCURP.setError(null);
                return true;
            } else {
                layoutCURP.setError("CURP no válida");
            }
        } else {
            layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_search_24);
            layoutCURP.setEndIconContentDescription("Buscar");
            layoutCURP.setError("CURP no válida");
        }
        return false;
    }

    private boolean validarFormatoCURP() {
        String curp = txtCURP.getText().toString().trim();
        if(curp.length() == 18) {
            Character[] caracteres = {'L', 'L', 'L', 'L', 'D', 'D', 'D', 'D', 'D', 'D', 'L', 'L', 'L', 'L', 'L', 'L', 'D', 'D'};
            Boolean curpValida = true;

            for (int i = 0; i < caracteres.length; i++) {
                Character tipo = caracteres[i];
                if(tipo == 'L') {
                    //Letra
                    if(!Character.isLetter(curp.charAt(i))) {
                        curpValida = false;
                    }
                } else {
                    //Digito
                    if(!Character.isDigit(curp.charAt(i))) {
                        curpValida = false;
                    }
                }
            }
            return curpValida;
        }

        return false;
    }

    private void buscarCURP() {
        if(!getEstatusConexionInternet()) {
            Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();
            return;
        }

        BottomBarActivity.abrirLoading("Validando curp");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Cliente cliente = new Cliente();
        cliente.setStrCurp(txtCURP.getText().toString());

        APIClientesInterface api = retrofit.create(APIClientesInterface.class);
        Call<ResponseAPI> apiCall = api.getClienteByCurp(cliente);
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    Toast.makeText(getContext(), "Cliente no encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if(response.body().getSuccess()) {
                            ArrayList<LinkedTreeMap<Object, Object>> linkedTreeMaps = (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado();
                            if(linkedTreeMaps.size() == 1) {
                                inicializarFormulario(new Cliente(linkedTreeMaps.get(0)));
                            } else {
                                Toast.makeText(getContext(), "Se han encontrado "+linkedTreeMaps.size()+" resultados", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Error al cargar cliente", Toast.LENGTH_SHORT).show();
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

    public void inicializarFormulario(Cliente cliente) {
        //Primero limpiamos la vista y despues asignamos datos
        limpiarVista();

        txtCURP.setText(cliente.getStrCurp());
        if(validarCURP()) {
            layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutCURP.setEndIconContentDescription(LIMPIAR_CAMPO);
        }

        txtNombre1.setText(cliente.getStrNombre1());
        txtNombre2.setText(cliente.getStrNombre2());
        txtApellidoPaterno.setText(cliente.getStrApellidoPaterno());
        txtApellidoMaterno.setText(cliente.getStrApellidoMaterno());

        //GENERO
        switch (cliente.getStrGenero().toUpperCase()) {
            case "HOMBRE":
                radioSexo.check(R.id.radioSexoHombre);
                break;
            case "MUJER":
                radioSexo.check(R.id.radioSexoMujer);
                break;
            default:
                radioSexo.check(R.id.radioSexoMujer);
        }

        //FECHA NACIMIENTO
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            datFechaNacimiento = sdf.parse(cliente.getDatFechaNacimiento());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            txtFechaNacimiento.setText(dateFormat.format(datFechaNacimiento));
        } catch (Exception e) {
            datFechaNacimiento = null;
            txtFechaNacimiento.setText("");
        }

        //ESTADO CIVIL
        for (EstadoCivil estadoCivil: arrayEstadoCivil) {
            if(estadoCivil.getIdEstadoCivil() == Integer.parseInt(cliente.getIdEdoCivil())) {
                txtEstadoCivil.setText(estadoCivil.getStrEstadoCivil());
                break;
            }
        }

        txtNacionalidad.setText(cliente.getStrNacionalidad());
        txtOcupacion.setText(cliente.getStrOcupacion());
        txtActividad.setText(cliente.getStrDescripcionActividad());
        txtCelular.setText(cliente.getStrCelular());
        txtTelefono.setText(cliente.getStrTelefono());
        txtEmail.setText(cliente.getStrEmail());
        txtClaveElector.setText(cliente.getStrClaveElector());
        txtNumeroElector.setText(cliente.getStrNumeroElector());
        txtEstadoOrigen.setText(cliente.getStrEstadoNacimiento());
        txtPaisOrigen.setText(cliente.getStrPaisNacimiento());
        txtCodigoPostal.setText(cliente.getStrCodigoPostal());
        txtDomicilioMejora.setText(cliente.getStrDomicilio());
        txtDomicilioMejoraNumExt.setText(cliente.getStrDireccionNumero());
        txtDomicilioMejoraNumInt.setText(cliente.getStrDireccionNumeroInterno());
        txtDomicilioMejoraColonia.setText(cliente.getStrColonia());

        //CIUDAD DE LA MEJORA
        seleccionarDomicilioMejoraMunicipio(cliente.getStrMunicipio() != "" ? cliente.getStrMunicipio() : null);

        txtReferenciaBancaria.setText("");
        txtInstitucionBancaria.setText("");

        txtIngresos.setText("");
        txtEgresos.setText("");
        txtMontoSolicitado.setText("");


        txtProductoSolicitado.setText("");
        radioPlazoProducto.check(R.id.radioPlazoProducto12Meses);

        radioQuedateEnCasa.check(R.id.radioQuedateEnCasaNoAplica);
    }

    public void limpiarVista() {

        limpiarSelectorSucursal();
        limpiarSelectorCoordinador();

        clienteSeleccionado = null;
        txtCURP.setText("");
        layoutCURP.setEndIconDrawable(R.drawable.ic_baseline_search_24);
        layoutCURP.setEndIconContentDescription("Buscar");
        layoutCURP.setError(null);

        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        //GENERO
        radioSexo.check(R.id.radioSexoMujer);
        //FECHA NACIMIENTO
        datFechaNacimiento = null;
        txtFechaNacimiento.setText("");
        //CONYUGE
        txtNombreConyuge.setText("");
        txtLugarNacimientoConyuge.setText("");
        datFechaNacimientoConyuge = null;
        txtFechaNacimientoConyuge.setText("");
        txtOcupacionConyuge.setText("");
        //ESTADO CIVIL
        txtEstadoCivil.setText("");
        txtNacionalidad.setText("");
        txtOcupacion.setText("");
        txtActividad.setText("");
        txtCelular.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtClaveElector.setText("");
        txtNumeroElector.setText("");
        seleccionarEstadoOrigen(null);
        txtPaisOrigen.setText("");
        txtCodigoPostal.setText("");
        txtDomicilioMejora.setText("");
        txtDomicilioMejoraNumExt.setText("");
        txtDomicilioMejoraNumInt.setText("");
        txtDomicilioMejoraColonia.setText("");
        //CIUDAD DE LA MEJORA
        seleccionarDomicilioMejoraMunicipio(null);
        txtReferenciaBancaria.setText("");
        txtInstitucionBancaria.setText("");
        txtIngresos.setText("");
        txtEgresos.setText("");
        txtMontoSolicitado.setText("");
        txtProductoSolicitado.setText("");
        radioPlazoProducto.check(R.id.radioPlazoProducto12Meses);
        radioQuedateEnCasa.check(R.id.radioQuedateEnCasaNoAplica);

        txtCURP.requestFocus();
    }

    private void guadarSolicitudDispersion() {
        if(validarSolicitudDispersion()) {

            SolicitudDispersion solicitudDispersion = armarObjetoSolicitud();

            if(getEstatusConexionInternet()) {
                guadarSolicitudDispersionSERVER(solicitudDispersion);
            } else {
                guadarSolicitudDispersionLOCAL(solicitudDispersion);
            }
        }
    }

    private void guadarSolicitudDispersionLOCAL(SolicitudDispersion solicitudDispersion) {
        TableSolicitudesDispersion table = new TableSolicitudesDispersion(getContext());
        table.insertar(solicitudDispersion);

        Toast.makeText(getContext(), "Registro guardado localmente", Toast.LENGTH_SHORT).show();
        limpiarVista();
    }

    private void guadarSolicitudDispersionSERVER(SolicitudDispersion solicitudDispersion) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BottomBarActivity.abrirLoading("Guardando...");

        APISolicitudDispersion api = retrofit.create(APISolicitudDispersion.class);
        Call<ResponseAPI> apiCall = api.addSolicitud(solicitudDispersion);
        apiCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().getResultado() == null) {
                    Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                    //No hay nada por hacer
                } else {
                    Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarVista();
                }

                BottomBarActivity.cerrarLoading();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                BottomBarActivity.cerrarLoading();
            }
        });
    }

    private SolicitudDispersion armarObjetoSolicitud() {

        SolicitudDispersion solicitudDispersion = new SolicitudDispersion();
        solicitudDispersion.setStrStatusSolicitud("TRÁMITE");

        solicitudDispersion.setIdPromotor("");
        solicitudDispersion.setStrUsuarioPromotor(usuario.getUser());
        solicitudDispersion.setStrPromotor(usuario.getNombre());
        solicitudDispersion.setStrCordinador(txtCoordinador.getText().toString().trim());
        solicitudDispersion.setIdSucursal(dialogBuscadorSucursales.getSelectedItem()  != null ? dialogBuscadorSucursales.getSelectedItem().getIdSucursal() + "" : "");
        solicitudDispersion.setIdCliente(clienteSeleccionado != null ? clienteSeleccionado.getIdCliente() : "");

        Date hoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss", new Locale("es", "ES"));
        solicitudDispersion.setStrFechaAlta(dateFormat.format(hoy));

        solicitudDispersion.setStrCURP(txtCURP.getText().toString());
        solicitudDispersion.setStrNombre1(txtNombre1.getText().toString());
        solicitudDispersion.setStrNombre2(txtNombre2.getText().toString());
        solicitudDispersion.setStrApellidoPaterno(txtApellidoPaterno.getText().toString());
        solicitudDispersion.setStrApellidoMaterno(txtApellidoMaterno.getText().toString());

        solicitudDispersion.setIdGenero(radioSexo.getCheckedRadioButtonId() == R.id.radioSexoMujer ? "2" : "1");
        solicitudDispersion.setStrGenero(radioSexo.getCheckedRadioButtonId() == R.id.radioSexoMujer ? "MUJER" : "HOMBRE");

        dateFormat = new SimpleDateFormat("YYYY/MM/dd", new Locale("es", "ES"));
        solicitudDispersion.setStrFechaNacimiento(datFechaNacimiento != null ? dateFormat.format(datFechaNacimiento) : "");

        String idEstadoCivil = "";
        for (int i = 0; i < arrayEstadoCivil.length; i++){
            if(arrayEstadoCivil[i].getStrEstadoCivil().trim() == txtEstadoCivil.getText().toString()) {
                idEstadoCivil = arrayEstadoCivil[i].getIdEstadoCivil() + "";
            }
        }

        solicitudDispersion.setIdEstadoCivil(idEstadoCivil);
        solicitudDispersion.setStrEstadoCivil(txtEstadoCivil.getText().toString().trim());

        solicitudDispersion.setStrNacionalidad(txtNacionalidad.getText().toString().trim());

        solicitudDispersion.setStrOcupacion(txtOcupacion.getText().toString().trim());
        solicitudDispersion.setStrActividad(txtActividad.getText().toString().trim());
        solicitudDispersion.setIdActividad("");

        solicitudDispersion.setStrCelular(txtCelular.getText().toString().trim());
        solicitudDispersion.setStrTelefono(txtTelefono.getText().toString().trim());
        solicitudDispersion.setStrEmail(txtEmail.getText().toString().trim());

        solicitudDispersion.setStrClaveINE(txtClaveElector.getText().toString().trim());
        solicitudDispersion.setStrNumeroINE(txtNumeroElector.getText().toString().trim());

        solicitudDispersion.setStrEstadoNacimiento(txtEstadoOrigen.getText().toString());
        solicitudDispersion.setStrPais(txtPaisOrigen.getText().toString());

        solicitudDispersion.setStrDomicilioCodigoPostal(txtCodigoPostal.getText().toString());
        solicitudDispersion.setStrDomicilio(txtDomicilioMejora.getText().toString());
        solicitudDispersion.setStrDomicilioNumExt(txtDomicilioMejoraNumExt.getText().toString());
        solicitudDispersion.setStrDomicilioNumInt(txtDomicilioMejoraNumInt.getText().toString());
        solicitudDispersion.setStrDomicilioColonia(txtDomicilioMejoraColonia.getText().toString());
        solicitudDispersion.setIdDomicilioMunicipio(dialogBuscadorMunicipios.getSelectedItem() != null ? dialogBuscadorMunicipios.getSelectedItem().getIdMunicipio() : "");
        solicitudDispersion.setIdDomicilioEstado(dialogBuscadorMunicipios.getSelectedItem() != null ? dialogBuscadorMunicipios.getSelectedItem().getIdEstado() : "");
        solicitudDispersion.setStrDomicilioMunicipio(dialogBuscadorMunicipios.getSelectedItem() != null ? dialogBuscadorMunicipios.getSelectedItem().getStrMunicipio() : "");
        solicitudDispersion.setStrDomicilioEstado(dialogBuscadorMunicipios.getSelectedItem() != null ? dialogBuscadorMunicipios.getSelectedItem().getStrEstado() : "");

        solicitudDispersion.setStrReferenciaBancaria(txtReferenciaBancaria.getText().toString());
        solicitudDispersion.setStrBanco(txtInstitucionBancaria.getText().toString());

        solicitudDispersion.setDblIngresos(Double.parseDouble(txtIngresos.getText().toString().trim()));
        solicitudDispersion.setDblEgresos(Double.parseDouble(txtEgresos.getText().toString().trim()));
        solicitudDispersion.setDblMontoSolicitado(Double.parseDouble(txtMontoSolicitado.getText().toString().trim()));
        solicitudDispersion.setDblMontoAutorizado(Double.parseDouble(txtMontoSolicitado.getText().toString().trim()));

        solicitudDispersion.setStrNombreConyuge(txtNombreConyuge.getText().toString().trim());
        solicitudDispersion.setStrOcupacionConyuge(txtOcupacionConyuge.getText().toString().trim());
        solicitudDispersion.setStrLugarNacimientoConyuge(txtLugarNacimientoConyuge.getText().toString().trim());
        solicitudDispersion.setStrFechaNacimientoConyuge(datFechaNacimientoConyuge != null ? dateFormat.format(datFechaNacimientoConyuge) : "");

        solicitudDispersion.setStrProducto(txtProductoSolicitado.getText().toString().trim());
        solicitudDispersion.setIntPlazo(radioPlazoProducto.getCheckedRadioButtonId() == R.id.radioPlazoProducto12Meses ? 12 : 24);
        solicitudDispersion.setIntQuedateCasa(radioQuedateEnCasa.getCheckedRadioButtonId() == R.id.radioQuedateEnCasaAplica ? 1 : 0);

        return solicitudDispersion;
    }

    private boolean validarSolicitudDispersion() {

        if(txtSucursal.getText().toString().trim().length() == 0) {
            layoutSucursal.setError("Seleccione una sucursal");
            txtSucursal.requestFocus();
            return false;
        } else {
            layoutSucursal.setError(null);
        }

        if(txtCoordinador.getText().toString().trim().length() == 0) {
            layoutCoordinador.setError("Seleccione un coordinador");
            txtCoordinador.requestFocus();
            return false;
        } else {
            layoutCoordinador.setError(null);
        }

        if(!validarCURP()) {
            txtCURP.requestFocus();
            return false;
        }

        if(txtNombre1.getText().toString().trim().length() == 0) {
            layoutNombre1.setError("Ingrese primer nombre");
            txtNombre1.requestFocus();
            return false;
        } else {
            layoutNombre1.setError(null);
        }

        if(txtApellidoPaterno.getText().toString().trim().length() == 0) {
            layoutApellidoPaterno.setError("Ingrese apellido paterno");
            txtApellidoPaterno.requestFocus();
            return false;
        } else {
            layoutApellidoPaterno.setError(null);
        }

        if(txtApellidoMaterno.getText().toString().trim().length() == 0) {
            layoutApellidoMaterno.setError("Ingrese apellido materno");
            txtApellidoMaterno.requestFocus();
            return false;
        } else {
            layoutApellidoMaterno.setError(null);
        }

        if(txtFechaNacimiento.getText().toString().trim().length() == 0) {
            layoutFechaNacimiento.setError("Seleccione fecha nacimiento");
            txtFechaNacimiento.requestFocus();
            return false;
        } else {
            layoutFechaNacimiento.setError(null);
        }

        if(txtEstadoCivil.getText().toString().trim().length() == 0) {
            layoutEstadoCivil.setError("Seleccione estado civil");
            txtEstadoCivil.requestFocus();
            return false;
        } else {
            layoutEstadoCivil.setError(null);
        }

        if(txtNacionalidad.getText().toString().trim().length() == 0) {
            layoutNacionalidad.setError("Ingrese nacionalidad");
            txtNacionalidad.requestFocus();
            return false;
        } else {
            layoutNacionalidad.setError(null);
        }

        if(txtOcupacion.getText().toString().trim().length() == 0) {
            layoutOcupacion.setError("Ingrese ocupación");
            txtOcupacion.requestFocus();
            return false;
        } else {
            layoutOcupacion.setError(null);
        }

        if(txtActividad.getText().toString().trim().length() == 0) {
            layoutActividad.setError("Ingrese actividad");
            txtActividad.requestFocus();
            return false;
        } else {
            layoutActividad.setError(null);
        }

        if(txtCelular.getText().toString().trim().length() == 0) {
            layoutCelular.setError("Ingrese número celular");
            txtCelular.requestFocus();
            return false;
        } else {
            layoutCelular.setError(null);
        }

        if(txtEmail.getText().toString().trim().length() == 0) {
            layoutEmail.setError("Ingrese correo electrónico");
            txtEmail.requestFocus();
            return false;
        } else {
            layoutEmail.setError(null);
        }

        if(txtClaveElector.getText().toString().trim().length() == 0) {
            layoutClaveElector.setError("Ingrese clave de elector");
            txtClaveElector.requestFocus();
            return false;
        } else {
            layoutClaveElector.setError(null);
        }

        if(txtNumeroElector.getText().toString().trim().length() == 0) {
            layoutNumeroElector.setError("Ingrese número de elector");
            txtNumeroElector.requestFocus();
            return false;
        } else {
            layoutNumeroElector.setError(null);
        }

        if(txtEstadoOrigen.getText().toString().trim().length() == 0) {
            layoutEstadoOrigen.setError("Ingrese estado de origen");
            txtEstadoOrigen.requestFocus();
            return false;
        } else {
            layoutEstadoOrigen.setError(null);
        }

        if(txtPaisOrigen.getText().toString().trim().length() == 0) {
            layoutPaisOrigen.setError("Ingrese país de origen");
            txtPaisOrigen.requestFocus();
            return false;
        } else {
            layoutPaisOrigen.setError(null);
        }

        if(txtCodigoPostal.getText().toString().trim().length() == 0) {
            txtCodigoPostal.setError("Ingrese código postal");
            txtCodigoPostal.requestFocus();
            return false;
        } else {
            txtCodigoPostal.setError(null);
        }

        if(txtDomicilioMejora.getText().toString().trim().length() == 0) {
            layoutDomicilioMejora.setError("Ingrese dirección de la mejora");
            txtDomicilioMejora.requestFocus();
            return false;
        } else {
            layoutDomicilioMejora.setError(null);
        }

        if(txtDomicilioMejoraNumExt.getText().toString().trim().length() == 0) {
            layoutDomicilioMejoraNumExt.setError("Ingrese número exterior");
            txtDomicilioMejoraNumExt.requestFocus();
            return false;
        } else {
            layoutDomicilioMejoraNumExt.setError(null);
        }

        if(txtDomicilioMejoraColonia.getText().toString().trim().length() == 0) {
            layoutDomicilioMejoraColonia.setError("Ingrese colonia");
            txtDomicilioMejoraColonia.requestFocus();
            return false;
        } else {
            layoutDomicilioMejoraColonia.setError(null);
        }

        if(txtDomicilioMejoraMunicipio.getText().toString().trim().length() == 0) {
            layoutDomicilioMejoraMunicipio.setError("Selccione municipio");
            txtDomicilioMejoraMunicipio.requestFocus();
            return false;
        } else {
            layoutDomicilioMejoraMunicipio.setError(null);
        }

        if(txtReferenciaBancaria.getText().toString().trim().length() == 0) {
            layoutReferenciaBancaria.setError("Ingrese referencia bancaria");
            txtReferenciaBancaria.requestFocus();
            return false;
        } else {
            layoutReferenciaBancaria.setError(null);
        }

        if(txtInstitucionBancaria.getText().toString().trim().length() == 0) {
            layoutInstitucionBancaria.setError("Ingrese nombre del banco");
            txtInstitucionBancaria.requestFocus();
            return false;
        } else {
            layoutInstitucionBancaria.setError(null);
        }

        if(txtIngresos.getText().toString().trim().length() == 0) {
            layoutIngresos.setError("Ingrese una cantidad");
            txtIngresos.requestFocus();
            return false;
        } else {
            layoutIngresos.setError(null);
        }

        if(txtEgresos.getText().toString().trim().length() == 0) {
            layoutEgresos.setError("Ingrese una cantidad");
            txtEgresos.requestFocus();
            return false;
        } else {
            layoutEgresos.setError(null);
        }

        if(txtMontoSolicitado.getText().toString().trim().length() == 0) {
            layoutMontoSolicitado.setError("Ingrese monto solicitado");
            txtMontoSolicitado.requestFocus();
            return false;
        } else {
            layoutMontoSolicitado.setError(null);
        }

        return true;
    }

    private void closeKeyBoard(){
        View view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}