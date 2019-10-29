package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ropaapp.DBHelper.entidadPrenda;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Seleccion_Prenda extends AppCompatActivity {
    static ArrayList<String> idfotos;
    DBHelper dbHelper;
    SQLiteDatabase db;
    String sUsuario;
    String sPerfil;
    //String rutaConCarpeta= Environment.getExternalStorageDirectory().toString() + "/saved_images";
    //List<String> item = new ArrayList<String>();
    Bitmap FTO;
    String IDfoto;
    public static ArrayList<String> getIdfotos() {
        return idfotos;
    }

    String usuarioArmario;

    Adaptador adap;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_prenda);
        recyclerView = findViewById(R.id.RecyclerView);
        //DB
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        //Intent
        Intent intent = getIntent();
        String categoria = intent.getStringExtra("categoria");
        //Shared preferences
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        usuarioArmario = datos.getString("usuarioArmario",null);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);
        //System.out.println("USUARIOARMARIO: " + usuarioArmario);

        //Acciones
        CojerImagenes(usuarioArmario, categoria, "1");

        adap = new Adaptador (feedData());
        adap.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(recyclerView.getChildAdapterPosition(v));
                String fotoaMandar  = idfotos.get(recyclerView.getChildAdapterPosition(v));
                Intent intent = new Intent(v.getContext(), Activity_Conjuntos.class);
                System.out.println(fotoaMandar);
                intent.putExtra("nombrefoto",fotoaMandar);
                intent.putExtra("Origen","armario");
                startActivity(intent);
            }
        });


        /*String fotoBorrar = idfotos.get(recyclerView.getChildAdapterPosition(v));
        Delete(fotoBorrar);
        Deleteconjunto(fotoBorrar);*/


        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setAdapter(adap);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
    }

    //TODO Centralizar
    public void CojerImagenes(String usuario, String categoria, String estado){
        //System.out.println(usuario + " : " + categoria + " : " + estado);
        idfotos = new ArrayList<>();
       SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {entidadPrenda._ID};
        String selection = entidadPrenda.COLUMN_NAME_IDUSUARIO + " = ?" + " AND " + entidadPrenda.COLUMN_NAME_CATEGORIA + " = ?" + " AND " + entidadPrenda.COLUMN_NAME_ESTADO + " = ?";
        String[] selectionArgs = {usuario, categoria, estado};
        Cursor cursor = db.query(entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
           while(cursor.moveToNext()){
            IDfoto = cursor.getString(cursor.getColumnIndexOrThrow(entidadPrenda._ID));
            idfotos.add(IDfoto);
            //System.out.println(IDfoto);
        }
    }

    public ArrayList<String> feedData(){
        ArrayList<String> idFotos = new ArrayList<String>();
        idFotos.add("primero");
        idFotos.add("segundo");
        return idfotos;
    }
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mencontextual, menu);
    }
    public void Delete(String nombre){
        String selection = DBHelper.entidadPrenda._ID + " LIKE ?";
        String [] selectionArgs = {nombre};
        int deletedRows = db.delete(DBHelper.entidadPrenda.TABLE_NAME,selection,selectionArgs);
        System.out.println("Borro foto");

        File fdelete = new File("/storage/emulated/0/saved_images/"+nombre+".jpg");
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + "/storage/emulated/0/saved_images/"+nombre+".jpg");
            } else {
                System.out.println("file not Deleted :" + "/storage/emulated/0/saved_images/"+nombre+".jpg");
            }
        }
    }
    public void Deleteconjunto(String nombre){
        Cursor cursor;
        String[] projection = {DBHelper.entidadConjunto._ID};

        String selection = DBHelper.entidadConjunto.COLUMN_NAME_PRENDA1 + "= ? OR " +
                DBHelper.entidadConjunto.COLUMN_NAME_PRENDA2 + "= ? OR "+
                DBHelper.entidadConjunto.COLUMN_NAME_PRENDA3 + "= ? OR "+
                DBHelper.entidadConjunto.COLUMN_NAME_PRENDA4 + "= ? ";
        String[] selectionArgs = {nombre,nombre,nombre,nombre};
        String sortOrder = DBHelper.entidadConjunto._ID+" DESC";
        cursor = db.query(DBHelper.entidadConjunto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        while(cursor.moveToNext()){
            String idconjunto = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadConjunto._ID));
            String selectionborrar = DBHelper.entidadConjunto._ID + " = ?" ;
            String [] selectionArgsBorrar = {idconjunto};
            int deletedRows = db.delete(DBHelper.entidadPrenda.TABLE_NAME,selectionborrar,selectionArgsBorrar);
            System.out.println("Borrado");
        }



    }
}


