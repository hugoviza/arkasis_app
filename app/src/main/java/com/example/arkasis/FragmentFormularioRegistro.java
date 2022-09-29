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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.arkasis.DB.tablas.TableSolicitudesDispersion;
import com.example.arkasis.componentes.DialogBuscadorActividades;
import com.example.arkasis.componentes.DialogBuscadorEstados;
import com.example.arkasis.componentes.DialogBuscadorMunicipios;
import com.example.arkasis.componentes.DialogBuscadorSucursales;
import com.example.arkasis.componentes.DialogBuscadorTipoVencimiento;
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
import com.example.arkasis.models.TipoVencimiento;
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
    LinearLayout llFotoINEFrontal, llFotoINEReverso, llFotoPerfil, llFotoComprobanteDomicilio, llSeparadorEquipandoHogar;
    RelativeLayout rlFotoINEFrontal, rlFotoINEReverso, rlFotoPerfil, rlFotoComprobanteDomicilio;
    ImageButton imgFotoINEFrontal, imgFotoINEReverso, imgFotoPerfil, imgFotoComprobanteDomicilio;
    ImageButton btnCancelarFotoINEFrontal, btnCancelarFotoINEReverso, btnCancelarFotoPerfil, btnCancelarFotoComprobanteDomicilio;
    MaterialDatePicker dpFechaNacimiento, dpFechaNacimientoConyuge;
    TextInputEditText txtFechaNacimiento, txtSucursal, txtPromotor, txtCoordinador, txtCURP, txtNombre1, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtNacionalidad, txtOcupacion, txtActividad,
            txtCelular, txtTelefono, txtEmail, txtClaveElector, txtNumeroElector, txtEstadoOrigen, txtPaisOrigen,
            txtNombreConyuge, txtLugarNacimientoConyuge, txtFechaNacimientoConyuge, txtOcupacionConyuge,
            txtCodigoPostal, txtDomicilioMejora, txtDomicilioMejoraNumExt, txtDomicilioMejoraNumInt,
            txtDomicilioMejoraColonia, txtDomicilioMejoraMunicipio, txtReferenciaBancaria, txtInstitucionBancaria,
            txtIngresos, txtEgresos, txtMontoSolicitadoEquipandoHogar, txtProductoEquipandoHogar,
            txtPlazo, txtTipoVencimiento, txtNumPagos
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
            layoutIngresos, layoutEgresos, layoutMontoSolicitadoEquipandoHogar, layoutProductoEquipandoHogar,
            layoutPlazo, layoutTipoVencimiento, layoutNumPagos
    ;
    RadioGroup radioSexo, radioProducto, radioContrato;
    AutoCompleteTextView txtEstadoCivil;
    DialogBuscadorSucursales dialogBuscadorSucursales;
    DialogBuscadorTipoVencimiento dialogBuscadorTipoVencimiento;
    DialogBuscarCoordinador dialogBuscarCoordinador, dialogBuscarPromotor;
    DialogBuscadorMunicipios dialogBuscadorMunicipios;
    DialogBuscadorEstados dialogBuscadorEstados;
    DialogBuscadorActividades dialogBuscadorActividades;

    //Data
    Date datFechaNacimiento, datFechaNacimientoConyuge;
    ArrayAdapter<String> adaptadorEstadoCivil;
    Usuario usuario;
    Estado estadoNacimiento;
    Municipio municipioMejora;
    Bitmap bmFotoINEFrontal, bmFotoINEReverso, bmFotoPerfil, bmFotoComprobanteDomicilio;
    String strFotoINEFrontal, strFotoINEReverso, strFotoPerfil, strFotoComprobanteDomicilio;

    String fotoPath;

    //Helper
    int tipoImagenSolicitada = 0;
    int requestPermisoSolicitado = 0;
    int pesoMaximo = 2048;


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
            layoutMontoSolicitadoEquipandoHogar = view.findViewById(R.id.layoutMontoSolicitadoEquipandoHogar);
            txtMontoSolicitadoEquipandoHogar = view.findViewById(R.id.txtMontoSolicitadoEquipandoHogar);
            layoutPlazo = view.findViewById(R.id.layoutPlazo);
            txtPlazo = view.findViewById(R.id.txtPlazo);
            radioProducto = view.findViewById(R.id.radioProducto);
            txtTipoVencimiento = view.findViewById(R.id.txtTipoVencimiento);
            layoutTipoVencimiento = view.findViewById(R.id.layoutTipoVencimiento);
            txtNumPagos = view.findViewById(R.id.txtNumPagos);
            layoutNumPagos = view.findViewById(R.id.layoutNumPagos);
            radioContrato = view.findViewById(R.id.radioContrato);

            txtProductoEquipandoHogar = view.findViewById(R.id.txtProductoEquipandoHogar);
            layoutProductoEquipandoHogar = view.findViewById(R.id.layoutProductoEquipandoHogar);

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

            rlFotoINEFrontal = view.findViewById(R.id.rlFotoINEFrontal);
            rlFotoINEReverso = view.findViewById(R.id.rlFotoINEReverso);
            rlFotoPerfil = view.findViewById(R.id.rlFotoPerfil);
            rlFotoComprobanteDomicilio = view.findViewById(R.id.rlFotoComprobanteDomicilio);

            btnCancelarFotoINEFrontal = view.findViewById(R.id.btnCancelarFotoINEFrontal);
            btnCancelarFotoINEReverso = view.findViewById(R.id.btnCancelarFotoINEReverso);
            btnCancelarFotoPerfil = view.findViewById(R.id.btnCancelarFotoPerfil);
            btnCancelarFotoComprobanteDomicilio = view.findViewById(R.id.btnCancelarFotoComprobanteDomicilio);

            llSeparadorEquipandoHogar = view.findViewById(R.id.llSeparadorEquipandoHogar);

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
            inicializarSelectoresImagenesGaleria();
            inicializarSelectoresImagenesCamara();
            inicializarBotonesCancelarSeleccionImagen();
            inicializarSelectorTipoVencimiento();

            BottomBarActivity.cerrarLoading();

            habilitarProductoEquipandoHogar(true);

            txtNumPagos.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    calcularPlazos();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

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
                        case R.id.radioProductoEquipandoHogar:
                            habilitarProductoEquipandoHogar(true);
                            break;
                        default:
                            habilitarProductoEquipandoHogar(true);
                    }
                }
            });
        }

        if(clienteSeleccionado != null) {
            inicializarFormulario(clienteSeleccionado);
        }

        return view;
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

    private void inicializarBotonesCancelarSeleccionImagen() {
        btnCancelarFotoINEFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampoFotoINEFrontal();
            }
        });

        btnCancelarFotoINEReverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampoFotoIneReverso();
            }
        });

        btnCancelarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampoFotoPerfil();
            }
        });

        btnCancelarFotoComprobanteDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampoFotoComprobanteDomicilio();
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

        File imagenFile = null;

        try {
            imagenFile = crearFileImagen();
        } catch (Exception e) {
            Toast.makeText(parent, "Error al abrir camara", Toast.LENGTH_SHORT).show();
            return;
        }

        if(imagenFile != null) {
            Uri uriImageFile = FileProvider.getUriForFile(parent, "com.example.arkasis.fileprovider", imagenFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImageFile);
        }

        if(intent.resolveActivity(parent.getPackageManager()) != null) {
            parent.startActivityFromFragment(this, intent, REQUEST_IMAGE);
        }
    }

    private File crearFileImagen() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", new Locale("es", "ES"));
        String fileName = "foto_arkasis_" + dateFormat.format(date);

        File directorio = parent.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File imagen = File.createTempFile(fileName, ".jpg", directorio);
        fotoPath = imagen.getAbsolutePath();
        return imagen;
    }

    private void limpiarCampoFotoINEFrontal() {
        bmFotoINEFrontal = null;
        strFotoINEFrontal = null;
        rlFotoINEFrontal.setVisibility(View.GONE);
        llFotoINEFrontal.setVisibility(View.VISIBLE);
    }

    private void limpiarCampoFotoIneReverso() {
        bmFotoINEReverso = null;
        strFotoINEReverso = null;
        rlFotoINEReverso.setVisibility(View.GONE);
        llFotoINEReverso.setVisibility(View.VISIBLE);
    }

    private void limpiarCampoFotoPerfil() {
        bmFotoPerfil = null;
        strFotoPerfil = null;
        rlFotoPerfil.setVisibility(View.GONE);
        llFotoPerfil.setVisibility(View.VISIBLE);
    }

    private void limpiarCampoFotoComprobanteDomicilio() {
        bmFotoComprobanteDomicilio = null;
        strFotoComprobanteDomicilio = null;
        rlFotoComprobanteDomicilio.setVisibility(View.GONE);
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
            if(resultCode == Activity.RESULT_OK && (data != null || fotoPath != null)) {
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
                            rlFotoINEFrontal.setVisibility(View.VISIBLE);
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
                            rlFotoINEReverso.setVisibility(View.VISIBLE);
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
                            rlFotoPerfil.setVisibility(View.VISIBLE);
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
                            rlFotoComprobanteDomicilio.setVisibility(View.VISIBLE);
                            llFotoComprobanteDomicilio.setVisibility(View.GONE);
                            break;
                        default:
                            Toast.makeText(parent, "Tipo de imagen no solicitada", Toast.LENGTH_SHORT).show();
                    }
                } else if(requestPermisoSolicitado == REQUEST_PERMISSION_CAMERA && fotoPath != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(fotoPath);
                    switch (tipoImagenSolicitada) {
                        case R.id.imgFotoINEFrontal:
                            bmFotoINEFrontal = bitmap;
                            strFotoINEFrontal =  DOCUMENTO_INE_FRONTAL + ".jpg";
                            imgFotoINEFrontal.setImageBitmap(bitmap);
                            rlFotoINEFrontal.setVisibility(View.VISIBLE);
                            llFotoINEFrontal.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoINEReverso:
                            bmFotoINEReverso = bitmap;
                            strFotoINEReverso =  DOCUMENTO_INE_REVERSO + ".jpg";
                            imgFotoINEReverso.setImageBitmap(bitmap);
                            rlFotoINEReverso.setVisibility(View.VISIBLE);
                            llFotoINEReverso.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoPerfil:
                            bmFotoPerfil = bitmap;
                            strFotoPerfil =  DOCUMENTO_FOTO_PERFIL + ".jpg";
                            imgFotoPerfil.setImageBitmap(bitmap);
                            rlFotoPerfil.setVisibility(View.VISIBLE);
                            llFotoPerfil.setVisibility(View.GONE);
                            break;
                        case R.id.imgFotoComprobanteDomicilio:
                            bmFotoComprobanteDomicilio = bitmap;
                            strFotoComprobanteDomicilio =  DOCUMENTO_COMPROBANTE_DOMICILIO + ".jpg";
                            imgFotoComprobanteDomicilio.setImageBitmap(bitmap);
                            rlFotoComprobanteDomicilio.setVisibility(View.VISIBLE);
                            llFotoComprobanteDomicilio.setVisibility(View.GONE);
                            break;
                        default:
                            Toast.makeText(parent, "Tipo de imagen no solicitada", Toast.LENGTH_SHORT).show();
                    }
                }
                fotoPath = null;
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
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }

    public Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Quality compression method, here 100 means no compression, store the compressed data in the BIOS
        while ((baos.toByteArray().length / 1024) > pesoMaximo) {  //Cycle to determine if the compressed image is greater than 100kb, greater than continue compression
            //Hacemos una regla de 3 para comprimir
            int quality = pesoMaximo * 100 / (baos.toByteArray().length / 1024);
            quality = quality > 100 ? 100 : quality;
            baos.reset();//Reset the BIOS to clear it
            //First parameter: picture format, second parameter: picture quality, 100 is the highest, 0 is the worst, third parameter: save the compressed data stream
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//Here, the compression options are used to store the compressed data in the BIOS
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//Store the compressed data in ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//Generate image from ByteArrayInputStream data
        return bitmap;
    }

    private String bitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        int quality = pesoMaximo * 100 / (byteArrayOutputStream.toByteArray().length / 1024);

        quality = quality > 100 ? 100 : quality;

        byteArrayOutputStream.reset();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    private void habilitarProductoEquipandoHogar(Boolean activo) {
        llSeparadorEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtMontoSolicitadoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutMontoSolicitadoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        layoutProductoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
        txtProductoEquipandoHogar.setVisibility(activo ? View.VISIBLE : View.GONE);
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
        ArrayAdapter adapter = new ArrayAdapter(parent, R.layout.item_dropdown, lista);
        txtEstadoCivil.setAdapter(adapter);
    }

    private void inicializarSelectorSucursales() {
        txtSucursal.setText("");
        dialogBuscadorSucursales = new DialogBuscadorSucursales(parent, view);

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
                    dialogBuscadorTipoVencimiento.setIdSucursal(Integer.toString(sucursal.getIdSucursal()));
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
            dialogBuscadorTipoVencimiento.setIdSucursal("");
        } else {
            txtSucursal.setText(strSucursal);
            layoutSucursal.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutSucursal.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutSucursal.setError(null);
        seleccionarCoordinador(null);
    }

    private void inicializarSelectorTipoVencimiento() {
        txtTipoVencimiento.setText("");
        dialogBuscadorTipoVencimiento = new DialogBuscadorTipoVencimiento(parent, view);

        txtSucursal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuscadorTipoVencimiento.show();
            }
        });

        txtTipoVencimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogBuscadorTipoVencimiento.show();
                }
            }
        });

        dialogBuscadorTipoVencimiento.setOnItemClickListener(new DialogBuscadorTipoVencimiento.OnItemClickListener() {
            @Override
            public void onItemClick(TipoVencimiento tipoVencimiento) {
                if(tipoVencimiento != null) {
                    seleccionarTipoVencimiento(tipoVencimiento.getStrTipoVencimiento());
                    dialogBuscadorTipoVencimiento.close();
                    dialogBuscadorTipoVencimiento.setIdTipoVencimiento(tipoVencimiento.getIdTipoVencimiento() + "");
                } else {
                    seleccionarTipoVencimiento(null);
                }
                closeKeyBoard();
                calcularPlazos();

            }
        });

        layoutTipoVencimiento.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutTipoVencimiento.getEndIconContentDescription() == LIMPIAR_CAMPO) {
                    seleccionarTipoVencimiento(null);
                    closeKeyBoard();
                } else {
                    dialogBuscadorTipoVencimiento.show();
                }
                calcularPlazos();
            }
        });
    }

    private void seleccionarTipoVencimiento(String strTipoVencimiento) {
        if(strTipoVencimiento == null) {
            txtTipoVencimiento.setText("");
            dialogBuscadorTipoVencimiento.limpiar();
            layoutTipoVencimiento.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            layoutTipoVencimiento.setEndIconContentDescription(SELECCIONAR);
        } else {
            txtTipoVencimiento.setText(strTipoVencimiento);
            layoutTipoVencimiento.setEndIconDrawable(R.drawable.ic_baseline_cancel_24);
            layoutTipoVencimiento.setEndIconContentDescription(LIMPIAR_CAMPO);
        }
        layoutTipoVencimiento.setError(null);
    }

    private void inicializarSelectorCoordinador() {
        txtCoordinador.setText("");
        dialogBuscarCoordinador = new DialogBuscarCoordinador(parent, view);

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
        dialogBuscarPromotor = new DialogBuscarCoordinador(parent, view);
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

    private void inicializarSelectorCiudadMejora() {
        dialogBuscadorMunicipios = new DialogBuscadorMunicipios(parent, view);
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
        dialogBuscadorActividades = new DialogBuscadorActividades(parent, view);
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
        dialogBuscadorEstados = new DialogBuscadorEstados(parent, view);
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
            Toast.makeText(parent, R.string.sin_conexion, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(parent, "Cliente no encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if(response.body().getSuccess()) {
                            ArrayList<LinkedTreeMap<Object, Object>> linkedTreeMaps = (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado();
                            if(linkedTreeMaps.size() == 1) {
                                inicializarFormulario(new Cliente(linkedTreeMaps.get(0)));
                            } else {
                                Toast.makeText(parent, "Se han encontrado "+linkedTreeMaps.size()+" resultados", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception ex) {
                        Toast.makeText(parent, "Error al cargar cliente", Toast.LENGTH_SHORT).show();
                    }
                }
                BottomBarActivity.cerrarLoading();
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                BottomBarActivity.cerrarLoading();
                Toast.makeText(parent, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
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

        txtPlazo.setText("");
        resetearProducto();
    }

    public void resetearProducto() {
        radioProducto.check(R.id.radioProductoEquipandoHogar);
        txtMontoSolicitadoEquipandoHogar.setVisibility(View.VISIBLE);
        txtMontoSolicitadoEquipandoHogar.setText("");
        txtProductoEquipandoHogar.setText("");
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

        // Plazo
        txtPlazo.setText("");
        txtTipoVencimiento.setText("");
        txtNumPagos.setText("");
        dialogBuscadorTipoVencimiento.setIdSucursal("");
        layoutPlazo.setError(null);
        layoutNumPagos.setError(null);
        layoutTipoVencimiento.setError(null);
        layoutTipoVencimiento.setEndIconDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
        layoutTipoVencimiento.setEndIconContentDescription("Buscar");

        resetearProducto();

        txtCURP.requestFocus();
    }

    private void guadarSolicitudDispersion() {
        if(validarSolicitudDispersion()) {
            //mostramos el mensaje de guardando
            BottomBarActivity.abrirLoading("Guardando...");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SolicitudDispersion solicitudDispersion = armarObjetoSolicitud();
                    if(getEstatusConexionInternet()) {
                        guadarSolicitudDispersionSERVER(solicitudDispersion);
                    } else {
                        guadarSolicitudDispersionLOCAL(solicitudDispersion);
                    }
                }
            }, 1000);
        }
    }

    private void guadarSolicitudDispersionLOCAL(SolicitudDispersion solicitudDispersion) {
        TableSolicitudesDispersion table = new TableSolicitudesDispersion(getContext());
        table.insertar(solicitudDispersion);

        Toast.makeText(parent, "Registro guardado localmente", Toast.LENGTH_SHORT).show();
        limpiarVista();
        BottomBarActivity.cerrarLoading();
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
                    Toast.makeText(parent, "Error al guardar", Toast.LENGTH_SHORT).show();
                    guadarSolicitudDispersionLOCAL(solicitudDispersion);
                } else if(response.body().getResultado() == null) {
                    Toast.makeText(parent, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                    //No hay nada por hacer
                } else {
                    Toast.makeText(parent, "Guardado correctamente", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(parent, getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                guadarSolicitudDispersionLOCAL(solicitudDispersion);
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
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));
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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
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

        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoEquipandoHogar) {
            solicitudDispersion.setDblMontoSolicitadoEquipandoHogar(Double.parseDouble(txtMontoSolicitadoEquipandoHogar.getText().toString().trim()));
            solicitudDispersion.setStrProducto(txtProductoEquipandoHogar.getText().toString().trim().toUpperCase());
        } else {
            solicitudDispersion.setDblMontoSolicitadoEquipandoHogar(0);
            solicitudDispersion.setStrProducto("");
        }

        if(dialogBuscadorTipoVencimiento.getSelectedItem() != null) {
            TipoVencimiento tipoVencimiento = dialogBuscadorTipoVencimiento.getSelectedItem();
            solicitudDispersion.setIdTipoVencimiento(tipoVencimiento.getIdTipoVencimiento());
            solicitudDispersion.setStrTipoVencimiento(tipoVencimiento.getStrTipoVencimiento());
        }
        solicitudDispersion.setIntNumPagos(Integer.parseInt(txtNumPagos.getText().toString()));
        solicitudDispersion.setDblPlazo(Double.parseDouble(txtPlazo.getText().toString()));

        switch (radioContrato.getCheckedRadioButtonId()) {
            case R.id.radioContratoUnicamenteAcreditado:
                solicitudDispersion.setIdTipoContratoIndividual("1");
                break;
            case R.id.radioContratoAcreditadoYAval:
                solicitudDispersion.setIdTipoContratoIndividual("2");
                break;
            case R.id.radioContratoAcreditadoYDosAvales:
                solicitudDispersion.setIdTipoContratoIndividual("3");
                break;
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
            // layoutReferenciaBancaria.setError("Ingrese referencia bancaria");
            // txtReferenciaBancaria.requestFocus();
            // return false;
        } else {
            layoutReferenciaBancaria.setError(null);
        }

        if(txtInstitucionBancaria.getText().toString().trim().length() == 0) {
            // layoutInstitucionBancaria.setError("Ingrese nombre del banco");
            // txtInstitucionBancaria.requestFocus();
            // return false;
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

        if(radioProducto.getCheckedRadioButtonId() == R.id.radioProductoEquipandoHogar) {

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

        // validar que hayan seleccionado el tipo de vencimiento
        if(dialogBuscadorTipoVencimiento.getSelectedItem() == null) {
            layoutTipoVencimiento.setError("Seleccione tipo de vencimiento");
            txtTipoVencimiento.requestFocus();
            return false;
        } else {
            layoutTipoVencimiento.setError(null);
        }
        // validar que hayan ingresado num pagos
        if(txtNumPagos.getText().toString().trim().length() == 0) {
            layoutNumPagos.setError("Ingrese número de pagos");
            txtNumPagos.requestFocus();
            return false;
        } else if(Integer.parseInt(txtNumPagos.getText().toString()) < 1) {
            layoutNumPagos.setError("Número de pagos debe ser mayor a cero");
            txtNumPagos.requestFocus();
            return false;
        } else {
            layoutNumPagos.setError(null);
        }

        if(txtPlazo.getText().toString().trim().length() == 0) {
            layoutPlazo.setError("Ingrese plazo");
            txtPlazo.requestFocus();
            return false;
        } else if(Double.parseDouble(txtPlazo.getText().toString()) < 1) {
            layoutPlazo.setError("Ingrese plazo mayor a cero");
            txtPlazo.requestFocus();
            return false;
        } else {
            layoutPlazo.setError(null);
        }

        // recalcular plazos
        calcularPlazos();

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

    private void calcularPlazos() {
        // Para calcular plazos requerimos
        // 1. Que hayan seleccionado Tipo de vencimiento
        // 2. Que hayan seleccionado Numero de pagos

        if(txtNumPagos.getText().toString().trim().length() == 0 || dialogBuscadorTipoVencimiento.getSelectedItem() == null) {
            txtPlazo.setText("0");
            return;
        }

        int numDias = dialogBuscadorTipoVencimiento.getSelectedItem().getIntNumDias();
        double numPagos = Double.parseDouble(txtNumPagos.getText().toString());
        double plazo = Math.round((numPagos * numDias / 30.00) * 100.00) / 100.00;
        txtPlazo.setText(Double.toString(plazo));
    }
}