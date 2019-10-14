package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Activity_MenuySelecciondeclientes extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button Armario;
    ArrayList<String> nombreusu = new ArrayList<>();
    Spinner Usuarios;
    String seleccionado ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuy_selecciondeclientes);
        Usuarios = findViewById(R.id.SeleccionUsario);
        CojerClientes();
        Usuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionado = Usuarios.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Armario = findViewById(R.id.BTNArmaEstilista);
        Armario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Activity_Armario.class);
                intent.putExtra("NombreUsuario",seleccionado);
                startActivity(intent);
            }
        });
    }


    public void CojerClientes(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadUsuario._ID};
        String selection = DBHelper.entidadUsuario.COLUMN_NAME_PERFIL+"= ?";
        String[] selectionArgs = {"usuario"};
        String sortOrder = DBHelper.entidadUsuario._ID+" DESC";
        Cursor cursor = db.query(DBHelper.entidadUsuario.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        while(cursor.moveToNext()){
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            nombreusu.add(nombre);
            System.out.println(nombre);
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, nombreusu);
        Usuarios.setAdapter(adaptador);
    }

}
