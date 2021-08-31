package com.example.phisiotips.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;
    private TextView textCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textCadastro = findViewById(R.id.textCadastro);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
    }


    //Levar para a activity do cadastro
    public void cadastro(View view) {

        Intent intent = new Intent(ActivityLogin.this, ActivityCadastro.class);
        startActivity(intent);
        finish();

    }

    // Verificar usuario logado
    public void verificaUsuarioLogado(View view) {
        if (usuario.getCurrentUser() != null) {
            Log.i("CreateUser", "Sucesso ao logar");


        } else {
            Log.i("CreateUser", "Usuario Deslogado");
        }

    }

    //Logar Usuario e levar para a tela principal

    public void logarUsuario(View view) {
        String email = textEmail.getText().toString();
        String senha = textSenha.getText().toString();


        /*if(email == null || email.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha os campos", Toast.LENGTH_LONG);

        }else if ( senha == null || senha.equals("")){
            Toast.makeText(getApplicationContext(),"Preencha a senha",Toast.LENGTH_LONG);
        }*/

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

        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}