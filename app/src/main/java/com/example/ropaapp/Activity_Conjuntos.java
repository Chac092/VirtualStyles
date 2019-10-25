package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Activity_Conjuntos extends AppCompatActivity {

    String sUsuario;
    ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
    int posicion = 0;
    TextView nombreConjunto;
    ImageView conjuntoPrenda1;
    ImageView conjuntoPrenda2;
    ImageView conjuntoPrenda3;
    ImageView conjuntoPrenda4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjuntos);
        //Intent
        Intent intent = getIntent();
        String prendaReferencia = intent.getStringExtra("nombreFoto");
        //Shared Preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        //Elementos de pantalla
        nombreConjunto = findViewById(R.id.nombreConjunto);
        conjuntoPrenda1 = findViewById(R.id.conjuntoPrenda1);
        conjuntoPrenda2 = findViewById(R.id.conjuntoPrenda2);
        conjuntoPrenda3 = findViewById(R.id.conjuntoPrenda3);
        conjuntoPrenda4 = findViewById(R.id.conjuntoPrenda4);

        //Seleccion y pintado
        conjuntos = seleccionarConjuntos(sUsuario, prendaReferencia);
        pintarConjunto(conjuntos, posicion);
    }


    private ArrayList<Conjunto> seleccionarConjuntos(String usuario, String prendaReferencia) {
        //TODO prendaReferencia
        Cursor cursor;
        DBHelper dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadConjunto._ID, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4, DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO};

        String selection = DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO + "= ?";
        String[] selectionArgs = {usuario};
        String sortOrder = DBHelper.entidadConjunto._ID+" DESC";
        cursor = db.query(DBHelper.entidadConjunto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();

        while(cursor.moveToNext()){
            int idConjunto = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto._ID));
            int idPrenda1 = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1));
            int idPrenda2 = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2));
            int idPrenda3 = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3));
            int idPrenda4 = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4));
            String idUsuario = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO));
            Conjunto conjunto = new Conjunto(idConjunto, idPrenda1, idPrenda2, idPrenda3, idPrenda4, idUsuario);
            conjuntos.add(conjunto);
        }

        return conjuntos;
    }

    private void pintarConjunto(ArrayList<Conjunto> conjuntos, int posicion){
        Conjunto conjunto = conjuntos.get(posicion);
        String nombre = "Conjunto: " + conjunto.getIdConjunto();
        nombreConjunto.setText(nombre);
        pintarFoto(conjuntoPrenda1, conjunto.getPrenda1());
        pintarFoto(conjuntoPrenda2, conjunto.getPrenda2());
        pintarFoto(conjuntoPrenda3, conjunto.getPrenda3());
        pintarFoto(conjuntoPrenda4, conjunto.getPrenda4());
    }

    private void pintarFoto(ImageView iv, int foto) {
        Bitmap bmp = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+foto+".jpg");
        iv.setImageBitmap(bmp);
    }

}
