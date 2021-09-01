package com.example.phisiotips.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phisiotips.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;
    private Button botaoEnviar;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        botaoEnviar = findViewById(R.id.buttonCadastro);


    }


    //Validar Campos Vazios
    /*public Boolean validarCampos(String email, String senha ){

        Boolean camposValidados = true;

        if(email == null || email.equals("")){
            camposValidados = false;

        }else if ( senha == null || senha.equals("")){
            camposValidados = false
        }

    }*/
//Deslogar usuario
    /*public void sair(View view){
        autenticacao.signOut();

        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(intent);
        finish();

    }*/

    

}