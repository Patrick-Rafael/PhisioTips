package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.adpter.Adapter;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.model.Enquetes;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.sobre.SobreFragment;
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


    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewMain;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private List<MainEnqutes> listaEnquete = new ArrayList<>();
    private FloatingActionButton buttonAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = FirebaseAuth.getInstance();


        //Redireciona para a pagina de adiconar
        buttonAdicionar = findViewById(R.id.buttonAdicionar);

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AdicionarActivity.class);
                startActivity(intent);



            }
        });


        recyclerViewMain = findViewById(R.id.recyclerViewMain);


        //Configurar Adater
        Adapter adapter = new Adapter(listaEnquete);


        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        //recyclerViewMain.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewMain.setAdapter(adapter);

        //Evento de Click
        recyclerViewMain.addOnItemTouchListener(
                new ClickListener(
                        getApplicationContext(),
                        recyclerViewMain,
                        new ClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                MainEnqutes enquetes = listaEnquete.get(position);

                                Intent intent_comentarios = new Intent(MainActivity.this, ComentariosActivity.class);
                                intent_comentarios.putExtra("Chave", enquetes.getChave());
                                intent_comentarios.putExtra("Titulo",enquetes.getTitulo());
                                startActivity(intent_comentarios);

                            }


                            @Override
                            public void onLongItemClick(View view, int position) {

                                MainEnqutes enquetes = listaEnquete.get(position);

                                 Toast.makeText(
                                        getApplicationContext(),
                                        "Item precionado: " + enquetes.getTitulo(),
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }

                )
        );


        //Pegando do banco de dados e constroi a lista
        database = FirebaseDatabase.getInstance().getReference().child("Enquetes");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MainEnqutes enquetes = dataSnapshot.getValue(MainEnqutes.class);

                    //pegando chave
                   enquetes.setChave(dataSnapshot.getKey());




                    listaEnquete.add(enquetes);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //Infla o menu na action Bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Adiciona envento de clique nas opções
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                autenticacao.signOut();
                Intent intent_sair = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent_sair);
                return true;

            case R.id.menu_sobre:
                Intent intent_sobre = new Intent(MainActivity.this, SobreActivity.class);
                startActivity(intent_sobre);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}