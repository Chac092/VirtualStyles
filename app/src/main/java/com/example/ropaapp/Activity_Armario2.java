package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;

import com.example.ropaapp.DBHelper.entidadPrenda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_Armario2 extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    List<String> item = new ArrayList<String>();
    Bitmap FTO;
    String IDfoto;
    ArrayList<String> idfotos = new ArrayList<String>();
    int Origen=0;
    String fotoAcojer;
    ImageView ImagenPrincipal;
    ImageView Imagen1;
    ImageView Imagen2;
    ImageView Imagen3;
    String IDusuario;
    int pos = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        setContentView(R.layout.activity_armario2);
        ImagenPrincipal = findViewById(R.id.imageView);
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
            fotoAcojer= idfotos.get(pos);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer+".jpg");
            Imagen3.setImageBitmap(FTO);
        }
        if(idfotos.size()>=2){
            fotoAcojer=idfotos.get(pos-1);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer+".jpg");
            Imagen2.setImageBitmap(FTO);
        }
        if(idfotos.size()>=1){
            fotoAcojer=idfotos.get(pos-2);
            FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer+".jpg");
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
}
