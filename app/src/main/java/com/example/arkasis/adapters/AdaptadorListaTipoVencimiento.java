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

import com.example.arkasis.DB.tablas.TableTipoVencimiento;
import com.example.arkasis.R;
import com.example.arkasis.models.Sucursal;
import com.example.arkasis.models.TipoVencimiento;

import java.util.List;

public class AdaptadorListaTipoVencimiento extends RecyclerView.Adapter<AdaptadorListaTipoVencimiento.ViewHolder> implements Filterable {
    private List<TipoVencimiento> lista;
    private LayoutInflater layoutInflater;
    private Context context;
    private AdaptadorListaTipoVencimiento.OnItemClickListener onItemClickListener;
    private String idTipoVencimiento, idSucursal;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AdaptadorListaTipoVencimiento(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        lista = buscarListaTipoVencimientoPorSucursal("");
    }

    @Override
    public Filter getFilter() { return listFilter; }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filtro = constraint.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            filterResults.values = buscarListaTipoVencimientoPorSucursal(filtro);
            notifyDataSetChanged();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lista.clear();
            lista.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public AdaptadorListaTipoVencimiento.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_simple_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaTipoVencimiento.ViewHolder holder, int position) {
        try {
            holder.bindData(lista.get(position));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public TipoVencimiento getItem(int position) {
        return lista != null ? lista.get(position) : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TipoVencimiento tipoVencimiento;
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

        public void bindData(TipoVencimiento tipoVencimiento) {
            this.tipoVencimiento = tipoVencimiento;
            tv1.setText(tipoVencimiento.getStrTipoVencimiento());
        }
    }

    public List<TipoVencimiento> buscarListaTipoVencimientoPorSucursal(String strTipoVencimiento) {
        TableTipoVencimiento table = new TableTipoVencimiento(context);
        return (List<TipoVencimiento>)(Object)table.findAll(idSucursal, strTipoVencimiento, 1000);
    }

    public void setOnItemClickListener(AdaptadorListaTipoVencimiento.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIdTipoVencimiento(String idTipoVencimiento) {
        this.idTipoVencimiento = idTipoVencimiento;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
        this.getFilter().filter("");
    }
}
