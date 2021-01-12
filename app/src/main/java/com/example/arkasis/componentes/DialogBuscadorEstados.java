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
import com.example.arkasis.adapters.AdaptadorListaEstados;
import com.example.arkasis.models.Estado;

public class DialogBuscadorEstados {
    private Context context;
    private View parent;
    private Dialog dialog;

    //Dialog componentes
    EditText txtBuscar;
    RecyclerView recycler_view;
    Button btnCerrar;

    //Data
    AdaptadorListaEstados adaptadorListaEstados;
    Estado estadoSeleccionado;

    //Eventos
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Estado estado);
    }

    public DialogBuscadorEstados(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    public void show() {
        if(dialog == null) {
            init();
            dialog.show();
        } else{
            dialog.show();
            adaptadorListaEstados.getFilter().filter("");
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
        estadoSeleccionado = null;
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

        tvTitle.setText("Seleccione estado");
        txtBuscar.requestFocus();
        showSoftKeyboard(txtBuscar);

        adaptadorListaEstados = new AdaptadorListaEstados(context);
        recycler_view = dialog.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recycler_view.setAdapter(adaptadorListaEstados);

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
                adaptadorListaEstados.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adaptadorListaEstados.setOnItemClickListener(new AdaptadorListaEstados.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(adaptadorListaEstados.getItem(position));
                    estadoSeleccionado = adaptadorListaEstados.getItem(position);
                } else {
                    onItemClickListener.onItemClick(null);
                    estadoSeleccionado = null;
                }
            }
        });
    }

    public Estado getSelectedItem() {
        return estadoSeleccionado;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void showSoftKeyboard(View view) {
        if(view.requestFocus()){
            InputMethodManager imm = (InputMethodManager)
                    parent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
