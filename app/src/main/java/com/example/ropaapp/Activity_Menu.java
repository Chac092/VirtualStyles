package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Activity_Menu extends AppCompatActivity {
    Button Armario;
    Button Conjunto;
    Button Favoritos;
    String sUsuario;
    String sPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Creamos los conjuntos
        prendasEjemplo PE = new prendasEjemplo();
        PE.Rellenar_conjuntos("adan");
        //Cojemos los datos necesarios
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);
        //Aplicamos los listeneres
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
                intent.putExtra("Origen", "todo");
                startActivityForResult(intent, 0);
            }
        });
        Favoritos = findViewById(R.id.BotonMenu3);
        Favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Activity_Conjuntos.class);
                intent.putExtra("Origen", "favoritos");
                startActivityForResult(intent, 0);
            }
        });
    }
    //Menu
    public boolean onCreateOptionsMenu(Menu menu)  {
        if (sPerfil.equals("estilista")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.overflowadmin, menu);
        }else if(sPerfil.equals("usuario")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.overflow, menu);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menuItemnomina){
            Pdf pdf =  new Pdf();
            Context contexto = getBaseContext();
            pdf.savePdf(sUsuario,sPerfil,contexto);
        }else if (id == R.id.menuItem2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.menuItemfactura){
            Pdf pdf =  new Pdf();
            Context contexto = getBaseContext();
            pdf.savePdf(sUsuario,sPerfil,contexto);
        }
        return super.onOptionsItemSelected(item);
    }


}
