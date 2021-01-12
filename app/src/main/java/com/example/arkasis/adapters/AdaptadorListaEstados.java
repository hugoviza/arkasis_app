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

import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.R;
import com.example.arkasis.models.Estado;

import java.util.List;

public class AdaptadorListaEstados extends RecyclerView.Adapter<AdaptadorListaEstados.ViewHolder> implements Filterable {
    private Context context;
    private List<Estado> listaEstados;
    private LayoutInflater layoutInflater;
    private AdaptadorListaEstados.OnItemClickListener onItemClickListener;

    public AdaptadorListaEstados(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        listaEstados = buscarEstado("");
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
            filterResults.values = buscarEstado(filtro);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaEstados.clear();
            listaEstados.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_simple_list, parent, false);
        return new AdaptadorListaEstados.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listaEstados.get(position));
    }

    @Override
    public int getItemCount() {
        return listaEstados != null ? listaEstados.size() : 0;
    }

    public Estado getItem(int position) {
        return listaEstados != null ? listaEstados.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Estado estado;
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

        public void bindData(Estado estado) {
            this.estado = estado;
            tv1.setText(estado.getStrEstado());
        }
    }

    public List<Estado> buscarEstado(String filtro) {
        TableMunicipios table = new TableMunicipios(context);
        return (List<Estado>)(Object)table.findAllEstados(filtro);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
