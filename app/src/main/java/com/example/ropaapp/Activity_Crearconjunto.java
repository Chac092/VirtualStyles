package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Activity_Crearconjunto extends AppCompatActivity {
    String conjuntoReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__crearconjunti);
        Intent intent = getIntent();
        conjuntoReferencia=intent.getStringExtra("nombrePrenda");


    }
}
