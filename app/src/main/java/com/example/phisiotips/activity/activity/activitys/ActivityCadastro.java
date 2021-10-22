package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.helper.Base64Custon;
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
    private TextInputEditText textEmail, textSenha, textNome;
    private Button botaoCadastro;
    private Usuario usuario;

    // Cadastrar Usuario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Esconde a ActionBar
        getSupportActionBar().hide();

        ids();

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();
                String nome = textNome.getText().toString();

                //Validar os campos preenchidos

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {
                        if (!nome.isEmpty()) {

                            usuario = new Usuario();
                            usuario.setEmail(email);
                            usuario.setNome(nome);
                            usuario.setSenha(senha);
                            cadastrarUsuario();

                        } else {
                            Toast.makeText(ActivityCadastro.this, "Preencha o nome!", Toast.LENGTH_LONG).show();
                        }

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
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String idUsuario = Base64Custon.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                    levarLogin();

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


    public void levarLogin() {

        Intent intent = new Intent(ActivityCadastro.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }


    public void ids() {
        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        botaoCadastro = findViewById(R.id.buttonCadastro);
        textNome = findViewById(R.id.textNome);
    }


}