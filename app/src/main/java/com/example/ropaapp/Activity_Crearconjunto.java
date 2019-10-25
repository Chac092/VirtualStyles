package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.os.Bundle;

public class Activity_Crearconjunto extends AppCompatActivity {
    String prendaReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearconjunto);
        Intent intent = getIntent();
        prendaReferencia=intent.getStringExtra("nombrePrenda");

    }


}
