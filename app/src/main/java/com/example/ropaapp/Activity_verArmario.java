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
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Messenger;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.FileNotFoundException;

        import static java.security.AccessController.getContext;

public class Activity_verArmario extends AppCompatActivity {
    ImageView fotillo;
    Uri targetUri = Uri.parse("content://com.android.providers.media.documents/document/image%A43");
    private static final int RQS_OPEN_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_armario);
        fotillo = findViewById(R.id.Fotoprenda);
        checkDocumentPermission();
        checkreadPermission();
        Cojerdato();
        //path = Uri.parse("content://com.android.providers.media.documents/document/image%A43");
    }

    public void Cojerdato(){
        Bitmap imagen = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/1570012838183.jpg");
        //Drawable imagen = getDrawable("/storage/emulated/0/Pictures/1570012838183.jpg");
        fotillo.setImageBitmap(imagen);
    }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Cojerdato();
        }
    }
    }




