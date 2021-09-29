package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.adpter.Adapter;
import com.example.phisiotips.activity.activity.adpter.AdapterComentarios;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.model.Respostas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComentariosActivity extends AppCompatActivity {

    private RecyclerView recyclerComentarios;
    private FloatingActionButton buttonComentarios;
    private List<Respostas> listaRespostas = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String chaveFireBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        buttonComentarios = findViewById(R.id.buttonComentario);

        //Pegando as chaves de cada item
        Bundle bundle = getIntent().getExtras();
        String chave = bundle.getString("chave");


        buttonComentarios.setOnClickListener(v -> {
            Intent intent = new Intent(ComentariosActivity.this, AdicionarComentariosActivity.class);
            intent.putExtra("chave", chave);
            startActivity(intent);

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


}
