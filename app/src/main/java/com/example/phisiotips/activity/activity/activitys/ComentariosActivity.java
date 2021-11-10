package com.example.phisiotips.activity.activity.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.adpter.Adapter;
import com.example.phisiotips.activity.activity.adpter.AdapterComentarios;
import com.example.phisiotips.activity.activity.config.ConfiguracaoFireBase;
import com.example.phisiotips.activity.activity.helper.Base64Custon;
import com.example.phisiotips.activity.activity.model.Enquetes;
import com.example.phisiotips.activity.activity.model.MainEnqutes;
import com.example.phisiotips.activity.activity.model.Respostas;
import com.example.phisiotips.activity.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class ComentariosActivity extends AppCompatActivity {

    private RecyclerView recyclerComentarios;
    private List<Respostas> listaRespostas = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference comentarioReferenica;
    private TextView titulo, nome;
    private ImageButton buttonEnviar;
    private TextInputEditText textComentario;
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private Usuario usuario;
    private final FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private final DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebaseDataBase();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        recuperarUsuario();
        ids();


        //Pegando as chaves de cada item
        Bundle bundle = getIntent().getExtras();
        String chave = bundle.getString("Chave");
        String textTitulo = bundle.getString("Titulo");

        //Log.i("texte", "chave: " + chave);


        //Recupera Enquete

        usuarioRef = fireBaseRef.child("Enquetes").child(chave);

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Enquetes enquetes = snapshot.getValue(Enquetes.class);

                assert enquetes != null;
                nome.setText(enquetes.getAutor());
                //Log.i("teste","nome: " + nome);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Encaminhar para o nó do FireBase
        comentarioReferenica = FirebaseDatabase.getInstance().getReference().child("Enquetes").child(chave).child("Resposta");


        //Pegando Titulo da postagem
        titulo.setText(textTitulo);


        //Envia o comentario
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarComentario();
                textComentario.setText("");
                listaRespostas.clear();
            }
        });


        //recuperaResumo();
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
            respostas.setUsuario(usuario.getNome());

            comentarioReferenica.push().setValue(respostas);

            Toast.makeText(ComentariosActivity.this, "Resposta Enviada", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(ComentariosActivity.this, "Preencha o Campo", Toast.LENGTH_LONG).show();
        }

    }

    public void ids() {
        titulo = findViewById(R.id.titulo);
        buttonEnviar = findViewById(R.id.buttonEnvivarTeste);
        textComentario = findViewById(R.id.textComentarioTeste);
        nome = findViewById(R.id.textNomeUsuario);
    }


    private void recuperarUsuario(){

        String idUsuario = autenticacao.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(idUsuario);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
