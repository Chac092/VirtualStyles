package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Button botonConjuntos;
    ArrayList<String> nombreusu = new ArrayList<>();
    Spinner Usuarios;
    String seleccionado ="";
    String sUsuario;
    String sPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);


        setContentView(R.layout.activity_menuy_selecciondeclientes);
        Usuarios = findViewById(R.id.SeleccionUsario);
        CojerClientes();
        Usuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionado = Usuarios.getSelectedItem().toString();
                //System.out.println("SELECCIONADO: " + seleccionado);
                final String MY_PREFS_NAME = "File";
                SharedPreferences.Editor datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                datos.putString("usuarioArmario", seleccionado);
                datos.apply();
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
                startActivity(intent);
            }
        });


        botonConjuntos = findViewById(R.id.botonConjuntos);
        botonConjuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Activity_Conjuntos.class);
                intent.putExtra("Origen", "todo");
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
            //System.out.println(nombre);
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, nombreusu);
        Usuarios.setAdapter(adaptador);
    }
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
