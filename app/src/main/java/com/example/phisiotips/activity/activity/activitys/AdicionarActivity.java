package com.example.phisiotips.activity.activity.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.model.Enquetes;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarActivity extends AppCompatActivity {

    private EditText textTitulo;
    private EditText textResumo;
    private Button buttonEnviar;
    private DatabaseReference enqueteReferencia;

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

                enviarDados();

            }
        });


    }

    //Enviar dados para a lista e fazer a verificação
    public void enviarDados() {

        String titulo = textTitulo.getText().toString();
        String resumo = textResumo.getText().toString();


        if (!titulo.isEmpty()) {
            if (!resumo.isEmpty()) {

                Enquetes enquete = new Enquetes(titulo, resumo);


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
}