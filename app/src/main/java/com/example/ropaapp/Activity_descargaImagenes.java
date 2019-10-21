package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Activity_descargaImagenes extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_descarga);

        imageView = findViewById(R.id.imageURL);

       Permisos();
        // Tenemos 20 prendas, primero las de adan en orden cabeza, pecho, piernas, pies y luego las de eva en el mismo orden
        String usuario = "adan";
        int categoria;
        boolean estado = true;
        boolean favorito = false;
        for (int i = 1; i <= 40; i++) {
            descargaImagen di = new descargaImagen();
            di.execute(i);

            Bitmap bitmap = di.getBitmap();
            imageView.setImageBitmap(bitmap);

            if (i > 20){usuario = "eva";}
            //Calculamos la categoria de la prenda (1 cabeza, 2 pecho, 3 piernas, 4 pies)
            categoria = 1+ ((i-1)/5);
            if (categoria > 4) {categoria -= 4;}
            //dejamos una de cada 5 prendas sin clasificar
            if (i%5 == 0) {estado = false;} else {estado = true;}
            //System.out.println(i + " : " + categoria + " : " + usuario + " : " + estado);

            //A la BD!
            String SQL_INSERT_PRENDA =
                    "INSERT INTO " + DBHelper.entidadPrenda.TABLE_NAME + " (" +
                            DBHelper.entidadPrenda._ID + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_ESTADO + ") " +
                            "VALUES (?, ?, ?, ?)";

            //TODO Insertar los datos en la BD
            //db.rawQuery(SQL_INSERT_PRENDA, new String[] {Integer.toString(i), usuario, Integer.toString(categoria), Boolean.toString(estado)});
        }
    }

    private void Permisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisocamara = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permisocamara != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }


    public class descargaImagen extends AsyncTask<Integer,Void,Void> {

        private Bitmap bitmap;

        @Override
        protected Void doInBackground(Integer... integers) {
            System.out.println("BACKGROUND!!!!!!!!!!!!!!!!");
            Integer i = integers[0];

            String stringurl = "https://github.com/Chac092/VirtualStyles/tree/master/fotosprueba/" + i + ".png";
            //String folderName = "/storage/emulated/0/saved_images/";
            int count;
            try {
                URL url = new URL(stringurl);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                //int lengthOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                bitmap = BitmapFactory.decodeStream(input);
                String fileName = i + ".png";
                //External directory path to save file
                String folder = Environment.getExternalStorageDirectory() + File.separator + "saved_images/";
                //Directory
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public Bitmap getBitmap(){return bitmap;}

    }

}
