package com.example.phisiotips.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ActivityCadastro extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;
    private Button botaoCadastro;
    private Usuario usuario;

// Cadastrar Usuario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        botaoCadastro = findViewById(R.id.buttonCadastro);

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                //Validar os campos preenchidos

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        cadastrarUsuario();


                    } else {
                        Toast.makeText(ActivityCadastro.this, "Preencha a senha!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ActivityCadastro.this, "Preencha o E-mail!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    // Cadastrar Usuario e envia de volta para a tela de login
    public void cadastrarUsuario() {

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    abrirLogin();

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Essa conta já foi cadastrada";

                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuario" + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(ActivityCadastro.this, excecao, Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void abrirLogin() {

        Intent intent = new Intent(ActivityCadastro.this, ActivityLogin.class);
        startActivity(intent);
        finish();

    }


}