package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.os.Bundle;
import android.widget.ImageView;

public class Activity_Crearconjunto extends AppCompatActivity {
    //String prendaReferencia;
    ImageView gorroConjunto;
    ImageView camisaConjunto;
    ImageView pantalonConjunto;
    ImageView zapatosConjunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearconjunto);
        //Intent intent = getIntent();
        //prendaReferencia=intent.getStringExtra("nombrePrenda");

        //Elementos graficos
        gorroConjunto = findViewById(R.id.gorroConjunto);
        camisaConjunto = findViewById(R.id.camisaConjunto);
        pantalonConjunto = findViewById(R.id.pantalonConjunto);
        zapatosConjunto = findViewById(R.id.zapatosConjunto);



    }


}
