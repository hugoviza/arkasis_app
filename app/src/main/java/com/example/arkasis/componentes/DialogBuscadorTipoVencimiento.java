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
import com.example.arkasis.adapters.AdaptadorListaTipoVencimiento;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.TipoVencimiento;

public class DialogBuscadorTipoVencimiento {
    private Context context;
    private View parent;
    private Dialog dialog;

    //Dialog componentes
    TextView tvTitle;
    EditText txtBuscar;
    RecyclerView recycler_view;
    Button btnCerrar;

    //Data
    AdaptadorListaTipoVencimiento adaptadorLista;
    TipoVencimiento tipoVencimientoSeleccionado;
    String idTipoVencimiento, idSucursal;

    //Eventos
    DialogBuscadorTipoVencimiento.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TipoVencimiento tipoVencimiento);
    }

    public DialogBuscadorTipoVencimiento(Context context, View parent) {
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
        idTipoVencimiento = null;
        tipoVencimientoSeleccionado = null;
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

        tvTitle.setText("Seleccione tipo vencimiento");

        adaptadorLista = new AdaptadorListaTipoVencimiento(dialog.getContext());
        recycler_view = dialog.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recycler_view.setAdapter(adaptadorLista);

        adaptadorLista.setIdSucursal(idSucursal == null ? "" : idSucursal);

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
                adaptadorLista.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adaptadorLista.setOnItemClickListener(new AdaptadorListaTipoVencimiento.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    tipoVencimientoSeleccionado = adaptadorLista.getItem(position);
                    onItemClickListener.onItemClick(adaptadorLista.getItem(position));
                } else {
                    tipoVencimientoSeleccionado = null;
                    onItemClickListener.onItemClick(null);
                }
            }
        });
    }

    public TipoVencimiento getSelectedItem() {
        return tipoVencimientoSeleccionado;
    }

    public void setOnItemClickListener(DialogBuscadorTipoVencimiento.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal == null ? "" : idSucursal;
        this.limpiar();
        if(adaptadorLista != null) {
            this.adaptadorLista.setIdSucursal(idSucursal == null ? "" : idSucursal);
        }
    }

    public void setIdTipoVencimiento(String idTipoVencimiento) {
        this.idTipoVencimiento = idTipoVencimiento == null ? "" : idTipoVencimiento;
        if(adaptadorLista != null) {
            this.adaptadorLista.setIdTipoVencimiento(idTipoVencimiento == null ? "" : idTipoVencimiento);
        }
    }
}
