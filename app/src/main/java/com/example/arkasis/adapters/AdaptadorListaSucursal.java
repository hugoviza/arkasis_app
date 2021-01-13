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

import com.example.arkasis.DB.tablas.TableSucursal;
import com.example.arkasis.R;
import com.example.arkasis.models.Sucursal;

import java.util.List;

public class AdaptadorListaSucursal extends RecyclerView.Adapter<AdaptadorListaSucursal.ViewHolder> implements Filterable {
    private List<Sucursal> listaSucursales;
    private LayoutInflater layoutInflater;
    private Context context;
    private AdaptadorListaSucursal.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AdaptadorListaSucursal(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        listaSucursales = buscarSucursales("");
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
            filterResults.values = buscarSucursales(filtro);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaSucursales.clear();
            listaSucursales.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public AdaptadorListaSucursal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_simple_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaSucursal.ViewHolder holder, int position) {
        try {

            holder.bindData(listaSucursales.get(position));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return listaSucursales != null ? listaSucursales.size() : 0;
    }

    public Sucursal getItem(int position) {
        return listaSucursales != null ? listaSucursales.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Sucursal sucursal;
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

        public void bindData(Sucursal sucursal) {
            this.sucursal = sucursal;
            tv1.setText(sucursal.getStrSucursal());
        }
    }

    public List<Sucursal> buscarSucursales(String filtro) {
        TableSucursal tableSucursal = new TableSucursal(context);
        return (List<Sucursal>)(Object)tableSucursal.findAll(filtro, 1000);
    }

    public void setOnItemClickListener(AdaptadorListaSucursal.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
