package com.example.phisiotips.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.phisiotips.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class ActivityCadastro extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
    }




    // Cadastrar Usuario e envia de volta para a tela de login
    public void enviarCadastro(View view) {

        String email = textEmail.getText().toString();
        String senha = textSenha.getText().toString();

        usuario.createUserWithEmailAndPassword(email, senha).addOnCompleteListener( ActivityCadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("CreateUser", "Sucesso ao cadastrar");


                } else {
                    Log.i("CreateUser", "Falha ao cadastrar");
                }
            }
        });

        Intent intent = new Intent(ActivityCadastro.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

}