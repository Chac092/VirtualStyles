package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Activity_CrearConjunto extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    //String prendaReferencia;
    ImageView gorroConjunto;
    ImageView camisaConjunto;
    ImageView pantalonConjunto;
    ImageView zapatosConjunto;
    String usuarioArmario;
    static ArrayList<String> misPrendas;
    View.OnClickListener listenerAdaptador;
    String categoria;

    Adapter_CrearConjunto miAdaptador;
    private RecyclerView rvPrendas;
    private RecyclerView.LayoutManager layoutManager;

    public static ArrayList<String> getMisPrendas() {
        return misPrendas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearconjunto);
        //DB
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        //Intent
        Intent intent = getIntent();
        categoria = intent.getStringExtra("categoria");
        if (categoria == null) {categoria = "1";}
        //Shared preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        usuarioArmario = datos.getString("usuarioArmario",null);

        //Elementos graficos
        gorroConjunto = findViewById(R.id.gorroConjunto);
        camisaConjunto = findViewById(R.id.camisaConjunto);
        pantalonConjunto = findViewById(R.id.pantalonConjunto);
        zapatosConjunto = findViewById(R.id.zapatosConjunto);

        //gorroConjunto.setClickable(true);
        gorroConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "1";
                refrescar();
            }
        });
        camisaConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "2";
                refrescar();
            }
        });
        pantalonConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "3";
                refrescar();
            }
        });
        zapatosConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "4";
                refrescar();
            }
        });

        //Acciones
        listenerAdaptador = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = rvPrendas.getChildAdapterPosition(v);
                String idFoto = misPrendas.get(posicion);
                String path = "/storage/emulated/0/saved_images/" + idFoto + ".jpg";
                Bitmap miFoto = BitmapFactory.decodeFile(path);
                if (categoria.equals("1")){
                    gorroConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("2")){
                    camisaConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("3")){
                    pantalonConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("4")){
                    zapatosConjunto.setImageBitmap(miFoto);
                }
            }
        };
        //
        //CARGUEMOS EL LISTENER
        misPrendas = listarPrendas(usuarioArmario, categoria, "1");
        miAdaptador = new Adapter_CrearConjunto (misPrendas);
        miAdaptador.setOnItemClickListener(listenerAdaptador);

        rvPrendas =findViewById(R.id.rvPrendas);
        rvPrendas.setAdapter(miAdaptador);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPrendas.setLayoutManager(layoutManager);

    }




    //TODO Centralizar
    private ArrayList<String> listarPrendas(String usuario, String categoria, String estado){
        //System.out.println(usuario + " : " + categoria + " : " + estado);
        ArrayList<String> prendas = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadPrenda._ID};
        String selection = DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?" + " AND " + DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA + " = ?" + " AND " + DBHelper.entidadPrenda.COLUMN_NAME_ESTADO + " = ?";
        String[] selectionArgs = {usuario, categoria, estado};
        Cursor cursor = db.query(DBHelper.entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        while(cursor.moveToNext()){
            prendas.add(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID)));
        }
        return prendas;
    }

    private void refrescar(){
        misPrendas = listarPrendas(usuarioArmario, categoria, "1");
        miAdaptador = new Adapter_CrearConjunto (misPrendas);
        miAdaptador.setOnItemClickListener(listenerAdaptador);
        rvPrendas.setAdapter(miAdaptador);
    }

}
