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
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ropaapp.DBHelper.entidadPrenda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Seleccion_Prenda extends AppCompatActivity {
    static ArrayList<String> idfotos = new ArrayList<String>();
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button siguiente;
    Button anterior;

    //String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    //List<String> item = new ArrayList<String>();
    Bitmap FTO;
    String IDfoto;
    public static ArrayList<String> getIdfotos() {
        return idfotos;
    }
    int Origen=0;
    String fotoAcojer1;
    String fotoAcojer2;
    String fotoAcojer3;
    ImageView ImagenPrincipal;
    ImageView Imagen1;
    ImageView Imagen2;
    ImageView Imagen3;
    String sUsuario;
    int pos = 2;
    Adaptador adap;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prenda);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        //DB
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        //Shared preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        //Elementos layout
        //anterior = findViewById(R.id.);
        //siguiente = findViewById(R.id.);
        //ImagenPrincipal = findViewById(R.id.imageView);
        //Acciones
        //CojerImagenes();
        //insertarImagenes();
        //pruebas reclicler view
        adap = new Adaptador (feedData());
        adap.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(recyclerView.getChildAdapterPosition(v));
            }
        });
        recyclerView =findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(adap);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void CojerImagenes(){
       SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {entidadPrenda._ID};
        String selection = entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?";
        String[] selectionArgs = {sUsuario};

        //String sortOrder = entidadPrenda.COLUMN_NAME_IDUSUARIO +" DESC";
        Cursor cursor = db.query(entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
           while(cursor.moveToNext()){
            IDfoto = cursor.getString(cursor.getColumnIndexOrThrow(entidadPrenda._ID));
            idfotos.add(IDfoto);
            //System.out.println(IDfoto);
        }
    }

    public void insertarImagenes(){
        //System.out.println("TAMAÑO ARRAY= " + idfotos.size());
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
            Imagen1.setBackgroundColor(getResources().getColor(R.color.Color3));
        }
        if(idfotos.size()-1>=pos-1){
            fotoAcojer2= idfotos.get(pos-1);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer2+".jpg");
            Imagen2.setImageBitmap(FTO);
        }else{
            Imagen2.setBackgroundColor(getResources().getColor(R.color.Color3));
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

    public void ReciclarView(){
        adap = new Adaptador(feedData());
        recyclerView =(RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(adap);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public ArrayList<String> feedData(){
        ArrayList<String> idFotos = new ArrayList<String>();
        idFotos.add("primero");
        idFotos.add("segundo");
        return idfotos;


    }

}
