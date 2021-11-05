package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.helper.Base64Custon;
import com.example.phisiotips.activity.activity.model.Enquetes;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdicionarActivity extends AppCompatActivity {

    private EditText textTitulo;
    private EditText textResumo;
    private Button buttonEnviar;
    private DatabaseReference enqueteReferencia;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebaseDataBase();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        textTitulo = findViewById(R.id.textTitulo);
        textResumo = findViewById(R.id.textResumo);
        buttonEnviar = findViewById(R.id.buttonEnviar);


        //Encaminhar para o nó do FireBase
        enqueteReferencia = FirebaseDatabase.getInstance().getReference().child("Enquetes");


        //Envia os dados para o fireBase
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperaResumoEsalvarDados();

            }
        });


    }

    public void recuperaResumoEsalvarDados () {


        String idUsuario = autenticacao.getCurrentUser().getUid();
        usuarioRef = fireBaseRef.child("Usuarios").child(idUsuario);

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);

                //nome.setText(usuario.getNome());

                String titulo = textTitulo.getText().toString();
                String resumo = textResumo.getText().toString();


                if (!titulo.isEmpty()) {
                    if (!resumo.isEmpty()) {

                        Enquetes enquete = new Enquetes(titulo, resumo, usuario.getNome());

                        enqueteReferencia.push().setValue(enquete);


                        Toast.makeText(AdicionarActivity.this, "Enviado com sucesso", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AdicionarActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(AdicionarActivity.this, "Preencha o Resumo!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(AdicionarActivity.this, "Preencha o Titulo!", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}