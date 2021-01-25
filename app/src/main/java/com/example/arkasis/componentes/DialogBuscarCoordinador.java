package com.example.arkasis.componentes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.R;
import com.example.arkasis.adapters.AdaptadorListaCoordinadores;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.Sucursal;

public class DialogBuscarCoordinador {
    private Context context;
    private View parent;
    private Dialog dialog;

    //Dialog componentes
    EditText txtBuscar;
    RecyclerView recycler_view;
    Button btnCerrar;

    //Data
    AdaptadorListaCoordinadores adaptadorListaCoordinadores;
    Coordinador coordinadorSeleccionado;
    String idSucursal = "";

    //Eventos
    DialogBuscarCoordinador.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Coordinador coordinador);
    }

    public DialogBuscarCoordinador(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    public void show() {
        if(dialog == null) {
            init();
            dialog.show();
        } else{
            dialog.show();
            txtBuscar.setText("");
            adaptadorListaCoordinadores.getFilter().filter("");
        }
    }

    public void close(){
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    public void limpiar() {
        if(txtBuscar != null) {
            txtBuscar.setText("");
        }
        coordinadorSeleccionado = null;
    }

    private void init() {
        if(dialog != null) {
            return;
        }

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.list_buscar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(parent.getMeasuredWidth() - 32, LinearLayout.LayoutParams.WRAP_CONTENT);

        txtBuscar = dialog.findViewById(R.id.txtBuscar);
        btnCerrar = dialog.findViewById(R.id.btnCerrar);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);

        tvTitle.setText("Seleccione coordinador");

        adaptadorListaCoordinadores = new AdaptadorListaCoordinadores(dialog.getContext(), idSucursal);
        recycler_view = dialog.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recycler_view.setAdapter(adaptadorListaCoordinadores);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptadorListaCoordinadores.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adaptadorListaCoordinadores.setOnItemClickListener(new AdaptadorListaCoordinadores.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(adaptadorListaCoordinadores.getItem(position));
                    coordinadorSeleccionado = adaptadorListaCoordinadores.getItem(position);
                } else {
                    onItemClickListener.onItemClick(null);
                    coordinadorSeleccionado = null;

                }
            }
        });
    }

    public Coordinador getSelectedItem() {
        return coordinadorSeleccionado;
    }

    public void setOnItemClickListener(DialogBuscarCoordinador.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal == null ? "" : idSucursal;
        if(adaptadorListaCoordinadores != null) {
            this.adaptadorListaCoordinadores.setIdSucursal(idSucursal == null ? "" : idSucursal);
        }
    }
}
