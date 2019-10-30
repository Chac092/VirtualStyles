package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Activity_CrearConjunto extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    //String prendaReferencia;
    ImageView gorroConjunto;
    ImageView camisaConjunto;
    ImageView pantalonConjunto;
    ImageView zapatosConjunto;
    FloatingActionButton botonGuardarConjunto;
    String usuarioArmario;
    String categoria;
    String Prenda1;
    String Prenda2;
    String Prenda3;
    String Prenda4;
    View.OnClickListener listenerAdaptador;
    static ArrayList<String> misPrendas;
    String sUsuario;
    String sPerfil;


    Adapter_CrearConjunto miAdaptador;
    private RecyclerView rvPrendas;
    private RecyclerView.LayoutManager layoutManager;

    public static ArrayList<String> getMisPrendas() {
        return misPrendas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearconjunto);
        //DB
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        //Intent
        Intent intent = getIntent();
        categoria = intent.getStringExtra("categoria");
        if (categoria == null) {categoria = "1";}
        //Shared preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        usuarioArmario = datos.getString("usuarioArmario",null);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);

        //Elementos graficos
        gorroConjunto = findViewById(R.id.gorroConjunto);
        camisaConjunto = findViewById(R.id.camisaConjunto);
        pantalonConjunto = findViewById(R.id.pantalonConjunto);
        zapatosConjunto = findViewById(R.id.zapatosConjunto);
        botonGuardarConjunto = findViewById(R.id.botonGuardarConjunto);

        //Los listeners de cada elemento en pantalla
        gorroConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "1";
                refrescar();
            }
        });
        camisaConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "2";
                refrescar();
            }
        });
        pantalonConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "3";
                refrescar();
            }
        });
        zapatosConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "4";
                refrescar();
            }
        });

        botonGuardarConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variables para los Toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                //comprobamos si ya existe
                long existentes = DatabaseUtils.queryNumEntries(db, DBHelper.entidadConjunto.TABLE_NAME,
                        DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1 + "=? AND " +
                        DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2 + "=? AND " +
                        DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3 + "=? AND " +
                        DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4 + "=?"
                        , new String[] {Prenda1, Prenda2, Prenda3, Prenda4});
                if (existentes == 0) {
                    ContentValues valoresConjunto = new ContentValues();
                    //valoresConjunto.put(DBHelper.entidadUsuario._ID, sUsuario);
                    valoresConjunto.put(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1, Prenda1);
                    valoresConjunto.put(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2, Prenda2);
                    valoresConjunto.put(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3, Prenda3);
                    valoresConjunto.put(DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4, Prenda4);
                    valoresConjunto.put(DBHelper.entidadConjunto.COLUMN_NAME_IDUSUARIO, usuarioArmario);
                    long idConjunto = db.insert(DBHelper.entidadConjunto.TABLE_NAME, null, valoresConjunto);
                    //System.out.println("CONJUNTO GUARDADOOOOOOOOO!: " + idConjunto);
                    CharSequence text = getString(R.string.conjuntoGuardado);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    //
                    Intent intent = new Intent(v.getContext(), Activity_Conjuntos.class);
                    startActivity(intent);
                }
                else {
                    CharSequence text = getString(R.string.conjuntoExiste);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });

        //Acciones
        listenerAdaptador = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = rvPrendas.getChildAdapterPosition(v);
                String idFoto = misPrendas.get(posicion);
                String path = "/storage/emulated/0/saved_images/" + idFoto + ".jpg";
                Bitmap miFoto = BitmapFactory.decodeFile(path);
                if (categoria.equals("1")){
                    Prenda1 = idFoto;
                    gorroConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("2")){
                    Prenda2 = idFoto;
                    camisaConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("3")){
                    Prenda3 = idFoto;
                    pantalonConjunto.setImageBitmap(miFoto);
                }
                else if (categoria.equals("4")){
                    Prenda4 = idFoto;
                    zapatosConjunto.setImageBitmap(miFoto);
                }
            }
        };
        //
        //CARGUEMOS EL LISTENER
        misPrendas = listarPrendas(usuarioArmario, categoria, "1");
        miAdaptador = new Adapter_CrearConjunto (misPrendas);
        miAdaptador.setOnItemClickListener(listenerAdaptador);

        rvPrendas =findViewById(R.id.rvPrendas);
        rvPrendas.setAdapter(miAdaptador);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPrendas.setLayoutManager(layoutManager);
    }
    //Menu
    public boolean onCreateOptionsMenu(Menu menu)  {
        if (sPerfil.equals("estilista")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.overflowadmin, menu);
        }else if(sPerfil.equals("usuario")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.overflow, menu);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menuItemnomina){
            Pdf pdf =  new Pdf();
            Context contexto = getBaseContext();
            pdf.savePdf(sUsuario,sPerfil,contexto);
        }else if (id == R.id.menuItem2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.menuItemfactura){
            Pdf pdf =  new Pdf();
            Context contexto = getBaseContext();
            pdf.savePdf(sUsuario,sPerfil,contexto);
        }
        return super.onOptionsItemSelected(item);
    }




    //Listamos las prendas
    private ArrayList<String> listarPrendas(String usuario, String categoria, String estado){
        //System.out.println(usuario + " : " + categoria + " : " + estado);
        ArrayList<String> prendas = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadPrenda._ID};
        String selection = DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?" + " AND " + DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA + " = ?" + " AND " + DBHelper.entidadPrenda.COLUMN_NAME_ESTADO + " = ?";
        String[] selectionArgs = {usuario, categoria, estado};
        Cursor cursor = db.query(DBHelper.entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        while(cursor.moveToNext()){
            prendas.add(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID)));
        }
        return prendas;
    }
    //Refrescamos la pantalla
    private void refrescar(){
        misPrendas = listarPrendas(usuarioArmario, categoria, "1");
        miAdaptador = new Adapter_CrearConjunto (misPrendas);
        miAdaptador.setOnItemClickListener(listenerAdaptador);
        rvPrendas.setAdapter(miAdaptador);
    }

}
