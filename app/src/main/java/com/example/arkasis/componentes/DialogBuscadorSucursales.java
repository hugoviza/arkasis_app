package com.example.arkasis.componentes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.R;
import com.example.arkasis.adapters.AdaptadorListaSucursal;
import com.example.arkasis.models.Sucursal;

public class DialogBuscadorSucursales {
    private Context context;
    private View parent;
    private Dialog dialog;

    //Dialog componentes
    TextView tvTitle;
    EditText txtBuscar;
    RecyclerView recycler_view;
    Button btnCerrar;

    //Data
    AdaptadorListaSucursal adaptadorListaSucursal;
    Sucursal sucursalSeleccionada;

    //Eventos
    DialogBuscadorSucursales.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Sucursal sucursal);
    }

    public DialogBuscadorSucursales(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    public void show() {
        if(dialog == null) {
            init();
            dialog.show();
        } else{
            dialog.show();
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
        sucursalSeleccionada = null;
    }

    public boolean isShowing() {
        return dialog != null ? dialog.isShowing() : false;
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
        tvTitle = dialog.findViewById(R.id.tvTitle);

        tvTitle.setText("Seleccione sucursal");

        adaptadorListaSucursal = new AdaptadorListaSucursal(dialog.getContext());
        recycler_view = dialog.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recycler_view.setAdapter(adaptadorListaSucursal);

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
                adaptadorListaSucursal.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adaptadorListaSucursal.setOnItemClickListener(new AdaptadorListaSucursal.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(adaptadorListaSucursal.getItem(position));
                    sucursalSeleccionada = adaptadorListaSucursal.getItem(position);
                } else {
                    onItemClickListener.onItemClick(null);
                    sucursalSeleccionada = null;
                }
            }
        });
    }

    public Sucursal getSelectedItem() {
        return sucursalSeleccionada;
    }

    public void setOnItemClickListener(DialogBuscadorSucursales.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
