package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_Armario2 extends AppCompatActivity {
    String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    List<String> item = new ArrayList<String>();
    Bitmap FTO;
    int Origen=0;
    String fotoAcojer;
    ImageView ImagenPrincipal;
    ImageView Imagen1;
    ImageView Imagen2;
    ImageView Imagen3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario2);
        ImagenPrincipal = findViewById(R.id.imageView);
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
         //List<String> item = new ArrayList<String>();
        //Defino la ruta donde busco los ficheros
        File f = new File(rutaConCarpeta);
        //Creo el array de tipo File con el contenido de la carpeta
        File[] files = f.listFiles();
        //Hacemos un Loop por cada fichero para extraer el nombre de cada uno
        for (int i = 0; i < files.length; i++)
        {
            //Sacamos del array files un fichero
            File file = files[i];
            //Si es directorio...
            if (file.isDirectory())
                item.add(file.getName() + "/");
                //Si es fichero...
            else
                item.add(file.getName());
        }
    }

    public void insertarImagenes(){
        fotoAcojer="Foto0.jpg";
        FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer);
        Imagen1.setImageBitmap(FTO);
        fotoAcojer="Foto1.jpg";
        FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer);
        Imagen2.setImageBitmap(FTO);
        fotoAcojer="Foto2.jpg";
        FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+fotoAcojer);
        Imagen3.setImageBitmap(FTO);
    }

    public void principalizarImagenes(){
        Bitmap foto;
        if (Origen==1){
            foto = ((BitmapDrawable)Imagen1.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }else if (Origen==2){
            foto = ((BitmapDrawable)Imagen1.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }else if(Origen==3){
            foto = ((BitmapDrawable)Imagen1.getDrawable()).getBitmap();
            ImagenPrincipal.setImageBitmap(foto);
        }
    }
}
