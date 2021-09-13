package com.example.phisiotips.activity.activity.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.model.MainEnqutes;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyviewHolder> {

    private List<MainEnqutes> listaEnquetes;

    public Adapter(List<MainEnqutes> lista) {
        this.listaEnquetes = lista;

    }

    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_main, parent, false);
        return new MyviewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        MainEnqutes enquete  = listaEnquetes.get( position );

        holder.titulo.setText(enquete.getTitulo());
        holder.resumo.setText(enquete.getResumo());


    }

    @Override
    public int getItemCount() {
        return listaEnquetes.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView resumo;

        public MyviewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            resumo = itemView.findViewById(R.id.textResumo);

        }
    }


}
