package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Menu extends AppCompatActivity {
    Button Armario;
    Button Conjunto;
    Button Favoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Armario = findViewById(R.id.Botonmenu1);
        Armario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Activity_Armario.class);
                startActivityForResult(intent, 0);
            }
        });
        Conjunto = findViewById(R.id.BotonMenu2);
        Conjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Activity_Conjuntos.class);
                startActivityForResult(intent, 0);
            }
        });
        Favoritos = findViewById(R.id.BotonMenu3);
        Favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Activity_Conjuntos.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
