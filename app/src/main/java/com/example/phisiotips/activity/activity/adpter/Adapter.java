package com.example.phisiotips.activity.activity.adpter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.activitys.ClickRecycler;
import com.example.phisiotips.activity.activity.activitys.ComentariosActivity;
import com.example.phisiotips.activity.activity.activitys.MainActivity;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.helper.UsuarioFirebase;
import com.example.phisiotips.activity.activity.model.Curtidas;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyviewHolder> {



    private List<MainEnqutes> listaEnquetes;
    private ClickRecycler clickListener;


    public Adapter(List<MainEnqutes> lista, ClickRecycler clickListener) {
        this.listaEnquetes = lista;
        this.clickListener = clickListener;

    }

    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_main, parent, false);
        return new MyviewHolder(itemLista);


    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

       final MainEnqutes enquete  = listaEnquetes.get( position );
       final Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        holder.titulo.setText(enquete.getTitulo());
        holder.resumo.setText(enquete.getResumo());

        //Recuperando Enquete curtida e quantidade de curtidas
        //Log.d("Patrick", "EnqueteId " + enquete.getChave());

        DatabaseReference curtidasRef = ConfiguracaoFireBase.getFirebase()
                .child("enquete-curtida")
                .child(enquete.getChave());

        curtidasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int qtdCurtidas = 0;
                if(snapshot.hasChild("enquete-curtida")){
                    Curtidas curtida = snapshot.getValue(Curtidas.class);
                    qtdCurtidas = curtida.getQtdCurtidas();

                }

                //Verificação se já foi clicado
                if(snapshot.hasChild(usuarioLogado.getIdUsuario())){
                    holder.likeButton.setLiked(true);


                }else{
                    holder.likeButton.setLiked(false);

                }





                //Montar objeto curtida

                Curtidas curtidas = new Curtidas();
                curtidas.setEnquete(enquete);
                curtidas.setUsuario(usuarioLogado);
                curtidas.setQtdCurtidas(qtdCurtidas);

                //Evento de click like
                holder.likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        curtidas.salvar();
                        holder.qtdCurtidas.setText( curtidas.getQtdCurtidas() + " curtidas" );

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        curtidas.remover();
                        holder.qtdCurtidas.setText( curtidas.getQtdCurtidas() + " curtidas" );

                    }
                });


                holder.qtdCurtidas.setText(curtidas.getQtdCurtidas() + " curtidas");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return listaEnquetes.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView titulo,resumo,qtdCurtidas;

        LikeButton likeButton;


        public MyviewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            resumo = itemView.findViewById(R.id.textResumo);
            likeButton = itemView.findViewById(R.id.likeButton);
            qtdCurtidas = itemView.findViewById(R.id.textQtdCurtidas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });



        }

    }


}
