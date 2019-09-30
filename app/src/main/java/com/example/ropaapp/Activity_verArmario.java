package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class Activity_verArmario extends AppCompatActivity {
    ImageView fotillo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_armario);
        fotillo = findViewById(R.id.Fotoprenda);
        MediaStore.Images.Media.getContentUri("Foto0");
        Bitmap bmp =  MediaStore.Images.Media.getBitmap(getContentResolver(),);
        System.out.println("verarmario exitioso");
        fotillo.setBackground();
    }
}
