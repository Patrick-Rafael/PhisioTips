package com.example.phisiotips.activity.activity.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFireBase {

    //Retorna Instancia do FireBaseDataBase

    private static DatabaseReference firebase;
    private static DatabaseReference referenciaFirebase;

    public static DatabaseReference getFirebaseDataBase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }


    private static FirebaseAuth autenticacao;

    //retorna a intancia do FireBaseAuth
    public static FirebaseAuth getFireBaseAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

    }

    public static DatabaseReference getFirebase(){
        if( referenciaFirebase == null ){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }
}
