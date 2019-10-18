package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Activity_Registro extends AppCompatActivity {

    Button botonRegistrate;
    EditText registroNombreUsuario;
    EditText registroContrasenya;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ProgressBar barraProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        barraProgreso = findViewById(R.id.barraProgresoResgistro);
        barraProgreso.setMax(100);
        barraProgreso.setProgress(33);
        registroNombreUsuario = findViewById(R.id.registroNombreUsuario);
        registroContrasenya = findViewById(R.id.registroContrasenya);
        botonRegistrate = findViewById(R.id.botonRegistrate);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        botonRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsuario = registroNombreUsuario.getText().toString();
                String sContrasenya = registroContrasenya.getText().toString();

                //Variables para los Toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                //Comprobamos que haya introducido ambos datos
                if (!sUsuario.isEmpty() && !sContrasenya.isEmpty()){

                    //Comprobamos si ese usuario existe ya
                    long existentes = DatabaseUtils.queryNumEntries(db, DBHelper.entidadUsuario.TABLE_NAME,
                            DBHelper.entidadUsuario._ID + "=?", new String[] {sUsuario});

                    if (existentes == 0) {
                        Intent intent = new Intent (v.getContext(), Activity_Pagar.class);
                        intent.putExtra("sUsuario", sUsuario );
                        intent.putExtra("sContrasenya" , sContrasenya );
                        startActivity(intent);


                    }
                    else {
                        CharSequence text = getString(R.string.usuarioExiste);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                else {
                    CharSequence text = getString(R.string.faltanDatos);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //Intent intent = new Intent (v.getContext(), Activity_Registro.class);
                //startActivityForResult(intent, 0);
            }
        });
    }
}
