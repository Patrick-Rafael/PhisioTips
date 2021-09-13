package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ActivityLogin extends AppCompatActivity {

    private TextInputEditText textEmail;
    private TextInputEditText textSenha;
    private TextView textCadastro;
    private Button botaoLogin;
    private Usuario usuarios;
    private FirebaseAuth autenticacao;
    private FirebaseAuth autenticacaoAuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        textCadastro = findViewById(R.id.textCadastro);
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        botaoLogin = findViewById(R.id.buttonLogin);

        //Faz verificação das execoções e  acessa a pagina principal

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        usuarios = new Usuario();
                        usuarios.setEmail(email);
                        usuarios.setSenha(senha);
                        validarlogin();


                    } else {
                        Toast.makeText(ActivityLogin.this, "Preencha a senha!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ActivityLogin.this, "Preencha o E-mail!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    //Levar para a activity do cadastro
    public void cadastro(View view) {

        Intent intent = new Intent(ActivityLogin.this, ActivityCadastro.class);
        startActivity(intent);
        finish();

    }


    //Valida o login do usuario
    public void validarlogin() {

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    abrirPrincipal();

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario não está cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha não corresponde a usuario cadastrado ";

                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuario" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(ActivityLogin.this, excecao, Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    //Levar para a MainActivity
    public void abrirPrincipal() {
        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    //Verifica o usuario
    public void verificarUsuarioLogado() {

        autenticacaoAuto = ConfiguracaoFireBase.getFireBaseAutenticacao();

        if (autenticacaoAuto.getCurrentUser() != null) {

            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
            startActivity(intent);


        }


    }


}