package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Activity_Conjuntos extends AppCompatActivity {


    String sUsuario;
    String sPerfil;
    String usuarioArmario;

    ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
    int posicion = 0;
    TextView nombreConjunto;
    ImageView conjuntoPrenda1;
    ImageView conjuntoPrenda2;
    ImageView conjuntoPrenda3;
    ImageView conjuntoPrenda4;
    Button siguienteConjunto;
    Button anteriorConjunto;
    FloatingActionButton botonFavYNuevo;
    int numeroConjuntos = 0;
    SQLiteDatabase db;
    DBHelper dbHelper;
    String prendaReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjuntos);
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getApplicationContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        //Intent
        Intent intent = getIntent();
        String Origen = intent.getStringExtra("Origen");
        prendaReferencia = intent.getStringExtra("nombrefoto");
        //Shared Preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);
        usuarioArmario = datos.getString("usuarioArmario",null);


        //Elementos de pantalla
        nombreConjunto = findViewById(R.id.nombreConjunto);
        conjuntoPrenda1 = findViewById(R.id.conjuntoPrenda1);
        conjuntoPrenda2 = findViewById(R.id.conjuntoPrenda2);
        conjuntoPrenda3 = findViewById(R.id.conjuntoPrenda3);
        conjuntoPrenda4 = findViewById(R.id.conjuntoPrenda4);
        siguienteConjunto = findViewById(R.id.siguienteConjunto);
        anteriorConjunto = findViewById(R.id.anteriorConjunto);
        botonFavYNuevo = findViewById(R.id.botoFavorito);
        if (sPerfil.equals("usuario")) {
            botonFavYNuevo.setImageDrawable(getDrawable(R.drawable.fav));
            System.out.println("Entro usuario");
        }else if(sPerfil.equals("estilista")){
            botonFavYNuevo.setImageDrawable(getDrawable(R.drawable.mas));
            System.out.println("Entro estilista");
        }
        //Seleccion y pintado
        if (Origen.equals("todo")){
            conjuntos = seleccionarConjuntos(usuarioArmario);
            pintarConjunto(conjuntos, posicion);
        }else if(Origen.equals("armario")){
            System.out.println(prendaReferencia);
            conjuntos = seleccionarConjuntosConPrenda(sUsuario,prendaReferencia);
            pintarConjunto(conjuntos, posicion);
        }else if(Origen.equals("favoritos")){
            conjuntos = seleccionarConjuntosFavoritos(sUsuario);
            pintarConjunto(conjuntos, posicion);
        }


        //Listeners
        botonFavYNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sPerfil.equals("usuario")) {
                    añadirFav();
                    CharSequence text ="Añadido a favorito";
                    Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
                    toast.show();
                }else if(sPerfil.equals("estilista")){
                    Intent intent = new Intent(getApplicationContext(), Activity_Crearconjunto.class);
                    startActivity(intent);
                }
            }
        });

        siguienteConjunto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(posicion>0) {
                            posicion--;
                            pintarConjunto(conjuntos, posicion);
                        }
                    }
                }
        );
        anteriorConjunto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(posicion<conjuntos.size()-1){
                                posicion++;
                                pintarConjunto(conjuntos, posicion);
                        }
                    }
                }
        );
    }


    private ArrayList<Conjunto> seleccionarConjuntos(String usuario) {
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
        if (conjuntos.size()-1>0){
        Conjunto conjunto = conjuntos.get(posicion);
        String nombre = "Conjunto: " + conjunto.getIdConjunto();
        nombreConjunto.setText(nombre);
        pintarFoto(conjuntoPrenda1, conjunto.getPrenda1());
        pintarFoto(conjuntoPrenda2, conjunto.getPrenda2());
        pintarFoto(conjuntoPrenda3, conjunto.getPrenda3());
        pintarFoto(conjuntoPrenda4, conjunto.getPrenda4());
    }else{
            if(sPerfil.equals("usuario")){
                CharSequence text ="No tienes conjuntos se te dirijira a la pantalla de menu";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Activity_Menu.class);
                startActivity(intent);
            }else if(sPerfil.equals("estilista")){
                CharSequence text ="No tiene conjunto se te enviara a la pantalla crearconjuntos";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Activity_Crearconjunto.class);
                startActivity(intent);
            }
        }
    }

    private void pintarFoto(ImageView iv, int foto) {
        Bitmap bmp = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+foto+".jpg");
        iv.setImageBitmap(bmp);
    }

    public void añadirFav(){
        Conjunto conjunto = conjuntos.get(posicion);
        String IdConjunto;
        IdConjunto = String.valueOf(conjunto.getIdConjunto());
        ContentValues valuesfav = new ContentValues();
        valuesfav.put(DBHelper.entidadConjunto.COLUMN_NAME_FAVORITO,"1");
        String selectionUpdateFactura = DBHelper.entidadConjunto._ID+ " LIKE ?";
        String [] selectionArgsUpdateFactura = {IdConjunto};
        int countFavs = db.update(DBHelper.entidadConjunto.TABLE_NAME,valuesfav,selectionUpdateFactura,selectionArgsUpdateFactura);
        System.out.println(countFavs);
    }


    private ArrayList<Conjunto> seleccionarConjuntosConPrenda(String usuario,String PrendaSeleccionada) {
        //TODO prendaReferencia
        Cursor cursor;
        DBHelper dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadConjunto._ID, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4, DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO};

        String selection = DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO + "= ? AND ("+ DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1 + "= ? OR " +
                DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2 + "= ? OR "+ DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3 + "= ? OR " +
                DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4 + "= ?)";
        String[] selectionArgs = {usuario,PrendaSeleccionada,PrendaSeleccionada,PrendaSeleccionada,PrendaSeleccionada};
        String sortOrder = DBHelper.entidadConjunto._ID+" DESC";
        cursor = db.query(DBHelper.entidadConjunto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
        System.out.println("tamaño de cursor de prendas cojidad = "+cursor.getCount());
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


    private ArrayList<Conjunto> seleccionarConjuntosFavoritos(String usuario) {
        //TODO prendaReferencia
        Cursor cursor;
        DBHelper dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadConjunto._ID, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3, DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4, DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO};

        String selection = DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO + "= ? AND "+ DBHelper.entidadConjunto.COLUMN_NAME_FAVORITO + "= ?";
        String[] selectionArgs = {usuario,"1"};
        String sortOrder = DBHelper.entidadConjunto._ID+" DESC";
        cursor = db.query(DBHelper.entidadConjunto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
        System.out.println("tamaño de cursor de prendas cojidad = "+cursor.getCount());
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

}
