package com.example.phisiotips.activity.activity.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.model.Respostas;
import com.example.phisiotips.activity.activity.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.MyViewHolderComentarios> {

    private List<Respostas> listaRespostas;




    public AdapterComentarios(List<Respostas> listaRespostas) {
        this.listaRespostas = listaRespostas;

    }



    public MyViewHolderComentarios onCreateViewHolder(ViewGroup parent, int viewType) {

        View comentarioLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comentarios, parent, false);


        return new MyViewHolderComentarios(comentarioLista);
    }


    @Override
    public void onBindViewHolder(AdapterComentarios.MyViewHolderComentarios holder, int position) {

        Respostas respostas = listaRespostas.get(position);

        holder.comentarios.setText(respostas.getResposta());

        holder.nomeComentario.setText(respostas.getUsuario());




    }



    @Override
    public int getItemCount() {
        return listaRespostas.size();
    }

    public class MyViewHolderComentarios extends RecyclerView.ViewHolder {

        TextView comentarios, nomeComentario;


        public MyViewHolderComentarios(View itemView) {
            super(itemView);

            comentarios = itemView.findViewById(R.id.textComentario);
            nomeComentario = itemView.findViewById(R.id.textNomeComentario);


        }
    }



}
