package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.net.Inet4Address;

public class Activity_Conjuntos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjuntos);
        Intent intent = getIntent();
        String Foto = intent.getStringExtra("nombreFoto");
    }
    public void Crearconjuntos(){

    }
}
