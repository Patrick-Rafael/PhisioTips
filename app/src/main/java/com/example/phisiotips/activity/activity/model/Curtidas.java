package com.example.phisiotips.activity.activity.model;

import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Curtidas {

    public int qtdCurtidas = 0;
    public MainEnqutes enquete;
    public Usuario usuario;


    public Curtidas() {
    }

    public void salvar(){
        DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebaseDataBase();

        //Objeto para o usuario
        HashMap<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("autor",usuario.getNome());


        DatabaseReference pCurtidasRef = fireBaseRef
                .child("enquete-curtida")
                .child(enquete.getChave())// id da enquete
                .child(usuario.getIdUsuario());//id do usuario

        pCurtidasRef.setValue(dadosUsuario);

        //atualizar curtidas
        atualizarQuantidade(1);


    }

    public void remover(){


        DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebaseDataBase();



        DatabaseReference pCurtidasRef = fireBaseRef
                .child("enquete-curtida")
                .child(enquete.getChave())// id da enquete
                .child(usuario.getIdUsuario());//id do usuario

        pCurtidasRef.removeValue();

        //atualizar curtidas
        atualizarQuantidade(-1);


    }

    public void atualizarQuantidade (int valor){
        DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebaseDataBase();

        DatabaseReference pCurtidasRef = fireBaseRef
                .child("enquete-curtida")
                .child(enquete.getChave())// id da enquete
                .child("qtdCurtidas");
        setQtdCurtidas(getQtdCurtidas() + valor);
        pCurtidasRef.setValue(getQtdCurtidas());


    }





    public MainEnqutes getEnquete() {
        return enquete;
    }

    public void setEnquete(MainEnqutes enquete) {
        this.enquete = enquete;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(int qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }
}
