package com.example.ropaapp;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Path;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Messenger;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.ArrayList;
        import java.util.List;

        import static java.security.AccessController.getContext;

public class Activity_verArmario extends AppCompatActivity {
    ImageView fotillo;
    private static final int RQS_OPEN_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_armario);
        fotillo = findViewById(R.id.Fotoprenda);
        checkDocumentPermission();
        checkreadPermission();
        Cojerdato();
    }
    //Este metodo lo usaremos para cojer las fotos
    public void Cojerdato(){
        Bitmap pepe = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/Shutta_20191004_102344.jpg");
        //ponemos esa foto en el ImagenView fotillo
        fotillo.setImageBitmap(pepe);
    }
    //Este metodo sirve para dar permisos de manejar documentos en la app
    private void checkDocumentPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.MANAGE_DOCUMENTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    //Aqui pediremos los permisos de lectura
    private void checkreadPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
}




