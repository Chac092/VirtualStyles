package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Activity_Crearconjunto extends AppCompatActivity {
    String conjuntoReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearconjunto);
        Intent intent = getIntent();
        conjuntoReferencia=intent.getStringExtra("nombrePrenda");


    }
}
