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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentFormularioRegistro() {
        // Required empty public constructor
    }

    //Componentes
    View view;
    MaterialDatePicker dpFechaNacimiento, dpFechaNacimientoConyuge;
    TextInputEditText txtFechaNacimiento, txtEstadoCivil;
    Dialog dialogEstadoCivil ;

    //Data
    Date datFechaNacimiento;
    ArrayAdapter<String> adaptadorEstadoCivil;

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

        inicializarDatPickers();
        inicializarDialogEstadoCivil();
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
                datFechaNacimiento = new Date((Long) selection);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                txtFechaNacimiento.setText(dateFormat.format(datFechaNacimiento));
            }
        });

    }

    public void inicializarDialogEstadoCivil() {

        String[] names = { "SOLTERA(O)", "CASADA(O) (BIENES MANCUMUNADOS)","CASADA(O) (BIENES SEPARADOS)","UNIÓN LIBRE","VIUDA(O)","DIVORCIADA(O)" };

        adaptadorEstadoCivil = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, names);

        dialogEstadoCivil = new Dialog(getActivity());
        dialogEstadoCivil.setContentView(R.layout.simple_list_item);
        dialogEstadoCivil.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEstadoCivil.getWindow().setLayout(view.getMeasuredWidth() - 32, LinearLayout.LayoutParams.WRAP_CONTENT);


        ListView simple_list = dialogEstadoCivil.findViewById(R.id.simple_list);
        simple_list.setAdapter(adaptadorEstadoCivil);

        txtEstadoCivil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEstadoCivil.show();
            }
        });

        txtEstadoCivil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dialogEstadoCivil.show();
                }
            }
        });

        simple_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < adaptadorEstadoCivil.getCount()) {
                    txtEstadoCivil.setText(adaptadorEstadoCivil.getItem(position));
                } else {
                    txtEstadoCivil.setText("");
                    dialogEstadoCivil.dismiss();
                }
            }
        });

    }
}