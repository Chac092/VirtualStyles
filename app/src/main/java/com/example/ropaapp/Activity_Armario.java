package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity_Armario extends AppCompatActivity {
    Button Camisa;
    final static int cons =0;
    int contador = 0;
    Bitmap bmp;
    Button Pantalones;
    Button Gorro;
    private Context TheThis;
    Button zapatos;
    Button subirFoto;
    Button consultarFotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);
        Camisa = findViewById(R.id.BTNOpion1);
        Camisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),Activity_verArmario.class);
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
        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarfoto();
            }
        });

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
            guardarFoto();
        }
    }
    public void guardarFoto(){
        //Guardaremos la foto con un titulo para mas tarde buscarla

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fotosArmario");
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Creado!");
        }
        File imagen = new File(dir, "Foto"+contador +".jpg");
        try {
            FileOutputStream fout = new FileOutputStream(imagen);
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fout);
            fout.flush();
            fout.close();
            if(imagen.exists()){
                System.out.println("EXISTE!");
            }else{
                System.out.println("NO EXISTE!");
            }
        }

        catch(FileNotFoundException e) {
            System.out.println(e);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        //MediaStore.Images.Media.insertImage(getContentResolver(),bmp,"Foto"+contador, "");
        contador = contador+1;

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

}
