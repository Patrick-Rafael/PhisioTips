package com.example.phisiotips.activity.activity.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFireBase {

    private static FirebaseAuth autenticacao;

    //retorna a intancia do FireBaseAuth
    public static FirebaseAuth getFireBaseAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

    }
}
