package com.example.ropaapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class prendasEjemplo extends AppCompatActivity {

    Context context = GlobalApplication.getAppContext();
    ArrayList<Conjunto> Conjuntos = new ArrayList<>();//TODO temporal
    ArrayList<ArrayList> TodosArrays = new ArrayList<>();
    ArrayList<String> gorros = new ArrayList<>();//TODO temporal
    ArrayList<String> camisa = new ArrayList<>();//TODO temporal
    ArrayList<String> pantalon = new ArrayList<>();//TODO temporal
    ArrayList<String> zapatillas = new ArrayList<>();//TODO temporal
    DBHelper dbHelper;
    SQLiteDatabase db;

    public void Rellenar_conjuntos(String usuario){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        TodosArrays.add(gorros);
        TodosArrays.add(camisa);
        TodosArrays.add(pantalon);
        TodosArrays.add(zapatillas);

        for (int i=1;TodosArrays.size()>=i;i++){
            String[] projectiongorros = {DBHelper.entidadPrenda._ID};
            // String sortOrdergorros = DBHelper.entidadPrenda._ID + " DESC";
            String selectiongorros = DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA + " = ? AND "+DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?";
            String[] selectionArgsgorros = {""+i+"",usuario};
            Cursor cursorgorros = db.query(DBHelper.entidadPrenda.TABLE_NAME, projectiongorros, selectiongorros, selectionArgsgorros, null, null, null);
            while (cursorgorros.moveToNext()) {
                String nombre = cursorgorros.getString(cursorgorros.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
                TodosArrays.get(i-1).add(nombre);
                System.out.println("Entro = "+i);
            }
        }

        int id = 0;
        int prenda1 = 0;
        int prenda2 = 0;
        int prenda3 = 0;
        int prenda4 = 0;
        String idUsuario = usuario;


        for (int p1 = 0;p1<gorros.size();p1++){
            prenda1 = Integer.parseInt(gorros.get(p1));
            for (int p2 = 0; p2 <camisa.size();p2++){
                prenda2 = Integer.parseInt(gorros.get(p2));
                for (int p3=0;p3<pantalon.size();p3++){
                    prenda3 = Integer.parseInt(pantalon.get(p3));
                    for (int p4 = 0;p4<zapatillas.size();p4++){
                        prenda4 = Integer.parseInt(zapatillas.get(p4));
                        //Conjunto nuevo = new Conjunto(id,prenda1,prenda2,prenda3,prenda4,idUsuario);
                        Conjunto nuevo = new Conjunto(id,prenda1,prenda2,prenda3,prenda4,idUsuario);
                        Conjuntos.add(nuevo);
                        System.out.println(Conjuntos.size());
                        id = id +1;

                    }
                }
            }
        }
        for (int k = 0; k>Conjuntos.size();k++){
            String SQL_INSERT_USUARIO =
                    "INSERT INTO " + DBHelper.entidadConjunto.TABLE_NAME + " (" +
                            DBHelper.entidadConjunto._ID + ", " +
                            DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1 + ", " +
                            DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2 + ", " +
                            DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3 + ", " +
                            DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4 + ", " +
                            DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO + ") " +

                            "VALUES ('" + Conjuntos.get(k).getIdConjunto() + "', '" + Conjuntos.get(k).getPrenda1() + "', '" + Conjuntos.get(k).getPrenda2() + "', '" + Conjuntos.get(k).getPrenda3() + "', '" + Conjuntos.get(k).getPrenda4()
                            + "', '" + Conjuntos.get(k).getIdUsuario()+"')";
            db.execSQL(SQL_INSERT_USUARIO);
        }
    }

    public void copiaImagen(int i) throws IOException {


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        // the file to be moved or copied
        String fileName = "p"+ i;

        InputStream in = context.getResources().openRawResource(context.getResources().getIdentifier(fileName,"raw", context.getPackageName()));
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
        String TAG = "CopiaImagen " + i;
        Log.v(TAG, "Copy file successful.");
    }

    private void Permisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisocamara = ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permisocamara != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
}
