package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Activity_rellenarPrendas extends AppCompatActivity {
    Spinner opciones;
    String [] elecciones={"1","2","3","4"};
    String usuario;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ImageView Fotoprincipal;
    String Seleccion;
    Button Aceptar;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rellenar_prendas);
        Aceptar = findViewById(R.id.BTNAceptar);
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upadate();
                refrescar();
            }
        });
        Fotoprincipal = findViewById(R.id.FotoPrinc);
        opciones = findViewById(R.id.listaCat);
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, elecciones);
        opciones.setAdapter(adaptador);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("Id_usu");
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Seleccion  = opciones.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
         refrescar();
    }

    public void refrescar(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadPrenda._ID};
        String selection = DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO+"= ?"+" AND "+DBHelper.entidadPrenda.COLUMN_NAME_ESTADO+"= ?";
        String[] selectionArgs = {usuario, String.valueOf(0)};
        String sortOrder = DBHelper.entidadPrenda._ID+" DESC";
        Cursor cursor = db.query(DBHelper.entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        while(cursor.moveToNext()){
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            System.out.println(nombre);
            Bitmap FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+nombre+".jpg");
            System.out.println(FTO);
            Fotoprincipal.setImageBitmap(FTO);
        }
    }

    public void Upadate(){
        ContentValues values = new ContentValues();
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO,1);
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA,Seleccion);
        String selection = DBHelper.entidadPrenda._ID+ " LIKE ?";
        String [] selectionArgs = {nombre};
        int count = db.update(DBHelper.entidadPrenda.TABLE_NAME,values,selection,selectionArgs);
        System.out.println(count);
    }
    public void Delete(){

    }

}
