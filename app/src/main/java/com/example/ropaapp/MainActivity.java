package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //variables to manage de database
    DBHelper dbHelper;
    SQLiteDatabase db;

    Button Entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Entrar = findViewById(R.id.botonEntrar);
        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Activity_Menu.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
