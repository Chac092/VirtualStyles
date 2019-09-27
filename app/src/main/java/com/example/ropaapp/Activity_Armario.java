package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Activity_Armario extends AppCompatActivity {
    Button Camisa;
    Button Pantalones;
    Button Gorro;
    Button zapatos;
    Button subirFoto;
    Button consultarFotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);
        Camisa = findViewById(R.id.BTNOpion1);
        Gorro = findViewById(R.id.BTNOpion2);
        Pantalones = findViewById(R.id.BTNOpion3);
        zapatos = findViewById(R.id.BTNOpion4);
        subirFoto = findViewById(R.id.BTNSubirFoto);
        consultarFotos = findViewById(R.id.BTNPendientes);
    }
}
