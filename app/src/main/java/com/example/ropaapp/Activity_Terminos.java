package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Activity_Terminos extends AppCompatActivity {

    Button botonRechazarTerminos;
    Button botonAceptarTerminos;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ProgressBar barraProgresoTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__terminos);
        barraProgresoTerminos = findViewById(R.id.barraProgresoTerminos);
        barraProgresoTerminos.setMax(100);
        barraProgresoTerminos.setProgress(100);
        Intent i = getIntent();
        final String sUsuario = i.getStringExtra("sUsuario");
        final String sContrasenya = i.getStringExtra("sContrasenya");
        final int iNumeroTarjeta = i.getIntExtra("iNumeroTarjeta", 0);
        final String sFechaCaducidad = i.getStringExtra("sFechaCaducidad");
        final int iCodigoSeguridad = i.getIntExtra("iCodigoSeguridad", 0);

        botonRechazarTerminos = findViewById(R.id.botonRechazarTerminos);
        botonAceptarTerminos = findViewById(R.id.botonAceptarTerminos);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        botonRechazarTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        botonAceptarTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues valoresUsuario = new ContentValues();
                valoresUsuario.put(DBHelper.entidadUsuario._ID, sUsuario);
                valoresUsuario.put(DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA, sContrasenya);
                valoresUsuario.put(DBHelper.entidadUsuario.COLUMN_NAME_PERFIL, "usuario");
                long idUsuario = db.insert(DBHelper.entidadUsuario.TABLE_NAME, null, valoresUsuario);

                ContentValues valoresTarjeta = new ContentValues();
                valoresTarjeta.put(DBHelper.entidadTarjeta._ID, iNumeroTarjeta);
                valoresTarjeta.put(DBHelper.entidadTarjeta.COLUMN_NAME_CADUCIDAD, sFechaCaducidad);
                valoresTarjeta.put(DBHelper.entidadTarjeta.COLUMN_NAME_CODIGO_SEGURIDAD, iCodigoSeguridad);
                valoresTarjeta.put(DBHelper.entidadTarjeta.COLUMN_NAME_IDUSUARIO, idUsuario);



                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}