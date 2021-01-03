package com.example.arkasis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.DB.tablas.TableCoordinadores;
import com.example.arkasis.DB.tablas.TableSucursal;
import com.example.arkasis.R;
import com.example.arkasis.models.Coordinador;
import com.example.arkasis.models.Sucursal;

import java.util.List;

public class AdaptadorListaCoordinadores extends RecyclerView.Adapter<AdaptadorListaCoordinadores.ViewHolder> implements Filterable {
    List<Coordinador> listaCoordinadores;
    private LayoutInflater layoutInflater;
    private Context context;
    private AdaptadorListaCoordinadores.OnItemClickListener onItemClickListener;
    String idSucursal;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filtro = constraint.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            //filterResults.values = listaMunicipiosEncontrados;
            filterResults.values = buscarCoordinador(filtro);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaCoordinadores.clear();
            listaCoordinadores.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public AdaptadorListaCoordinadores.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = layoutInflater.inflate(R.layout.item_simple_list, parent, false);
        return new AdaptadorListaCoordinadores.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaCoordinadores.ViewHolder holder, int position) {
        holder.bindData(listaCoordinadores.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCoordinadores != null ? listaCoordinadores.size() : 0;
    }

    public Coordinador getItem(int position) {
        return listaCoordinadores != null ? listaCoordinadores.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Coordinador coordinador;
        private TextView tv1;

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

        public void bindData(Coordinador coordinador) {
            this.coordinador = coordinador;
            tv1.setText(coordinador.getStrNombre());
        }
    }

    public AdaptadorListaCoordinadores(Context context, String idSucursal) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.idSucursal = idSucursal;
        listaCoordinadores = buscarCoordinador("");
    }

    public List<Coordinador> buscarCoordinador(String filtro) {
        TableCoordinadores table = new TableCoordinadores(context);
        return (List<Coordinador>)(Object)table.findAll(filtro, idSucursal, 100);
    }

    public void setOnItemClickListener(AdaptadorListaCoordinadores.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }
}
