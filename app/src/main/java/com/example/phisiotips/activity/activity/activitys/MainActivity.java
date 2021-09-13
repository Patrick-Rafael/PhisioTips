package com.example.phisiotips.activity.activity.activitys;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewMain;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private List<MainEnqutes> listaEnquete = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        botaoSair = findViewById(R.id.buttonCadastro);
        recyclerViewMain = findViewById(R.id.recyclerViewMain);

       // database = FirebaseDatabase.getInstance().getReference("Enquetes");


        //Criar filmes
        this.criarEnquetes();


        //Configurar Adater
        Adapter adapter = new Adapter( listaEnquete );


        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewMain.setAdapter(adapter);


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
    //Criar Lista de Enquetes
    public void criarEnquetes(){

        MainEnqutes enquete = new MainEnqutes("#LowCarb","Pouco carbohidrato pra ficar giga ");
        this.listaEnquete.add( enquete );

        enquete = new MainEnqutes("#BotaOShape", "Ficar giga usando veneno e tudo mais");
        this.listaEnquete.add(enquete);

        enquete = new MainEnqutes("#BotaOShape", "Ficar giga usando veneno e tudo mais");
        this.listaEnquete.add(enquete);

        enquete = new MainEnqutes("#BotaOShape", "Ficar giga usando veneno e tudo mais");
        this.listaEnquete.add(enquete);

        enquete = new MainEnqutes("#BotaOShape", "Ficar giga usando veneno e tudo mais");
        this.listaEnquete.add(enquete);

        enquete = new MainEnqutes("#BotaOShape", "Ficar giga usando veneno e tudo mais");
        this.listaEnquete.add(enquete);


    }

}