package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //variables to manage de database
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button botonEntrar;
    Button botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonRegistro = findViewById(R.id.botonRegistro);
        botonEntrar = findViewById(R.id.botonEntrar);
        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String MY_PREFS_NAME = "File";
                SharedPreferences.Editor datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                datos.putString("Id_usu", "Pepe");
                datos.putString("Perfil", "usuario");
                datos.apply();
                String Perfil = "Estilista";
                if (Perfil.equals("Estilista")) {
                    Intent intent = new Intent(v.getContext(), Activity_MenuySelecciondeclientes.class);
                    startActivity(intent);
                }
                if (Perfil.equals("usuario")) {
                    Intent intent = new Intent(v.getContext(), Activity_Menu.class);
                    startActivity(intent);
                }
            }
            });

        botonRegistro.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                Intent intent = new Intent(v.getContext(), Activity_Registro.class);
                startActivity(intent);
            }
            });
        }
}




