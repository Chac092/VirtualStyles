package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;

public class Activity_rellenarPrendas extends AppCompatActivity {
    //Atributtos
    String [] elecciones={"1","2","3","4"};
    String usuario;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ImageView Fotoprincipal;
    String Seleccion;
    Button Borrar;
    String nombre;
    String sUsuario;
    String sPerfil;
    ImageView gorro;
    ImageView camiseta;
    ImageView pantalon;
    ImageView zapatillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Base de datos
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rellenar_prendas);
        //Los listeners
        gorro = findViewById(R.id.BTNasignarGorro);
        gorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion = "1";
                Upadate();
                refrescar();
            }
        });
        camiseta = findViewById(R.id.BTNasignarCamiseta);
        camiseta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion = "2";
                Upadate();
                refrescar();
            }
        });
        pantalon = findViewById(R.id.BTNasignarPantalon);
        pantalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion = "3";
                Upadate();
                refrescar();
            }
        });
        zapatillas = findViewById(R.id.BTNasignarZapatillas);
        zapatillas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion = "4";
                Upadate();
                refrescar();
            }
        });
        Borrar = findViewById(R.id.BTNBorrar);
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
                refrescar();
            }
        });
        Fotoprincipal = findViewById(R.id.FotoPrinc);
        ArrayAdapter adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, elecciones);
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Intent intent = getIntent();
        sUsuario = intent.getStringExtra("NombreUsuario");
        sPerfil = datos.getString("sPerfil","");
        System.out.println(sUsuario);
        refrescar();
    }
    //Refrescar las prendas que se muestran
    public void refrescar(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadPrenda._ID};
        String selection = DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO+"= ?"+" AND "+DBHelper.entidadPrenda.COLUMN_NAME_ESTADO+"= ?";
        String[] selectionArgs = {sUsuario, "0"};
        String sortOrder = DBHelper.entidadPrenda._ID+" DESC";
        Cursor cursor = db.query(DBHelper.entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        System.out.println(cursor.getCount());
        while(cursor.moveToNext()){
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            System.out.println(nombre);
            Bitmap FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+nombre+".jpg");
            System.out.println(FTO);
            Fotoprincipal.setImageBitmap(FTO);
        }
        if (cursor.getCount() == 0) {
            Intent intent = new Intent(getApplicationContext(), Activity_Armario.class);
            startActivity(intent);
        }
    }
    //Para rellenar las prendas sin clasificar
    public void Upadate(){
        ContentValues values = new ContentValues();
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO,1);
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA,Seleccion);
        String selection = DBHelper.entidadPrenda._ID+ " LIKE ?";
        String [] selectionArgs = {nombre};
        int count = db.update(DBHelper.entidadPrenda.TABLE_NAME,values,selection,selectionArgs);
        System.out.println(count);
    }
    //Para borrar las prendas de la base de datos y de el telefono
    public void Delete(){
        String selection = DBHelper.entidadPrenda._ID + " LIKE ?";
        String [] selectionArgs = {nombre};
        int deletedRows = db.delete(DBHelper.entidadPrenda.TABLE_NAME,selection,selectionArgs);


        File fdelete = new File("/storage/emulated/0/saved_images/"+nombre+".jpg");
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + "/storage/emulated/0/saved_images/"+nombre+".jpg");
            } else {
                System.out.println("file not Deleted :" + "/storage/emulated/0/saved_images/"+nombre+".jpg");
            }
        }
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
