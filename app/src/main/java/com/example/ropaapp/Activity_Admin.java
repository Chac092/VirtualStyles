package com.example.ropaapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.ropaapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class Activity_Admin extends AppCompatActivity implements Fragment_Estilista.OnFragmentInteractionListener, Fragment_Usuario.OnFragmentInteractionListener, Fragment_Administrador.OnFragmentInteractionListener{
    MenuItem cobro;
    DBHelper dbHelper;
    SQLiteDatabase db;
    String sUsuario;
    String sPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Lamamos a la base de datos
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        //Ligamos los atributos con los contenidos del layout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        //Cojeremos los datos del usuario
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);

    }
    //Aqui se creara el menu overflow
   @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflowadmin, menu);
        return true;
    }
    //Aqui daremos las acciones que haran al clickar en las opciones del boton
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menuItemnomina){
            Pdf pdf =  new Pdf();
            Context contexto = getBaseContext();
            pdf.savePdf(sUsuario,sPerfil,contexto);
        }else if (id == R.id.menuItem2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}