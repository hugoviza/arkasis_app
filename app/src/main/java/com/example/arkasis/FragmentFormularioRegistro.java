package com.example.arkasis;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.componentes.DialogBuscadorActividades;
import com.example.arkasis.componentes.DialogBuscadorEstados;
import com.example.arkasis.componentes.DialogBuscadorMunicipios;
import com.example.arkasis.componentes.DialogBuscadorSucursales;
import com.example.arkasis.componentes.DialogBuscarCoordinador;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIClientesInterface;
import com.example.arkasis.interfaces.APISolicitudDispersion;
import com.example.arkasis.models.Actividad;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
    private final int REQUEST_PERMISSION_GALERY = 100;
    private final int REQUEST_PERMISSION_CAMERA = 101;
    private final int REQUEST_IMAGE = 101;


    private final String DOCUMENTO_INE_FRONTAL = "evidencia_ine_frontal";
    private final String DOCUMENTO_INE_REVERSO = "evidencia_ine_reverso";
    private final String DOCUMENTO_FOTO_PERFIL = "evidencia_foto_perfil";
    private final String DOCUMENTO_COMPROBANTE_DOMICILIO = "evidencia_comprobante_domicilio";

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
    Button btnLimpiar, btnGuardar,
            btnAbrirCamaraFotoINEFrontal, btnAbrirGaleriaFotoINEFrontal,
            btnAbrirCamaraFotoINEReverso, btnAbrirGaleriaFotoINEReverso,
            btnAbrirCamaraFotoPerfil, btnAbrirGaleriaFotoPerfil,
            btnAbrirCamaraFotoComprobanteDomicilio, btnAbrirGaleriaFotoComprobanteDomicilio
    ;
    LinearLayout llFotoINEFrontal, llFotoINEReverso, llFotoPerfil, llFotoComprobanteDomicilio;
    ImageButton imgFotoINEFrontal, imgFotoINEReverso, imgFotoPerfil, imgFotoComprobanteDomicilio;
    MaterialDatePicker dpFechaNacimiento, dpFechaNacimientoConyuge;
    TextInputEditText txtFechaNacimiento, txtSucursal, txtPromotor, txtCoordinador, txtCURP, txtNombre1, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtNacionalidad, txtOcupacion, txtActividad,
            txtCelular, txtTelefono, txtEmail, txtClaveElector, txtNumeroElector, txtEstadoOrigen, txtPaisOrigen,
            txtNombreConyuge, txtLugarNacimientoConyuge, txtFechaNacimientoConyuge, txtOcupacionConyuge,
            txtCodigoPostal, txtDomicilioMejora, txtDomicilioMejoraNumExt, txtDomicilioMejoraNumInt,
            txtDomicilioMejoraColonia, txtDomicilioMejoraMunicipio, txtReferenciaBancaria, txtInstitucionBancaria,
            txtIngresos, txtEgresos, txtMontoSolicitadoMejoraVivienda, txtMontoSolicitadoEquipandoHogar, txtProductoEquipandoHogar,
            txtCodigoPostal_mejoraVivienda, txtDomicilio_mejoraVivienda, txtNumExt_mejoraVivienda, txtNumInt_mejoraVivienda, txtColonia_mejoraVivienda, txtMunicipio_mejoraVivienda
    ;
    TextInputLayout layoutSucursal, layoutCoordinador, layoutCURP, layoutPromotor,
            layoutNombre1, layoutNombre2, layoutApellidoPaterno, layoutApellidoMaterno,
            layoutFechaNacimiento, layoutEstadoCivil, layoutNacionalidad,
            layoutActividad, layoutOcupacion,
            layoutCelular, layoutTelefono, layoutEmail,
            layoutClaveElector, layoutNumeroElector,
            layoutEstadoOrigen, layoutPaisOrigen,
            layoutNombreConyuge, layoutLugarNacimientoConyuge, layoutFechaNacimientoConyuge,layoutOcupacionConyuge,
            layoutCodigoPostal, layoutDomicilioMejora, layoutDomicilioMejoraNumExt, layoutDomicilioMejoraNumInt, layoutDomicilioMejoraColonia, layoutDomicilioMejoraMunicipio,
            layoutReferenciaBancaria, layoutInstitucionBancaria,
            layoutIngresos, layoutEgresos, layoutMontoSolicitadoMejoraVivienda, layoutMontoSolicitadoEquipandoHogar, layoutProductoEquipandoHogar,
            layoutCodigoPostal_mejoraVivienda, layoutDomicilio_mejoraVivienda, layoutNumExt_mejoraVivienda, layoutNumInt_mejoraVivienda, layoutColonia_mejoraVivienda, layoutMunicipio_mejoraVivienda;
    RadioGroup radioSexo, radioPlazoProducto, radioQuedateEnCasa, radioProducto;
    AutoCompleteTextView txtEstadoCivil;
    DialogBuscadorSucursales dialogBuscadorSucursales;
    DialogBuscarCoordinador dialogBuscarCoordinador, dialogBuscarPromotor;
    DialogBuscadorMunicipios dialogBuscadorMunicipios, dialogBuscadorMunicipios_mejoraVivienda;
    DialogBuscadorEstados dialogBuscadorEstados;
    DialogBuscadorActividades dialogBuscadorActividades;

    //Data
    Date datFechaNacimiento, datFechaNacimientoConyuge;
    ArrayAdapter<String> adaptadorEstadoCivil;
    Usuario usuario;
    Estado estadoNacimiento;
    Municipio municipioMejora;
    Municipio municipio_mejoraVivienda;
    Bitmap bmFotoINEFrontal, bmFotoINEReverso, bmFotoPerfil, bmFotoComprobanteDomicilio;
    String strFotoINEFrontal, strFotoINEReverso, strFotoPerfil, strFotoComprobanteDomicilio;

    String currentPic = null;

    //Helper
    int tipoImagenSolicitada = 0;
    int requestPermisoSolicitado = 0;


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
            layoutPromotor = view.findViewById(R.id.layoutPromotor);
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
            layoutMontoSolicitadoMejoraVivienda = view.findViewById(R.id.layoutMontoSolicitadoMejoraVivienda);
            layoutMontoSolicitadoEquipandoHogar = view.findViewById(R.id.layoutMontoSolicitadoEquipandoHogar);
            txtMontoSolicitadoMejoraVivienda = view.findViewById(R.id.txtMontoSolicitadoMejoraVivienda);
            txtMontoSolicitadoEquipandoHogar = view.findViewById(R.id.txtMontoSolicitadoEquipandoHogar);
            radioPlazoProducto = view.findViewById(R.id.radioPlazoProducto);
            radioQuedateEnCasa = view.findViewById(R.id.radioQuedateEnCasa);
            radioProducto = view.findViewById(R.id.radioProducto);

            txtProductoEquipandoHogar = view.findViewById(R.id.txtProductoEquipandoHogar);
            layoutProductoEquipandoHogar = view.findViewById(R.id.layoutProductoEquipandoHogar);

            txtCodigoPostal_mejoraVivienda = view.findViewById(R.id.txtCodigoPostal_mejoraVivienda);
            layoutCodigoPostal_mejoraVivienda = view.findViewById(R.id.layoutCodigoPostal_mejoraVivienda);
            txtDomicilio_mejoraVivienda = view.findViewById(R.id.txtDomicilio_mejoraVivienda);
            layoutDomicilio_mejoraVivienda = view.findViewById(R.id.layoutDomicilio_mejoraVivienda);
            txtNumExt_mejoraVivienda = view.findViewById(R.id.txtNumExt_mejoraVivienda);
            layoutNumExt_mejoraVivienda = view.findViewById(R.id.layoutNumExt_mejoraVivienda);
            txtNumInt_mejoraVivienda = view.findViewById(R.id.txtNumInt_mejoraVivienda);
            layoutNumInt_mejoraVivienda = view.findViewById(R.id.layoutNumInt_mejoraVivienda);
            txtColonia_mejoraVivienda = view.findViewById(R.id.txtColonia_mejoraVivienda);
            layoutColonia_mejoraVivienda = view.findViewById(R.id.layoutColonia_mejoraVivienda);
            txtMunicipio_mejoraVivienda = view.findViewById(R.id.txtMunicipio_mejoraVivienda);
            layoutMunicipio_mejoraVivienda = view.findViewById(R.id.layoutMunicipio_mejoraVivienda);

            imgFotoINEFrontal = view.findViewById(R.id.imgFotoINEFrontal);
            imgFotoINEReverso = view.findViewById(R.id.imgFotoINEReverso);
            imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil);
            imgFotoComprobanteDomicilio = view.findViewById(R.id.imgFotoComprobanteDomicilio);

            btnAbrirCamaraFotoINEFrontal = view.findViewById(R.id.btnAbrirCamaraFotoINEFrontal);
            btnAbrirGaleriaFotoINEFrontal = view.findViewById(R.id.btnAbrirGaleriaFotoINEFrontal);
            btnAbrirCamaraFotoINEReverso = view.findViewById(R.id.btnAbrirCamaraFotoINEReverso);
            btnAbrirGaleriaFotoINEReverso = view.findViewById(R.id.btnAbrirGaleriaFotoINEReverso);
            btnAbrirCamaraFotoPerfil = view.findViewById(R.id.btnAbrirCamaraFotoPerfil);
            btnAbrirGaleriaFotoPerfil = view.findViewById(R.id.btnAbrirGaleriaFotoPerfil);
            btnAbrirCamaraFotoComprobanteDomicilio = view.findViewById(R.id.btnAbrirCamaraFotoComprobanteDomicilio);
            btnAbrirGaleriaFotoComprobanteDomicilio = view.findViewById(R.id.btnAbrirGaleriaFotoComprobanteDomicilio);

            llFotoINEFrontal = view.findViewById(R.id.llFotoINEFrontal);
            llFotoINEReverso = view.findViewById(R.id.llFotoINEReverso);
            llFotoPerfil = view.findViewById(R.id.llFotoPerfil);
            llFotoComprobanteDomicilio = view.findViewById(R.id.llFotoComprobanteDomicilio);

            usuario = Config.USUARIO_SESION;
            txtPromotor.setText(usuario.getUser());

            inicializarSelectorPromotor();
            inicializarDatPickers();
            inicializarSelectorEstadoCivil();
            inicializarSelectorSucursales();
            inicializarSelectorCoordinador();
            inicializarSelectorEstadoOrigen();
            inicializarSelectorCiudadMejora();
            inicializarSelectorActividades();
            inicializarBuscadorCurp();
            inicializarMunicipio_mejoraVivienda();
            inicializarSelectoresImagenesGaleria();
            inicializarSelectoresImagenesCamara();

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

            radioProducto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.radioProductoMejoraVivienda:
                            limpiarProductoEquipandoHogar();
                            copiarDireccionMejora();
                            habilitarProductoEquipandoHogar(false);
                            habilitarProductoMejora(true);
                            break;
                        case R.id.radioProductoEquipandoHogar:
                            limpiarProductoMejora();
                            habilitarProductoEquipandoHogar(true);
                            habilitarProductoMejora(false);
                            break;
                        default:
                            habilitarProductoEquipandoHogar(true);
                            copiarDireccionMejora();
                            habilitarProductoMejora(true);
                    }
                }
            });
        }

        if(clienteSeleccionado != null) {
            inicializarFormulario(clienteSeleccionado);
        }

        return view;
    }

    private void copiarDireccionMejora() {
        txtCodigoPostal_mejoraVivienda.setText(txtCodigoPostal.getText());
        txtDomicilio_mejoraVivienda.setText(txtDomicilioMejora.getText());
        txtNumExt_mejoraVivienda.setText(txtDomicilioMejoraNumExt.getText());
        txtNumInt_mejoraVivienda.setText(txtDomicilioMejoraNumInt.getText());
        txtColonia_mejoraVivienda.setText(txtDomicilioMejoraColonia.getText());
        txtMunicipio_mejoraVivienda.setText(txtDomicilioMejoraMunicipio.getText());
    }

    private void inicializarSelectoresImagenesGaleria() {
        btnAbrirGaleriaFotoINEFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoINEFrontal;
                comprobarPermisosAbrirGaleria();
            }
        });

        btnAbrirGaleriaFotoINEReverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoINEReverso;
                comprobarPermisosAbrirGaleria();
            }
        });

        btnAbrirGaleriaFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoPerfil;
                comprobarPermisosAbrirGaleria();
            }
        });

        btnAbrirGaleriaFotoComprobanteDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoComprobanteDomicilio;
                comprobarPermisosAbrirGaleria();
            }
        });
    }

    private void inicializarSelectoresImagenesCamara() {
        btnAbrirCamaraFotoINEFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoINEFrontal;
                comprobarPermisosAbrirCamara();
            }
        });

        btnAbrirCamaraFotoINEReverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoINEReverso;
                comprobarPermisosAbrirCamara();
            }
        });

        btnAbrirCamaraFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoPerfil;
                comprobarPermisosAbrirCamara();
            }
        });

        btnAbrirCamaraFotoComprobanteDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImagenSolicitada = R.id.imgFotoComprobanteDomicilio;
                comprobarPermisosAbrirCamara();
            }
        });
    }

    private void comprobarPermisosAbrirGaleria() {
        requestPermisoSolicitado = REQUEST_PERMISSION_GALERY;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(parent, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_GALERY);
            }
        } else {
            abrirGaleria();
        }
    }

    private void comprobarPermisosAbrirCamara() {
        requestPermisoSolicitado = REQUEST_PERMISSION_CAMERA;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(parent, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
            }
        } else {
            abrirCamara();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if(intent.resolveActivity(parent.getPackageManager()) != null) {
            parent.startActivityFromFragment(this, intent, REQUEST_IMAGE);
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(parent.getPackageManager()) != null) {
            parent.startActivityFromFragment(this, intent, REQUEST_IMAGE);
        }
    }

    private void limpiarCampoFotoINEFrontal() {
        bmFotoINEFrontal = null;
        strFotoINEFrontal = null;
        imgFotoINEFrontal.setVisibility(View.GONE);
        llFotoINEFrontal.setVisibility(View.VISIBLE);
    }
    private void limpiarCampoFotoIneReverso() {
        bmFotoINEReverso = null;
        strFotoINEReverso = null;
        imgFotoINEReverso.setVisibility(View.GONE);
        llFotoINEReverso.setVisibility(View.VISIBLE);
    }
    private void limpiarCampoFotoPerfil() {
        bmFotoPerfil = null;
        strFotoPerfil = null;
        imgFotoPerfil.setVisibility(View.GONE);
        llFotoPerfil.setVisibility(View.VISIBLE);
    }
    private void limpiarCampoFotoComprobanteDomicilio() {
        bmFotoComprobanteDomicilio = null;
        strFotoComprobanteDomicilio = null;
        imgFotoComprobanteDomicilio.setVisibility(View.GONE);
        llFotoComprobanteDomicilio.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_GALERY:
                if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria();
                } else {
                    Toast.makeText(parent, "Se requiere conceder permisos para abrir galería", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PERMISSION_CAMERA:
                if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirCamara();
                } else {
                    Toast.makeText(parent, "Se requiere conceder permisos para abrir camara", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE) {
            if(resultCode == Activity.RESULT_OK && (data != null || currentPic != null)) {
                if(requestPermisoSolicitado == REQUEST_PERMISSION_GALERY) {
                    Uri uri = data.getData();
                    switch (tipoImagenSolicitada) {
                        case R.id.imgFotoINEFrontal:
                            try {
                                bmFotoINEFrontal = getBitmapFormUri(parent, uri);
                                strFotoINEFrontal = DOCUMENTO_INE_FRONTAL + "." + getExt(parent.getContentResolver().getType(uri));
                            } catch (IOException e) {
                                bmFotoINEFrontal = null;
                            }
                            imgFotoINEFrontal.setImageURI(uri);
                            imgFotoINEFrontal.setVisibility(View.VISIBLE);
                            llFotoINEFrontal.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoINEReverso:
                            try {
                                bmFotoINEReverso = getBitmapFormUri(parent, uri);
                                strFotoINEReverso = DOCUMENTO_INE_REVERSO + "." + getExt(parent.getContentResolver().getType(uri));
                            } catch (IOException e) {
                                bmFotoINEReverso = null;
                            }
                            imgFotoINEReverso.setImageURI(uri);
                            imgFotoINEReverso.setVisibility(View.VISIBLE);
                            llFotoINEReverso.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoPerfil:
                            try {
                                bmFotoPerfil = getBitmapFormUri(parent, uri);
                                strFotoPerfil =  DOCUMENTO_FOTO_PERFIL + "." + getExt(parent.getContentResolver().getType(uri));
                            } catch (IOException e) {
                                bmFotoPerfil = null;
                            }
                            imgFotoPerfil.setImageURI(uri);
                            imgFotoPerfil.setVisibility(View.VISIBLE);
                            llFotoPerfil.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoComprobanteDomicilio:
                            try {
                                bmFotoComprobanteDomicilio = getBitmapFormUri(parent, uri);
                                strFotoComprobanteDomicilio =  DOCUMENTO_COMPROBANTE_DOMICILIO + "." + getExt(parent.getContentResolver().getType(uri));
                            } catch (IOException e) {
                                bmFotoComprobanteDomicilio = null;
                            }
                            imgFotoComprobanteDomicilio.setImageURI(uri);
                            imgFotoComprobanteDomicilio.setVisibility(View.VISIBLE);
                            llFotoComprobanteDomicilio.setVisibility(View.GONE);
                            break;
                        default:
                            Toast.makeText(parent, "Tipo de imagen no solicitada", Toast.LENGTH_SHORT).show();
                    }
                } else if(requestPermisoSolicitado == REQUEST_PERMISSION_CAMERA) {
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    switch (tipoImagenSolicitada) {
                        case R.id.imgFotoINEFrontal:
                            bmFotoINEFrontal = bitmap;
                            strFotoINEFrontal =  DOCUMENTO_INE_FRONTAL + ".jpg";
                            imgFotoINEFrontal.setImageBitmap(bitmap);
                            imgFotoINEFrontal.setVisibility(View.VISIBLE);
                            llFotoINEFrontal.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoINEReverso:
                            bmFotoINEReverso = bitmap;
                            strFotoINEReverso =  DOCUMENTO_INE_REVERSO + ".jpg";
                            imgFotoINEReverso.setImageBitmap(bitmap);
                            imgFotoINEReverso.setVisibility(View.VISIBLE);
                            llFotoINEReverso.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoPerfil:
                            bmFotoPerfil = bitmap;
                            strFotoPerfil =  DOCUMENTO_FOTO_PERFIL + ".jpg";
                            imgFotoPerfil.setImageBitmap(bitmap);
                            imgFotoPerfil.setVisibility(View.VISIBLE);
                            llFotoPerfil.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoComprobanteDomicilio:
                            bmFotoComprobanteDomicilio = bitmap;
                            strFotoComprobanteDomicilio =  DOCUMENTO_COMPROBANTE_DOMICILIO + ".jpg";
                            imgFotoComprobanteDomicilio.setImageBitmap(bitmap);
                            imgFotoComprobanteDomicilio.setVisibility(View.VISIBLE);
                            llFotoComprobanteDomicilio.setVisibility(View.GONE);
                            break;
                        default:
                            Toast.makeText(parent, "Tipo de imagen no solicitada", Toast.LENGTH_SHORT).show();
                    }
                }

                tipoImagenSolicitada = 0;
            } else {
                Toast.makeText(parent, "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getExt(String type) {
        String[] strings =  type.split("/");
        return strings[1];
    }

    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //Image resolution is based on 480x800
        float hh = 800f;//The height is set as 800f here
        float ww = 480f;//Set the width here to 480f
        //Zoom ratio. Because it is a fixed scale, only one data of height or width is used for calculation
        int be = 1;//be=1 means no scaling
        if (originalWidth > originalHeight && originalWidth > ww) {//If the width is large, scale according to the fixed size of the width
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//If the height is high, scale according to the fixed size of the width
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //Proportional compression
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//Set scaling
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//Mass compression again
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Quality compression method, here 100 means no compression, store the compressed data in the BIOS
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //Cycle to determine if the compressed image is greater than 100kb, greater than continue compression
            baos.reset();//Reset the BIOS to clear it
            //First parameter: picture format, second parameter: picture quality, 100 is the highest, 0 is the worst, third parameter: save the compressed data stream
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//Here, the compression options are used to store the compressed data in the BIOS
            options -= 10;//10 less each time
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//Store the compressed data in ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//Generate image from ByteArrayInputStream data
        return bitmap;
    }

    private String bitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    private void habilitarProductoMejora(Boolean activo) {
        txtMontoSolicitadoMejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutMontoSolicitadoMejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);

        txtCodigoPostal_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtDomicilio_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtNumExt_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtNumInt_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtColonia_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtMunicipio_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutCodigoPostal_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutDomicilio_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutNumExt_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutNumInt_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutColonia_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutMunicipio_mejoraVivienda.setVisibility(activo ? View.VISIBLE : View.GONE);
    }

    private void limpiarProductoMejora() {
        txtMontoSolicitadoMejoraVivienda.setText("");

        txtCodigoPostal_mejoraVivienda.setText("");
        txtDomicilio_mejoraVivienda.setText("");
        txtNumExt_mejoraVivienda.setText("");
        txtNumInt_mejoraVivienda.setText("");
        txtColonia_mejoraVivienda.setText("");

        municipio_mejoraVivienda = null;
        seleccionarMunicipio_mejoraVivienda(null);

    }

    private void habilitarProductoEquipandoHogar(Boolean activo) {
        txtMontoSolicitadoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutMontoSolicitadoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutProductoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtProductoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
    }

    private void limpiarProductoEquipandoHogar() {
        txtMontoSolicitadoEquipandoHogar.setText("");
        txtProductoEquipandoHogar.setText("");
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
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);

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

                seleccionarFechaNacimiento(new Date((Long) selection + offsetFromUTC));
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
                txtFechaNacimientoConyuge.setText(dateFormat.format(datFechaNacimientoConyuge));
                closeKeyBoard();
            }
        });
    }

    private void seleccionarFechaNacimiento(Date date) {
        if(date == null) {
            txtFechaNacimiento.setText("");
        } else {
            try {
                datFechaNacimiento = date;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
                txtFechaNacimiento.setText(dateFormat.format(datFechaNacimiento));
            } catch (Exception e) {
                txtFechaNacimiento.setText("");
            }
        }
        layoutFechaNacimiento.setError(null);
    }

    private void seleccionarFechaNacimientoConyuge(Date date) {
        if(date == null) {
            txtFechaNacimientoConyuge.setText("");
        } else {
            try {
                datFechaNacimientoConyuge = date;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
                txtFechaNacimientoConyuge.setText(dateFormat.format(datFechaNacimientoConyuge));
            } catch (Exception e) {
                txtFechaNacimientoConyuge.setText("");
            }
        }
        layoutFechaNacimientoConyuge.setError(null);
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
                    seleccionarSucursal(sucursal.getStrSucursal());
                    dialogBuscadorSucursales.close();
                    dialogBuscarCoordinador.setIdSucursal(sucursal.getIdSucursal() + "");
                } else {
                    seleccionarSucursal(null);
                }
                closeKeyBoard();
            }
        });

        layoutSucursal.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutSucursal.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarSucursal(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorSucursales.show();
                }
            }
        });
    }

    private void seleccionarSucursal(String strSucursal) {
        if(strSucursal == null) {
            txtSucursal.setText("");
            dialogBuscadorSucursales.limpiar();
            layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutSucursal.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtSucursal.setText(strSucursal);
            layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutSucursal.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutSucursal.setError(null);
        seleccionarCoordinador(null);
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
                    seleccionarCoordinador(coordinador.getStrNombre());
                    dialogBuscarCoordinador.close();
                } else {
                    seleccionarCoordinador(null);
                }
                closeKeyBoard();
            }
        });

        layoutCoordinador.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutCoordinador.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarCoordinador(null);
                    closeKeyBoard();
                } else {
                    dialogBuscarCoordinador.show();
                }
            }
        });
    }

    private void inicializarSelectorPromotor() {
        txtPromotor.setText("");
        dialogBuscarPromotor = new DialogBuscarCoordinador(getContext(), view);
        dialogBuscarPromotor.setTitle("Seleccione promotor");

        txtPromotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscarPromotor.show();
            }
        });

        txtPromotor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscarPromotor.show();
                }
            }
        });

        dialogBuscarPromotor.setOnItemClickListener(new DialogBuscarCoordinador.OnItemClickListener() {
            @Override
            public void onItemClick(Coordinador coordinador) {
                if(coordinador != null) {
                    seleccionarPromotor(coordinador.getStrNombre());
                    dialogBuscarPromotor.close();
                } else {
                    seleccionarPromotor(null);
                }
                closeKeyBoard();
            }
        });

        layoutPromotor.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutPromotor.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarPromotor(null);
                    closeKeyBoard();
                } else {
                    dialogBuscarPromotor.show();
                }
            }
        });
    }

    private void seleccionarPromotor(String promotor) {
        if(promotor == null) {
            txtPromotor.setText("");
            dialogBuscarPromotor.limpiar();
            layoutPromotor.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutPromotor.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtPromotor.setText(promotor);
            layoutPromotor.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutPromotor.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutPromotor.setError(null);
    }

    private void seleccionarCoordinador(String strCoordinador) {
        if(strCoordinador == null) {
            txtCoordinador.setText("");
            dialogBuscarCoordinador.limpiar();
            layoutCoordinador.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutCoordinador.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtCoordinador.setText(strCoordinador);
            layoutCoordinador.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutCoordinador.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutCoordinador.setError(null);
    }

    private void inicializarMunicipio_mejoraVivienda() {
        dialogBuscadorMunicipios_mejoraVivienda = new DialogBuscadorMunicipios(getContext(), view);
        txtMunicipio_mejoraVivienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorMunicipios_mejoraVivienda.show();
            }
        });

        txtMunicipio_mejoraVivienda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscadorMunicipios_mejoraVivienda.show();
                }
            }
        });

        dialogBuscadorMunicipios_mejoraVivienda.setOnItemClickListener(new DialogBuscadorMunicipios.OnItemClickListener() {
            @Override
            public void onItemClick(Municipio municipio) {
                if(municipio != null) {
                    seleccionarMunicipio_mejoraVivienda(municipio.getStrNombreMunicipioEstado());
                    municipio_mejoraVivienda = municipio;
                    dialogBuscadorMunicipios_mejoraVivienda.close();
                } else {
                    seleccionarMunicipio_mejoraVivienda(null);
                }
                closeKeyBoard();
            }
        });

        layoutMunicipio_mejoraVivienda.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutMunicipio_mejoraVivienda.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarMunicipio_mejoraVivienda(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorMunicipios_mejoraVivienda.show();
                }
            }
        });
    }

    private void seleccionarMunicipio_mejoraVivienda(String strMunicipio) {
        if(strMunicipio == null) {
            txtMunicipio_mejoraVivienda.setText("");
            dialogBuscadorMunicipios_mejoraVivienda.limpiar();
            layoutMunicipio_mejoraVivienda.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutMunicipio_mejoraVivienda.setEndIconContentDescription(SELECCIONAR);
            municipio_mejoraVivienda = null;
        } else {
            txtMunicipio_mejoraVivienda.setText(strMunicipio);
            layoutMunicipio_mejoraVivienda.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutMunicipio_mejoraVivienda.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutMunicipio_mejoraVivienda.setError(null);
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
                    municipioMejora = municipio;
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

    private void seleccionarDomicilioMejoraMunicipio(String strMunicipio) {
        if(strMunicipio == null) {
            txtDomicilioMejoraMunicipio.setText("");
            dialogBuscadorMunicipios.limpiar();
            layoutDomicilioMejoraMunicipio.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutDomicilioMejoraMunicipio.setEndIconContentDescription(SELECCIONAR);
            municipioMejora = null;
        } else {
            txtDomicilioMejoraMunicipio.setText(strMunicipio);
            layoutDomicilioMejoraMunicipio.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutDomicilioMejoraMunicipio.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutDomicilioMejoraMunicipio.setError(null);
    }

    private void inicializarSelectorActividades() {
        dialogBuscadorActividades = new DialogBuscadorActividades(getContext(), view);
        txtActividad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialogBuscadorActividades.show();
                }
            }
        });

        txtActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorActividades.show();
            }
        });

        dialogBuscadorActividades.setOnItemClickListener(new DialogBuscadorActividades.OnItemClickListener() {
            @Override
            public void onItemClick(Actividad actividad) {
                if(actividad != null) {
                    seleccionarActividad(actividad.getStrActividad());
                    dialogBuscadorActividades.close();
                } else {
                    seleccionarActividad(null);
                }
            }
        });

        layoutActividad.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutActividad.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarActividad(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorActividades.show();
                }
            }
        });
    }

    private void seleccionarActividad(String strActividad) {
        if(strActividad == null) {
            txtActividad.setText("");
            dialogBuscadorActividades.limpiar();
            layoutActividad.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutActividad.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtActividad.setText(strActividad);
            layoutActividad.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutActividad.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutActividad.setError(null);
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
        layoutEstadoOrigen.setError(null);
    }

    private void inicializarBuscadorCurp() {
        txtCURP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txtCURP.getText().toString().trim().length() == 18) {
                    //Si la curp es válida entonces sacamos la fecha de nacimiento
                    if(validarCURP()) {
                        String curp = txtCURP.getText().toString().trim();
                        String anio = curp.substring(4, 6);
                        String mes = curp.substring(6, 8);
                        String dia = curp.substring(8, 10);

                        try {
                            DateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                            Date date = sdf.parse(anio+"-"+mes+"-"+dia);
                            seleccionarFechaNacimiento(date);
                        } catch (Exception e) {
                            seleccionarFechaNacimiento(null);
                        }
                    }
                }
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
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(cliente.getDatFechaNacimiento());
            seleccionarFechaNacimiento(date);
        } catch (Exception e) {
            seleccionarFechaNacimiento(null);
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
        //seleccionarActividad(cliente.getStrDescripcionActividad());
        seleccionarActividad(null);
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

        if(cliente.getStrMunicipio() != "") {
            municipioMejora = new Municipio();
            municipioMejora.setIdEstado(cliente.getIdEstado());
            municipioMejora.setStrEstado(cliente.getStrEstado());
            municipioMejora.setIdMunicipio(cliente.getIdMunicipio());
            municipioMejora.setStrMunicipio(cliente.getStrMunicipio());
            seleccionarDomicilioMejoraMunicipio(municipioMejora.getStrMunicipio());
        } else {
            municipioMejora = null;
            seleccionarDomicilioMejoraMunicipio(null);
        }

        txtReferenciaBancaria.setText("");
        txtInstitucionBancaria.setText("");

        txtIngresos.setText("");
        txtEgresos.setText("");

        //CONYUGE
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateConyuge = sdf.parse(cliente.getDatFechaNacimientoConyuge());
            seleccionarFechaNacimientoConyuge(dateConyuge);
        } catch (Exception e) {
            seleccionarFechaNacimientoConyuge(null);
        }
        txtOcupacionConyuge.setText(cliente.getStrOcupacionConyuge());
        txtNombreConyuge.setText(cliente.getStrNombreConyuge());
        txtLugarNacimientoConyuge.setText(cliente.getStrLugarNacimientoConyuge());

        radioPlazoProducto.check(R.id.radioPlazoProducto12Meses);
        radioQuedateEnCasa.check(R.id.radioQuedateEnCasaNoAplica);
        resetearProducto();
    }

    public void resetearProducto() {
        radioProducto.check(R.id.radioProductoMejoraVivienda);

        txtMontoSolicitadoMejoraVivienda.setVisibility(View.VISIBLE);
        txtMontoSolicitadoMejoraVivienda.setText("");
        txtMontoSolicitadoEquipandoHogar.setVisibility(View.GONE);
        txtMontoSolicitadoEquipandoHogar.setText("");


        txtCodigoPostal_mejoraVivienda.setText("");
        txtDomicilio_mejoraVivienda.setText("");
        txtNumExt_mejoraVivienda.setText("");
        txtNumInt_mejoraVivienda.setText("");
        txtColonia_mejoraVivienda.setText("");
        //CIUDAD DE LA MEJORA
        seleccionarMunicipio_mejoraVivienda(null);
        municipio_mejoraVivienda = null;

        txtCodigoPostal_mejoraVivienda.setVisibility(View.VISIBLE);
        txtDomicilio_mejoraVivienda.setVisibility(View.VISIBLE);
        txtNumExt_mejoraVivienda.setVisibility(View.VISIBLE);
        txtNumInt_mejoraVivienda.setVisibility(View.VISIBLE);
        txtColonia_mejoraVivienda.setVisibility(View.VISIBLE);
    }

    public void limpiarVista() {
        seleccionarPromotor(null);
        seleccionarSucursal(null);
        seleccionarCoordinador(null);

        limpiarCampoFotoINEFrontal();
        limpiarCampoFotoIneReverso();
        limpiarCampoFotoPerfil();
        limpiarCampoFotoComprobanteDomicilio();

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
        seleccionarActividad(null);
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
        radioPlazoProducto.check(R.id.radioPlazoProducto12Meses);
        radioQuedateEnCasa.check(R.id.radioQuedateEnCasaNoAplica);

        resetearProducto();

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
                if(response.body() == null ) {
                    Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                } else if(response.body().getResultado() == null) {
                    Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                    //No hay nada por hacer
                } else {
                    Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            limpiarVista();
                        }
                    });
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

        //CABECERAS
        solicitudDispersion.setIdPromotor(dialogBuscarPromotor.getSelectedItem() != null ? (dialogBuscarPromotor.getSelectedItem().getIdCoordinador() + "") : "");
        solicitudDispersion.setStrPromotor(txtPromotor.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrUsuario(usuario.getUser().toUpperCase());
        solicitudDispersion.setStrCordinador(txtCoordinador.getText().toString().trim().toUpperCase());
        solicitudDispersion.setIdSucursal(dialogBuscadorSucursales.getSelectedItem()  != null ? dialogBuscadorSucursales.getSelectedItem().getIdSucursal() + "" : "");
        solicitudDispersion.setIdCliente(clienteSeleccionado != null ? clienteSeleccionado.getIdCliente() : "");

        Date hoy = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", new Locale("es", "ES"));
        solicitudDispersion.setStrFechaAlta(dateFormat.format(hoy));
        //NOMBRE CLIENTE
        solicitudDispersion.setStrCURP(txtCURP.getText().toString().toUpperCase());
        solicitudDispersion.setStrNombre1(txtNombre1.getText().toString().toUpperCase());
        solicitudDispersion.setStrNombre2(txtNombre2.getText().toString().toUpperCase());
        solicitudDispersion.setStrApellidoPaterno(txtApellidoPaterno.getText().toString().toUpperCase());
        solicitudDispersion.setStrApellidoMaterno(txtApellidoMaterno.getText().toString().toUpperCase());
        //GENERO CLIENTE
        solicitudDispersion.setIdGenero(radioSexo.getCheckedRadioButtonId() == R.id.radioSexoMujer ? "2" : "1");
        solicitudDispersion.setStrGenero(radioSexo.getCheckedRadioButtonId() == R.id.radioSexoMujer ? "MUJER" : "HOMBRE");
        //FECHA NACIMIENTO
        dateFormat = new SimpleDateFormat("YYYY-MM-dd", new Locale("es", "ES"));
        solicitudDispersion.setStrFechaNacimiento(datFechaNacimiento != null ? dateFormat.format(datFechaNacimiento) : "");
        //ESTADO CIVIL
        String idEstadoCivil = "";
        for (int i = 0; i < arrayEstadoCivil.length; i++){
            if(arrayEstadoCivil[i].getStrEstadoCivil().trim().equals(txtEstadoCivil.getText().toString().trim())) {
                idEstadoCivil = arrayEstadoCivil[i].getIdEstadoCivil() + "";
            }
        }
        solicitudDispersion.setIdEstadoCivil(idEstadoCivil);
        solicitudDispersion.setStrEstadoCivil(txtEstadoCivil.getText().toString().trim().toUpperCase());
        //ACTIVIDAD CLIENTE
        solicitudDispersion.setStrOcupacion(txtOcupacion.getText().toString().trim());
        Actividad actividad = dialogBuscadorActividades != null ? dialogBuscadorActividades.getSelectedItem() : null;
        solicitudDispersion.setStrActividad(actividad.getStrActividad().trim().toUpperCase());
        solicitudDispersion.setIdActividad(actividad.getIdActividad()+"");
        solicitudDispersion.setStrCNBV(actividad.getStrCNBV());
        //CONTACTO
        solicitudDispersion.setStrCelular(txtCelular.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrTelefono(txtTelefono.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrEmail(txtEmail.getText().toString().trim());
        //INE
        solicitudDispersion.setStrClaveINE(txtClaveElector.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrNumeroINE(txtNumeroElector.getText().toString().trim().toUpperCase());

        //DOCUMENTOS
        solicitudDispersion.setStrFotoINEFrontal_B64(bitMapToBase64(bmFotoINEFrontal));
        solicitudDispersion.setStrFotoINEReverso_B64(bitMapToBase64(bmFotoINEReverso));
        solicitudDispersion.setStrFotoPerfil_B64(bitMapToBase64(bmFotoPerfil));
        solicitudDispersion.setStrFotoComprobanteDomicilio_B64(bitMapToBase64(bmFotoComprobanteDomicilio));
        solicitudDispersion.setStrFotoINEFrontal_nombre(strFotoINEFrontal);
        solicitudDispersion.setStrFotoINEReverso_nombre(strFotoINEReverso);
        solicitudDispersion.setStrFotoPerfil_nombre(strFotoPerfil);
        solicitudDispersion.setStrFotoComprobanteDomicilio_nombre(strFotoComprobanteDomicilio);

        //NACIONALIDAD
        solicitudDispersion.setStrNacionalidad(txtNacionalidad.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrEstadoNacimiento(txtEstadoOrigen.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrPais(txtPaisOrigen.getText().toString().trim().toUpperCase());
        //DOMICILIO
        solicitudDispersion.setStrDomicilioCodigoPostal(txtCodigoPostal.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrDomicilio(txtDomicilioMejora.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrDomicilioNumExt(txtDomicilioMejoraNumExt.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrDomicilioNumInt(txtDomicilioMejoraNumInt.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrDomicilioColonia(txtDomicilioMejoraColonia.getText().toString().trim().toUpperCase());
        solicitudDispersion.setIdDomicilioMunicipio(municipioMejora != null ? municipioMejora.getIdMunicipio() : "");
        solicitudDispersion.setIdDomicilioEstado(municipioMejora != null ? municipioMejora.getIdEstado() : "");
        solicitudDispersion.setStrDomicilioMunicipio(municipioMejora != null ? municipioMejora.getStrMunicipio().toUpperCase() : "");
        solicitudDispersion.setStrDomicilioEstado(municipioMejora != null ? municipioMejora.getStrEstado().toUpperCase() : "");
        //BANCOS
        solicitudDispersion.setStrReferenciaBancaria(txtReferenciaBancaria.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrBanco(txtInstitucionBancaria.getText().toString().trim().toUpperCase());
        //INGRESOS
        solicitudDispersion.setDblIngresos(Double.parseDouble(txtIngresos.getText().toString().trim()));
        solicitudDispersion.setDblEgresos(Double.parseDouble(txtEgresos.getText().toString().trim()));
        //CONYUGE
        solicitudDispersion.setStrNombreConyuge(txtNombreConyuge.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrOcupacionConyuge(txtOcupacionConyuge.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrLugarNacimientoConyuge(txtLugarNacimientoConyuge.getText().toString().trim().toUpperCase());
        solicitudDispersion.setStrFechaNacimientoConyuge(datFechaNacimientoConyuge != null ? dateFormat.format(datFechaNacimientoConyuge) : "");
        //PRODUCTO
        solicitudDispersion.setIntPlazo(radioPlazoProducto.getCheckedRadioButtonId() == R.id.radioPlazoProducto12Meses ? 12 : 24);
        solicitudDispersion.setIntQuedateCasa(radioQuedateEnCasa.getCheckedRadioButtonId() == R.id.radioQuedateEnCasaAplica ? 1 : 0);

        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoMejoraVivienda || radioProducto.getCheckedRadioButtonId() == R.id.radioProductoAmbos ) {
            solicitudDispersion.setDblMontoSolicitadoMejoraVivienda(Double.parseDouble(txtMontoSolicitadoMejoraVivienda.getText().toString().trim()));
            //DOMICILIO
            solicitudDispersion.setStrCodigoPostal_mejoraVivienda(txtCodigoPostal_mejoraVivienda.getText().toString().trim().toUpperCase());
            solicitudDispersion.setStrDomicilio_mejoraVivienda(txtDomicilio_mejoraVivienda.getText().toString().trim().toUpperCase());
            solicitudDispersion.setStrNumExt_mejoraVivienda(txtNumExt_mejoraVivienda.getText().toString().trim().toUpperCase());
            solicitudDispersion.setStrNumInt_mejoraVivienda(txtNumInt_mejoraVivienda.getText().toString().trim().toUpperCase());
            solicitudDispersion.setStrColonia_mejoraVivienda(txtColonia_mejoraVivienda.getText().toString().trim().toUpperCase());
            solicitudDispersion.setIdMunicipio_mejoraVivienda(municipio_mejoraVivienda != null ? municipio_mejoraVivienda.getIdMunicipio() : "");
            solicitudDispersion.setIdEstado_mejoraVivienda(municipio_mejoraVivienda != null ? municipio_mejoraVivienda.getIdEstado() : "");
            solicitudDispersion.setStrMunicipio_mejoraVivienda(municipio_mejoraVivienda != null ? municipio_mejoraVivienda.getStrMunicipio().toUpperCase() : "");
            solicitudDispersion.setStrEstado_mejoraVivienda(municipio_mejoraVivienda != null ? municipio_mejoraVivienda.getStrEstado().toUpperCase() : "");
        } else {
            solicitudDispersion.setDblMontoSolicitadoMejoraVivienda(0);
            //DOMICILIO
            solicitudDispersion.setStrCodigoPostal_mejoraVivienda("");
            solicitudDispersion.setStrDomicilio_mejoraVivienda("");
            solicitudDispersion.setStrNumExt_mejoraVivienda("");
            solicitudDispersion.setStrNumInt_mejoraVivienda("");
            solicitudDispersion.setStrColonia_mejoraVivienda("");
            solicitudDispersion.setIdMunicipio_mejoraVivienda("");
            solicitudDispersion.setIdEstado_mejoraVivienda("");
            solicitudDispersion.setStrMunicipio_mejoraVivienda("");
            solicitudDispersion.setStrEstado_mejoraVivienda("");
        }

        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoEquipandoHogar || radioProducto.getCheckedRadioButtonId() == R.id.radioProductoAmbos ) {
            solicitudDispersion.setDblMontoSolicitadoEquipandoHogar(Double.parseDouble(txtMontoSolicitadoEquipandoHogar.getText().toString().trim()));
            solicitudDispersion.setStrProducto(txtProductoEquipandoHogar.getText().toString().trim().toUpperCase());
        } else {
            solicitudDispersion.setDblMontoSolicitadoEquipandoHogar(0);
            solicitudDispersion.setStrProducto("");
        }

        return solicitudDispersion;
    }

    private boolean validarSolicitudDispersion() {

        if(txtPromotor.getText().toString().trim().length() == 0) {
            layoutPromotor.setError("Seleccione un promotor");
            layoutPromotor.requestFocus();
            return false;
        } else {
            layoutPromotor.setError(null);
        }

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
            layoutActividad.setError("Seleccione actividad");
            txtActividad.requestFocus();
            return false;
        } else {
            layoutActividad.setError(null);
        }

        if(txtCelular.getText().toString().trim().length() == 0) {
            layoutCelular.setError("Ingrese número celular");
            txtCelular.requestFocus();
            return false;
        } else if(txtCelular.getText().toString().trim().length() < 10) {
            layoutCelular.setError("Ingrese número celular a 10 dígitos");
            txtCelular.requestFocus();
            return false;
        } else {
            layoutCelular.setError(null);
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-zA-Z]+";
        if(txtEmail.getText().toString().trim().length() == 0) {
            layoutEmail.setError("Ingrese correo electrónico");
            txtEmail.requestFocus();
            return false;
        } else if(!txtEmail.getText().toString().trim().matches(emailPattern)) {
            layoutEmail.setError("Ingrese correo electrónico válido");
            txtEmail.requestFocus();
            return false;
        } else {
            layoutEmail.setError(null);
        }

        if(txtClaveElector.getText().toString().trim().length() < 18) {
            layoutClaveElector.setError("Ingrese clave de elector a 18 dígitos");
            txtClaveElector.requestFocus();
            return false;
        } else {
            layoutClaveElector.setError(null);
        }

        if(txtNumeroElector.getText().toString().trim().length() < 10) {
            layoutNumeroElector.setError("Ingrese número de elector válido");
            txtNumeroElector.requestFocus();
            return false;
        } else {
            layoutNumeroElector.setError(null);
        }

        if(bmFotoINEFrontal == null) {
            Toast.makeText(parent, "Seleccione foto frontal de INE", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(bmFotoINEReverso == null) {
            Toast.makeText(parent, "Seleccione foto del reverso de INE", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(bmFotoPerfil == null) {
            Toast.makeText(parent, "Seleccione foto perfil del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(bmFotoComprobanteDomicilio == null) {
            Toast.makeText(parent, "Seleccione foto de comprobante de domicilio", Toast.LENGTH_SHORT).show();
            return false;
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
            layoutCodigoPostal.setError("Ingrese código postal");
            layoutCodigoPostal.requestFocus();
            return false;
        } else {
            layoutCodigoPostal.setError(null);
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

        if(radioProducto.getCheckedRadioButtonId() == -1) {
            Toast.makeText(parent, "Seleccione por lo menos un producto por solicitar", Toast.LENGTH_SHORT).show();
            radioProducto.requestFocus();
            return false;
        }

        //Validaciones de producto mejora
        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoMejoraVivienda || radioProducto.getCheckedRadioButtonId() == R.id.radioProductoAmbos ) {
            if(txtMontoSolicitadoMejoraVivienda.getText().toString().trim().length() == 0) {
                layoutMontoSolicitadoMejoraVivienda.setError("Ingrese una cantidad");
                txtMontoSolicitadoMejoraVivienda.requestFocus();
                return false;
            } else {
                layoutMontoSolicitadoMejoraVivienda.setError(null);
            }

            if(txtCodigoPostal_mejoraVivienda.getText().toString().trim().length() == 0) {
                layoutCodigoPostal_mejoraVivienda.setError("Ingrese código postal");
                txtCodigoPostal_mejoraVivienda.requestFocus();
                return false;
            } else {
                layoutCodigoPostal_mejoraVivienda.setError(null);
            }

            if(txtDomicilio_mejoraVivienda.getText().toString().trim().length() == 0) {
                layoutDomicilio_mejoraVivienda.setError("Ingrese dirección de la mejora");
                txtDomicilio_mejoraVivienda.requestFocus();
                return false;
            } else {
                layoutDomicilio_mejoraVivienda.setError(null);
            }

            if(txtNumExt_mejoraVivienda.getText().toString().trim().length() == 0) {
                layoutNumExt_mejoraVivienda.setError("Ingrese número exterior");
                txtNumExt_mejoraVivienda.requestFocus();
                return false;
            } else {
                layoutNumExt_mejoraVivienda.setError(null);
            }

            if(txtColonia_mejoraVivienda.getText().toString().trim().length() == 0) {
                layoutColonia_mejoraVivienda.setError("Ingrese colonia");
                txtColonia_mejoraVivienda.requestFocus();
                return false;
            } else {
                layoutColonia_mejoraVivienda.setError(null);
            }

            if(txtMunicipio_mejoraVivienda.getText().toString().trim().length() == 0) {
                layoutMunicipio_mejoraVivienda.setError("Selccione municipio");
                txtMunicipio_mejoraVivienda.requestFocus();
                return false;
            } else {
                layoutMunicipio_mejoraVivienda.setError(null);
            }
        }
        //FIN Validaciones de producto mejora

        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoEquipandoHogar || radioProducto.getCheckedRadioButtonId() == R.id.radioProductoAmbos) {

            if(txtMontoSolicitadoEquipandoHogar.getText().toString().trim().length() == 0) {
                layoutMontoSolicitadoEquipandoHogar.setError("Ingrese una cantidad");
                txtMontoSolicitadoEquipandoHogar.requestFocus();
                return false;
            } else {
                layoutMontoSolicitadoEquipandoHogar.setError(null);
            }

            if(txtProductoEquipandoHogar.getText().toString().trim().length() == 0) {
                layoutProductoEquipandoHogar.setError("Ingrese nombre de producto");
                txtProductoEquipandoHogar.requestFocus();
                return false;
            } else {
                layoutProductoEquipandoHogar.setError(null);
            }
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