package com.example.arkasis.adapters;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.R;
import com.example.arkasis.models.SolicitudDispersion;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdaptadorListaSolicitudes extends RecyclerView.Adapter<AdaptadorListaSolicitudes.ViewHolder>{

    private List<SolicitudDispersion> listaSolicitudes;

    private Context context;
    private LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public AdaptadorListaSolicitudes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_solicitud_dispersion, parent, false);
        return new AdaptadorListaSolicitudes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaSolicitudes.ViewHolder holder, int position) {
        holder.bindData(listaSolicitudes.get(position));
    }

    @Override
    public int getItemCount() {
        return listaSolicitudes != null ? listaSolicitudes.size() : 0;
    }

    public SolicitudDispersion getItem(int position) {
        return listaSolicitudes != null ? listaSolicitudes.get(position) : null;
    }

    public void setListaSolicitudes(List<SolicitudDispersion> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SolicitudDispersion solicitudDispersion;

        ImageView imgPerfil;
        TextView tvNombreCliente, tvCURPCliente, txtProducto, tvUbicacion, txtFechaSolicitud, txtMontoSolicitado, txtEstatus, tvEstatusSolicitud;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPerfil = itemView.findViewById(R.id.imgPerfil);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvCURPCliente = itemView.findViewById(R.id.tvCURPCliente);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            txtFechaSolicitud = itemView.findViewById(R.id.txtFechaSolicitud);
            txtMontoSolicitado = itemView.findViewById(R.id.txtMontoSolicitado);
            txtEstatus = itemView.findViewById(R.id.txtEstatus);
            tvEstatusSolicitud = itemView.findViewById(R.id.tvEstatusSolicitud);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bindData(SolicitudDispersion solicitudDispersion) {
            this.solicitudDispersion = solicitudDispersion;

            tvNombreCliente.setText(solicitudDispersion.getStrNombreCompleto());
            tvCURPCliente.setText(solicitudDispersion.getStrCURP());
            txtProducto.setText(solicitudDispersion.getStrProducto());
            tvUbicacion.setText(solicitudDispersion.getStrDomicilioCompleto());
            txtFechaSolicitud.setText(solicitudDispersion.getStrFechaAlta());
            NumberFormat formatter = new DecimalFormat("#,###");
            txtMontoSolicitado.setText(formatter.format(solicitudDispersion.getDblMontoSolicitadoEquipandoHogar())+"");
            tvEstatusSolicitud.setText(solicitudDispersion.getStrStatusSolicitud());
            if(solicitudDispersion.getStrEstatusInserccionServidor().trim().length() > 0) {
                txtEstatus.setText("Estatus de solicitud: " + solicitudDispersion.getStrEstatusInserccionServidor());
            } else {
                txtEstatus.setVisibility(View.GONE);
            }
        }
    }

    public AdaptadorListaSolicitudes(List<SolicitudDispersion> listaSolicitudes, Context context) {
        this.listaSolicitudes = listaSolicitudes;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
