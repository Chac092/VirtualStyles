package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Activity_descargaImagenes extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    String root = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_descarga);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();


        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        // Tenemos 20 prendas, primero las de adan en orden cabeza, pecho, piernas, pies y luego las de eva en el mismo orden
        String sUsuario = "adan";
        int categoria;
        boolean estado = true;
        boolean favorito = false;
        for (int i = 1; i <= 40; i++) {
            try {
                copiaImagen(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i > 20){sUsuario = "eva";}
            //Calculamos la categoria de la prenda (1 cabeza, 2 pecho, 3 piernas, 4 pies)
            categoria = 1+ ((i-1)/5);
            if (categoria > 4) {categoria -= 4;}
            //dejamos una de cada 5 prendas sin clasificar
            if (i%5 == 0) {estado = false;} else {estado = true;}
            //System.out.println(i + " : " + categoria + " : " + sUsuario + " : " + estado);

            //Insertar los datos en la BD
            String SQL_INSERT_PRENDA =
                    "INSERT INTO " + DBHelper.entidadPrenda.TABLE_NAME + " (" +
                            DBHelper.entidadPrenda._ID + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA + ", " +
                            DBHelper.entidadPrenda.COLUMN_NAME_ESTADO + ") " +
                            "VALUES (?, ?, ?, ?)";

            ContentValues values = new ContentValues();
            values.put(DBHelper.entidadPrenda._ID, i);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA,categoria);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO, estado);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_FAVORITO, false);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO,sUsuario);
            long newRowId = db.insert(DBHelper.entidadPrenda.TABLE_NAME, null, values);

        }
    }

    private void copiaImagen(int i) throws IOException {
        // the file to be moved or copied
        String fileName = "p"+ i;
        InputStream in = getResources().openRawResource(getResources().getIdentifier(fileName,"raw", getPackageName()));
        File targetLocation = new File(root + "/saved_images/" + i + ".jpg");
        OutputStream out = new FileOutputStream(targetLocation);
        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        //String TAG = "CopiaImagen " + i;
        //Log.v(TAG, "Copy file successful.");
    }
}
