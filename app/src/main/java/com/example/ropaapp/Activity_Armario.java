package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class Activity_Armario extends AppCompatActivity {
    Button Camisa;
    final static int cons =0;
    int contador = 0;
    Bitmap bmp;
    Button Pantalones;
    Button Gorro;
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
    public void sacarfoto(){
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,cons);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            bmp = (Bitmap) ext.get("data");
            System.out.println("exito");
            guardarFoto();
        }
    }
    public void guardarFoto(){
        MediaStore.Images.Media.insertImage(getContentResolver(),bmp,"Foto"+contador, "");
        contador = contador+1;

    }
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
