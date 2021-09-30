package com.example.phisiotips.activity.activity.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.model.Respostas;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarComentariosActivity extends AppCompatActivity {

    private TextInputEditText textComentario;
    private DatabaseReference comentarioReferenica;
    private Button buttonEnviarComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_comentarios);

        textComentario = findViewById(R.id.textComentario);
        buttonEnviarComentario = findViewById(R.id.buttonEnviarComentario);


        //Pegando as chaves de cada item
        Bundle bundle = getIntent().getExtras();
        String chave = bundle.getString("chave");

        Log.i("Teste", "Chave: " + chave );



        //Encaminhar para o nó do FireBase
        comentarioReferenica = FirebaseDatabase.getInstance().getReference().child("Enquetes").child(chave).child("Resposta");

        buttonEnviarComentario.setOnClickListener(v -> enviarComentario());

    }

    //Envia a resposta para o nó do FireBase
    public void enviarComentario() {

        String resposta = textComentario.getText().toString();

        if (!resposta.isEmpty()) {

            Respostas respostas = new Respostas(resposta);

            comentarioReferenica.push().setValue(respostas);

            Toast.makeText(AdicionarComentariosActivity.this, "Resposta Enviada", Toast.LENGTH_LONG).show();

            finish();

        } else {
            Toast.makeText(AdicionarComentariosActivity.this, "Preencha o Campo", Toast.LENGTH_LONG).show();
        }

    }

}