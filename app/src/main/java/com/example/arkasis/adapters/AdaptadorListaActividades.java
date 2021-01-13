package com.example.arkasis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.DB.tablas.TableActividades;
import com.example.arkasis.R;
import com.example.arkasis.models.Actividad;

import java.util.List;

public class AdaptadorListaActividades extends RecyclerView.Adapter<AdaptadorListaActividades.ViewHolder> implements Filterable {
    private List<Actividad> listaActividades ;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filtro = constraint.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            List<Actividad> listaActividades = buscar(filtro);
            listaActividades.add(new Actividad(0, filtro.toUpperCase(), ""));
            filterResults.values = listaActividades;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaActividades.clear();
            listaActividades.addAll((List)results.values);
            notifyDataSetChanged();
        }
    } ;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public AdaptadorListaActividades.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_simple_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaActividades.ViewHolder holder, int position) {
        holder.bindData(listaActividades.get(position));
    }

    @Override
    public int getItemCount() {
        return listaActividades != null ? listaActividades.size() : 0;
    }

    public Actividad getItem(int position) {
        return listaActividades != null ? listaActividades.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Actividad actividad;
        TextView tv1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bindData(Actividad actividad) {
            this.actividad = actividad;
            tv1.setText(actividad.getStrActividad());
        }
    }

    public AdaptadorListaActividades(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        listaActividades = buscar("");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<Actividad> buscar(String filtro) {
        TableActividades table = new TableActividades(context);
        return (List<Actividad>)(Object)table.findAll(filtro, 200);

    }
}
