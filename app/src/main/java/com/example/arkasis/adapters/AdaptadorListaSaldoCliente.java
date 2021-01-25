package com.example.arkasis.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.R;
import com.example.arkasis.models.SaldoCliente;

import java.util.List;

public class AdaptadorListaSaldoCliente extends RecyclerView.Adapter<AdaptadorListaSaldoCliente.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SaldoCliente> listaSaldos;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card_saldo_cliente, parent,  false);

        return new AdaptadorListaSaldoCliente.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listaSaldos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaSaldos != null ? listaSaldos.size() : 0;
    }

    public SaldoCliente getItem(int position) {
        return listaSaldos != null ? listaSaldos.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SaldoCliente saldoCliente;
        private ImageButton btnExpand;
        private TextView tvProducto, tvContrato, tvFechaMinistracion, tvFechaVencimiento, tvTotalPagos, tvCapital, tvIntereses, tvSeguro, tvTotal, tvAbono, tvSaldo;
        private LinearLayout llInfoCompleta;

        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;

            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvContrato = itemView.findViewById(R.id.tvContrato);
            tvFechaMinistracion = itemView.findViewById(R.id.tvFechaMinistracion);
            tvFechaVencimiento = itemView.findViewById(R.id.tvFechaVencimiento);
            tvTotalPagos = itemView.findViewById(R.id.tvTotalPagos);
            tvCapital = itemView.findViewById(R.id.tvCapital);
            tvIntereses = itemView.findViewById(R.id.tvIntereses);
            tvSeguro = itemView.findViewById(R.id.tvSeguro);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvAbono = itemView.findViewById(R.id.tvAbono);
            tvSaldo = itemView.findViewById(R.id.tvSaldo);
            btnExpand = itemView.findViewById(R.id.btnExpand);
            llInfoCompleta = itemView.findViewById(R.id.llInfoCompleta);

            btnExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnExpand.getContentDescription() == context.getString(R.string.abrir)) {
                        //La info está cerrada, así que la abrimos
                        TransitionManager.beginDelayedTransition(llInfoCompleta, new AutoTransition());
                        llInfoCompleta.setVisibility(View.VISIBLE);
                        //Cambiamos el boton y el estatus para que al siguiente clic se cierre la seccion de info

                        btnExpand.setContentDescription( context.getString(R.string.cerrar));
                        btnExpand.setImageResource( R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        //Ocultamos la info
                        TransitionManager.beginDelayedTransition(llInfoCompleta, new AutoTransition());
                        llInfoCompleta.setVisibility(View.GONE);
                        //Cambiamos el boton y el estatus para que al siguiente clic se cierre la seccion de info

                        btnExpand.setContentDescription( context.getString(R.string.abrir));
                        btnExpand.setImageResource( R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                }
            });
        }

        public void bindData(SaldoCliente saldoCliente) {
            this.saldoCliente = saldoCliente;
            tvProducto.setText(saldoCliente.getStrProducto());
            tvContrato.setText(saldoCliente.getStrFolioContrato());
            tvFechaMinistracion.setText(saldoCliente.getDatFechaMinistracion());
            tvFechaVencimiento.setText(saldoCliente.getDatFechaVencimiento());
            tvTotalPagos.setText(saldoCliente.getIntTotalPagos());
            tvCapital.setText(saldoCliente.getDblCapital());
            tvIntereses.setText(saldoCliente.getDblIntereses());
            tvSeguro.setText(saldoCliente.getDblSeguro());
            tvTotal.setText(saldoCliente.getDblTotal());
            tvAbono.setText(saldoCliente.getDblAbono());
            tvSaldo.setText(saldoCliente.getDblSaldo());
        }
    }


    public AdaptadorListaSaldoCliente(List<SaldoCliente> listaSaldos, Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listaSaldos = listaSaldos;
    }
}
