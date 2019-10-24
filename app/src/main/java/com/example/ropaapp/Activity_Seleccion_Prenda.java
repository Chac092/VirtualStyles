package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.ropaapp.DBHelper.entidadPrenda;
import java.util.ArrayList;


public class Activity_Seleccion_Prenda extends AppCompatActivity {
    static ArrayList<String> idfotos;
    DBHelper dbHelper;
    SQLiteDatabase db;

    //String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    //List<String> item = new ArrayList<String>();
    Bitmap FTO;
    String IDfoto;
    public static ArrayList<String> getIdfotos() {
        return idfotos;
    }

    String usuarioArmario;

    Adaptador adap;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prenda);
        recyclerView = findViewById(R.id.RecyclerView);
        //DB
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        //Intent
        Intent intent = getIntent();
        String categoria = intent.getStringExtra("categoria");
        //Shared preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        usuarioArmario = datos.getString("usuarioArmario",null);

        System.out.println("USUARIOARMARIO: " + usuarioArmario);

        //Acciones
        CojerImagenes(usuarioArmario, categoria, "1");

        adap = new Adaptador (feedData());
        adap.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView =findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(adap);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void CojerImagenes(String usuario, String categoria, String estado){
        System.out.println(usuario + " : " + categoria + " : " + estado);
        idfotos = new ArrayList<>();
       SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {entidadPrenda._ID};
        String selection = entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?" + " AND " + entidadPrenda.COLUMN_NAME_CATEGORIA + " = ?" + " AND " + entidadPrenda.COLUMN_NAME_ESTADO + " = ?";
        String[] selectionArgs = {usuario, categoria, estado};
        Cursor cursor = db.query(entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
           while(cursor.moveToNext()){
            IDfoto = cursor.getString(cursor.getColumnIndexOrThrow(entidadPrenda._ID));
            idfotos.add(IDfoto);
            //System.out.println(IDfoto);
        }
    }

    public ArrayList<String> feedData(){
        ArrayList<String> idFotos = new ArrayList<String>();
        idFotos.add("primero");
        idFotos.add("segundo");
        return idfotos;


    }

}
