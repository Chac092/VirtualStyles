package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ropaapp.DBHelper.entidadPrenda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_Armario2 extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button siguiente;
    Button anterior;
    String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    List<String> item = new ArrayList<String>();
    Bitmap FTO;
    String IDfoto;
    ArrayList<String> idfotos = new ArrayList<String>();
    int Origen=0;
    String fotoAcojer1;
    String fotoAcojer2;
    String fotoAcojer3;
    ImageView ImagenPrincipal;
    ImageView Imagen1;
    ImageView Imagen2;
    ImageView Imagen3;
    String IDusuario;
    int pos = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario2);
        anterior = findViewById(R.id.BTNAnterior);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTNAtnerior();
            }
        });
        siguiente = findViewById(R.id.BTNsiguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTNsiguiente();
            }
        });
        ImagenPrincipal = findViewById(R.id.imageView);
        ImagenPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion();
            }
        });
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        IDusuario = datos.getString("Id_usu",null);
        Imagen1 = findViewById(R.id.imageView2);
        Imagen2 = findViewById(R.id.imageView3);
        Imagen3 = findViewById(R.id.imageView4);
        CojerImagenes();
        insertarImagenes();
        Imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Origen=1;
                principalizarImagenes();
            }
        });
        Imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Origen=2;
                principalizarImagenes();
            }
        });
        Imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Origen = 3;
                principalizarImagenes();
            }
        });
    }
    public void CojerImagenes(){
       SQLiteDatabase db = dbHelper.getReadableDatabase();
    String[] projection = {entidadPrenda._ID};
    String selection = entidadPrenda.COLUMN_NAME_IDUSUARIO+"= ?";
    String[] selectionArgs = {IDusuario};
    String sortOrder = entidadPrenda.COLUMN_NAME_IDUSUARIO+" DESC";
    Cursor cursor = db.query(entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
       while(cursor.moveToNext()){
        IDfoto = cursor.getString(cursor.getColumnIndexOrThrow(entidadPrenda._ID));
        idfotos.add(IDfoto);
        //System.out.println(IDfoto);
    }
}

    public void insertarImagenes(){
        System.out.println("TAMAÑO ARRAY= " +idfotos.size());
        if(idfotos.size()>=3){
            fotoAcojer3= idfotos.get(pos);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer3+".jpg");
            Imagen3.setImageBitmap(FTO);
        }
        if(idfotos.size()>=2){
            fotoAcojer2=idfotos.get(pos-1);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer2+".jpg");
            Imagen2.setImageBitmap(FTO);
        }
        if(idfotos.size()>=1){
            fotoAcojer1=idfotos.get(pos-2);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer1+".jpg");
            Imagen1.setImageBitmap(FTO);
        }

    }

    public void principalizarImagenes(){
        Bitmap foto;
        if (Origen==1){
            foto = ((BitmapDrawable)Imagen1.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }else if (Origen==2){
            foto = ((BitmapDrawable)Imagen2.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }else if(Origen==3){
            foto = ((BitmapDrawable)Imagen3.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }
    }

    public void BTNsiguiente(){
        if(pos < idfotos.size()-1){
            pos = pos +3;
        }else{
            Toast toast2 = Toast.makeText(getApplicationContext(), "No cuentas con mas fotos ", Toast.LENGTH_SHORT);
            toast2.setGravity(Gravity.CENTER,0,0);
            toast2.show();
        }
        if (idfotos.size()-1>=pos){
            fotoAcojer1= idfotos.get(pos);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer1+".jpg");
            Imagen1.setImageBitmap(FTO);
        }else{
            Imagen1.setBackgroundColor(getResources().getColor(R.color.ColordeFondo));
        }
        if(idfotos.size()-1>=pos-1){
            fotoAcojer2= idfotos.get(pos-1);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer2+".jpg");
            Imagen2.setImageBitmap(FTO);
        }else{
            Imagen2.setBackgroundColor(getResources().getColor(R.color.ColordeFondo));
            System.out.println("entro");
        }
        if (idfotos.size()-1>=pos-2){
            fotoAcojer3= idfotos.get(pos-2);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer3+".jpg");
            Imagen3.setImageBitmap(FTO);
        }
    }

    public void BTNAtnerior(){
        if(pos > 2){
            pos = pos-3;
        }else{
            Toast toast2 = Toast.makeText(getApplicationContext(), "No cuentas con mas fotos ", Toast.LENGTH_SHORT);
            toast2.setGravity(Gravity.CENTER,0,0);
            toast2.show();
        }
        if (idfotos.size()-1>=pos){
            fotoAcojer1= idfotos.get(pos);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer1+".jpg");
            Imagen1.setImageBitmap(FTO);
        }
        if(idfotos.size()-1>=pos-1){
            fotoAcojer2= idfotos.get(pos-1);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer2+".jpg");
            Imagen2.setImageBitmap(FTO);
        }
        if (idfotos.size()-1>=pos-2){
            fotoAcojer3= idfotos.get(pos-2);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer3+".jpg");
            Imagen3.setImageBitmap(FTO);
        }
    }

    public void Seleccion(){
        String Foto="";
        if(Origen == 1){
            Foto = fotoAcojer1;
        }else if(Origen==2){
            Foto = fotoAcojer2;
        }else if(Origen ==3){
            Foto = fotoAcojer3;
        }
        Intent intent = new Intent(this,Activity_Conjuntos.class);
        intent.putExtra("nombreFoto",Foto);
        startActivity(intent);
    }
}
