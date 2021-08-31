package com.example.phisiotips.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private TextInputEditText textEmail;
    private TextInputEditText textSenha;
    private Button botaoEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        botaoEnviar = findViewById(R.id.buttonEnviar);


    }

    //Deslogar Usuario
    public void deslogarUsuario(View view) {
        usuario.signOut();
    }

    // Verificar usuario logado
    public void verificaUsuarioLogado(View view) {
        if (usuario.getCurrentUser() != null) {
            Log.i("CreateUser", "Sucesso ao logar");


        } else {
            Log.i("CreateUser", "Usuario Deslogado");
        }

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

}