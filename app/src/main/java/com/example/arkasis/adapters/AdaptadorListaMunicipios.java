package com.example.arkasis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.DB.tablas.TableMunicipios;
import com.example.arkasis.R;
import com.example.arkasis.models.Municipio;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaMunicipios extends RecyclerView.Adapter<AdaptadorListaMunicipios.ViewHolder> implements Filterable {
    private List<Municipio> listaMunicipios;
    //private List<Municipio> listaMunicipiosFull;

    private LayoutInflater layoutInflater;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCiudad, tvEstado;
        private Municipio municipio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCiudad = itemView.findViewById(R.id.tvCiudad);
            tvEstado = itemView.findViewById(R.id.tvEstado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        public void bindData(final Municipio municipio) {
            this.municipio = municipio;

            tvCiudad.setText(municipio.getStrMunicipio());
            tvEstado.setText(municipio.getStrEstado());
        }
    }

    /*public AdaptadorListaMunicipios(List<Municipio> listaMunicipios, Context context) {
        this.context = context;
        this.listaMunicipios = listaMunicipios;
        this.listaMunicipiosFull = new ArrayList<>(listaMunicipios);
        this.layoutInflater = LayoutInflater.from(context);
    }*/

    public AdaptadorListaMunicipios(Context context) {
        this.context = context;
        this.listaMunicipios = selectMunicipios("");
        //this.listaMunicipiosFull = new ArrayList<>(listaMunicipios);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdaptadorListaMunicipios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_ciudad, null);

        return new AdaptadorListaMunicipios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaMunicipios.ViewHolder holder, int position) {
        holder.bindData(listaMunicipios.get(position));
    }

    @Override
    public int getItemCount() {
        return listaMunicipios.size();
    }

    @Override
    public Filter getFilter() {
        return municipiosFilter;
    }

    private Filter municipiosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filtro = constraint.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            //filterResults.values = listaMunicipiosEncontrados;
            filterResults.values = selectMunicipios(filtro);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaMunicipios.clear();
            listaMunicipios.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Municipio getItem(int position) {
        return listaMunicipios != null
                ? (listaMunicipios.get(position))
                : null;
    }

    public List<Municipio> selectMunicipios(String filtro) {
        TableMunicipios tableMunicipios = new TableMunicipios(context);
        return tableMunicipios.findAll(filtro, 100);
    }

    public void reiniciar() {
        if(listaMunicipios == null) {
            listaMunicipios = new ArrayList<>();
        }

        listaMunicipios.clear();
        listaMunicipios = selectMunicipios("");
        notifyDataSetChanged();
    }
}
