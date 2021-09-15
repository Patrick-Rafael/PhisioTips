package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.adpter.Adapter;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //private Button botaoSair;
    //private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewMain;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private List<MainEnqutes> listaEnquete = new ArrayList<>();
    private FloatingActionButton buttonAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Redireciona para a pagina de adiconar
        buttonAdicionar = findViewById(R.id.buttonAdicionar);

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AdicionarActivity.class);
                startActivity(intent);
                finish();


            }
        });

        // autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        //botaoSair = findViewById(R.id.buttonCadastro);

        recyclerViewMain = findViewById(R.id.recyclerViewMain);


        //autenticacao.signOut();

        //Configurar Adater
        Adapter adapter = new Adapter(listaEnquete);


        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewMain.setAdapter(adapter);


        //Pegando do banco de dados
        database = FirebaseDatabase.getInstance().getReference().child("Enquetes");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MainEnqutes enquetes = dataSnapshot.getValue(MainEnqutes.class);

                    listaEnquete.add(enquetes);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


// Botão Sair
        /*botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao.signOut();

                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent);
                finish();

            }
        });*/


    }

}