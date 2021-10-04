package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.adpter.Adapter;
import com.example.phisiotips.activity.activity.adpter.AdapterComentarios;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.model.Respostas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComentariosActivity extends AppCompatActivity {

    private RecyclerView recyclerComentarios;
    //private FloatingActionButton buttonComentarios;
    private List<Respostas> listaRespostas = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference comentarioReferenica;
    private TextView titulo;
    private ImageButton buttonEnviar;
    private TextInputEditText textComentario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        ids();


        //Pegando as chaves de cada item
        Bundle bundle = getIntent().getExtras();
        String chave = bundle.getString("Chave");
        String textTitulo = bundle.getString("Titulo");

        Log.i("texte", "chave: " + chave);


        //Encaminhar para o nó do FireBase
        comentarioReferenica = FirebaseDatabase.getInstance().getReference().child("Enquetes").child(chave).child("Resposta");

        //Pegando Titulo da postagem
        titulo.setText(textTitulo);



        //Enviar para activity de adicionar comentarios
        /*buttonComentarios.setOnClickListener(v -> {
            Intent intent = new Intent(ComentariosActivity.this, AdicionarComentariosActivity.class);
            intent.putExtra("chave", chave);
            startActivity(intent);

        });*/

        //Envia o comentario
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarComentario();
                textComentario.setText("");
                listaRespostas.clear();
            }
        });



        recyclerComentarios = findViewById(R.id.recyclerComentarios);

        //Configurar adapter
        AdapterComentarios adapterComentarios = new AdapterComentarios(listaRespostas);


        //Configurar RecyclerView

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerComentarios.setLayoutManager(layoutManager);
        recyclerComentarios.setHasFixedSize(true);
        recyclerComentarios.setAdapter(adapterComentarios);
        recyclerComentarios.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));


        //Puxando a lista do Firebase e exibindo no RecyclerView

        database = FirebaseDatabase.getInstance().getReference().child("Enquetes").child(chave).child("Resposta");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Respostas respostas = dataSnapshot.getValue(Respostas.class);

                    listaRespostas.add(respostas);


                }

                adapterComentarios.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    //Envia a resposta para o nó do FireBase
    public void enviarComentario() {

        String resposta = textComentario.getText().toString();

        if (!resposta.isEmpty()) {

            Respostas respostas = new Respostas(resposta);

            comentarioReferenica.push().setValue(respostas);

            Toast.makeText(ComentariosActivity.this, "Resposta Enviada", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(ComentariosActivity.this, "Preencha o Campo", Toast.LENGTH_LONG).show();
        }

    }

    public void ids() {
        //buttonComentarios = findViewById(R.id.buttonComentario);
        titulo = findViewById(R.id.titulo);
        buttonEnviar = findViewById(R.id.buttonEnvivarTeste);
        textComentario = findViewById(R.id.textComentarioTeste);

    }


}
