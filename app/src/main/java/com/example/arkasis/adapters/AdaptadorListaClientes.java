package com.example.arkasis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arkasis.BottomBarActivity;
import com.example.arkasis.R;
import com.example.arkasis.config.Config;
import com.example.arkasis.interfaces.APIClientesInterface;
import com.example.arkasis.models.Cliente;
import com.example.arkasis.models.ResponseAPI;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaptadorListaClientes extends RecyclerView.Adapter<AdaptadorListaClientes.ViewHolder> {
    private Context context;
    private List<Cliente> listaClientes;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public AdaptadorListaClientes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cliente, parent, false);
        return new AdaptadorListaClientes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaClientes.ViewHolder holder, int position) {
        holder.bindData(listaClientes.get(position));
    }

    @Override
    public int getItemCount() {
        return listaClientes != null ? listaClientes.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPerfil;
        TextView tvNombreCliente, tvCURPCliente, tvUbicacion, tvEstatusCliente;
        MaterialCardView card_cliente;
        Cliente cliente;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPerfil = itemView.findViewById(R.id.imgPerfil);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvCURPCliente = itemView.findViewById(R.id.tvCURPCliente);
            tvEstatusCliente = itemView.findViewById(R.id.tvEstatusCliente);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            card_cliente = itemView.findViewById(R.id.card_cliente);

            card_cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        public void bindData(final Cliente cliente) {
            this.cliente = cliente;

            tvNombreCliente.setText(cliente.getStrNombreCompleto());
            tvCURPCliente.setText(cliente.getStrCurp());
            tvUbicacion.setText(cliente.getMunicipioResidencia());
            tvEstatusCliente.setVisibility(View.GONE);
        }
    }

    public AdaptadorListaClientes(@NonNull Context context) {
        this.context = context;
        this.listaClientes = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
        notifyDataSetChanged();
    }

    public void buscarClientes(Cliente cliente) {
        try
        {
            BottomBarActivity.abrirLoading("Buscando...");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIClientesInterface apiClientesInterface = retrofit.create(APIClientesInterface.class);
            Call<ResponseAPI> apiCall = apiClientesInterface.getClientes(cliente);
            apiCall.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    if(response.body() == null || response.body().getResultado() == null) {
                        //No hay nada por hacer

                        Toast.makeText(context, context.getString(R.string.sin_segistros), Toast.LENGTH_SHORT).show();
                        setData(new ArrayList<>());

                    } else {
                        List<Cliente> listaClientes = new ArrayList<>();
                        try {
                            if(response.body().getSuccess()) {
                                for (LinkedTreeMap<Object, Object> treeMap : (ArrayList<LinkedTreeMap<Object, Object>>)response.body().getResultado()) {
                                    listaClientes.add(new Cliente(treeMap));
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "No es posible cargar cliente", Toast.LENGTH_SHORT).show();
                        }

                        setData(listaClientes);
                    }
                    BottomBarActivity.cerrarLoading();
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Toast.makeText(context, context.getString(R.string.sin_acceso_servidor), Toast.LENGTH_SHORT).show();
                    setData(new ArrayList<>());
                    BottomBarActivity.cerrarLoading();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            setData(new ArrayList<>());
            BottomBarActivity.cerrarLoading();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Cliente getItem(int position) {
        return listaClientes.get(position);
    }
}
