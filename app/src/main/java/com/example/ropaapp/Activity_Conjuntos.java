package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

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
    Button botonNuevoEstilo;
    int numeroConjuntos = 0;

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
        sUsuario = datos.getString("usuarioArmario",null);
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
        botonNuevoEstilo = findViewById(R.id.botonNuevoEstilo);

        //Seleccion y pintado
        conjuntos = seleccionarConjuntos(usuarioArmario, prendaReferencia);
        numeroConjuntos = conjuntos.size();
        pintarConjunto(conjuntos, posicion);

        //Listeners
        botonNuevoEstilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Crearconjunto.class);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), Activity_MenuySelecciondeclientes.class);
                startActivity(intent);
            }
        }
    }

    private void pintarFoto(ImageView iv, int foto) {
        Bitmap bmp = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+foto+".jpg");
        iv.setImageBitmap(bmp);
    }

}
