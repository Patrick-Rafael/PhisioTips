package com.example.phisiotips.activity.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.Executor;

public class Cadastro {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;


    // Cadastrar Usuario
    public void enviarCadastro(View view) {

        String email = textEmail.getText().toString();
        String senha = textSenha.getText().toString();

        usuario.createUserWithEmailAndPassword(email, senha).addOnCompleteListener((Executor) Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("CreateUser", "Sucesso ao cadastrar");


                } else {
                    Log.i("CreateUser", "Falha ao cadastrar");
                }
            }
        });


    }
}
