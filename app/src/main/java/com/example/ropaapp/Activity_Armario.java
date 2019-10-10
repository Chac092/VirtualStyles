package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_Armario extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button Camisa;
    final static int cons =0;
    Bitmap bmp;
    Button Pantalones;
    Button Gorro;
    String IDusuario;
    Button zapatos;
    Button subirFoto;
    Button consultarFotos;
    String Perfil = "Estilista";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        //Cojeremos el id del usuario logueado
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        IDusuario = datos.getString("Id_usu",null);
        System.out.println("Nombre = "+ IDusuario);
        //Uniremos todos los elementos con sus ids
        Camisa = findViewById(R.id.BTNOpion1);
        Camisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),Activity_Armario2.class);
                startActivityForResult(intent, 0);
            }
        });
        Gorro = findViewById(R.id.BTNOpion2);
        Pantalones = findViewById(R.id.BTNOpion3);
        zapatos = findViewById(R.id.BTNOpion4);
        subirFoto = findViewById(R.id.BTNSubirFoto);
        consultarFotos = findViewById(R.id.BTNPendientes);
        checkwrittePermission();
        checkCameraPermission();
        checkreadePermission();
        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sacarfoto();
            }
        });
        if (Perfil.equals("Estilista")) {
            subirFoto.setCursorVisible(false);
        }
    }
    //Aqui comprobaremos los permisos de la camara y si no los tiene los pediremos
    private void checkCameraPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    //Este metodo lo usaremos para sacar la foto
    public void sacarfoto(){
        //Mediante un intente llamaremos a la camara para sacar una foto
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,cons);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Una vez sacada esa foto vamos a cojerla del intent y la guardaremos en forma de bitmap
            Bundle ext = data.getExtras();
            bmp = (Bitmap) ext.get("data");
            System.out.println("exito");
            //guardarFoto();
            saveTempBitmap(bmp);
        }
    }
      //Aqui comprobaremos si tenemos los permisos de escritura y si no lo tenemos los pediremos
    private void checkwrittePermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    private void checkreadePermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO, false);
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_FAVORITO,0);
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO,IDusuario);
        long newRowId = db.insert(DBHelper.entidadPrenda.TABLE_NAME, null, values);
        String IDfoto = String.valueOf(newRowId);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = IDfoto +".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
