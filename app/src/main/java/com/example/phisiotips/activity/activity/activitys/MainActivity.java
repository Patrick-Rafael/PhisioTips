package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements ClickRecycler {


    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewMain;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private List<MainEnqutes> listaEnquete = new ArrayList<>();
    private FloatingActionButton buttonAdicionar;
    private AlertDialog alertDialog;


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

        //swipe();
        //Configurar Adater
        Adapter adapter = new Adapter(listaEnquete, this);


        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewMain.setAdapter(adapter);


        //Pegando do banco de dados e constroindo a lista
        //Fazendo a dialog de Loadding screen
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando")
                .setCancelable(false)
                .build();
        alertDialog.show();


        database = FirebaseDatabase.getInstance().getReference().child("Enquetes");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEnquete.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MainEnqutes enquetes = dataSnapshot.getValue(MainEnqutes.class);

                    //pegando chave
                    enquetes.setChave(dataSnapshot.getKey());


                    listaEnquete.add(enquetes);
                    alertDialog.dismiss();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /*ublic void swipe(){

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {



            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerViewMain);
    }*/

    //Infla o menu na action Bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Adiciona envento de clique nas op????es
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                autenticacao.signOut();
                Intent intent_sair = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent_sair);
                finish();
                return true;

            case R.id.menu_sobre:
                Intent intent_sobre = new Intent(MainActivity.this, SobreActivity.class);
                startActivity(intent_sobre);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int position) {

        MainEnqutes item = listaEnquete.get(position);

        Intent intent_comentarios = new Intent(MainActivity.this, ComentariosActivity.class);
        intent_comentarios.putExtra("Chave", item.getChave());
        intent_comentarios.putExtra("Titulo", item.getTitulo());
        startActivity(intent_comentarios);

    }

    @Override
    public void onLongItemClick(int position) {

    }

}