package com.example.phisiotips.activity.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;

    //Logar Usuario
    public void logarUsuario(View view) {
        String email = textEmail.getText().toString();
        String senha = textSenha.getText().toString();


        usuario.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.i("signIn", "Sucesso ao logar ");

                } else {
                    Log.i("signIn", "Erro ao logar");

                }

            }
        });
    }


}
