package com.example.phisiotips.activity.activity.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.phisiotips.R;
import com.example.phisiotips.activity.activity.sobre.SobreFragment;

public class SobreActivity extends AppCompatActivity {

    private SobreFragment sobreFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        sobreFragment = new SobreFragment();

        //Configurar exibição
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameSobre, sobreFragment);
        transaction.commit();

    }

}

