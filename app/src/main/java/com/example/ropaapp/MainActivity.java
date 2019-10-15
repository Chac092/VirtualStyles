package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //variables to manage de database
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button botonEntrar;
    Button botonRegistro;
    EditText loginNombreUsuario;
    EditText loginContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();

        loginNombreUsuario = findViewById(R.id.loginNombreUsuario);
        loginContrasenya = findViewById(R.id.loginContrasenya);
        botonRegistro = findViewById(R.id.botonRegistro);
        botonEntrar = findViewById(R.id.botonEntrar);
        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsuario = loginNombreUsuario.getText().toString();
                String sContrasenya = loginContrasenya.getText().toString();

                //Variables para los Toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                //Comprobamos que haya introducido ambos datos
                if (!sUsuario.isEmpty() && !sContrasenya.isEmpty()){

                    //Comprobamos si ese usuario existe ya
                    String[] projection = {
                            DBHelper.entidadUsuario.COLUMN_NAME_PERFIL
                    };
                    String selection = DBHelper.entidadUsuario._ID + " = ?" + " AND " + DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA + " = ?";
                    String[] selectionArgs = {sUsuario, sContrasenya};

                    Cursor cursor = db.query(
                        DBHelper.entidadUsuario.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                    );

                    if(cursor != null && cursor.getCount()>0){
                        cursor.moveToFirst();
                        //do your action
                        //Fetch your data
                        String sPerfil = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadUsuario.COLUMN_NAME_PERFIL));
                        //System.out.println(perfil);
                        final String MY_PREFS_NAME = "File";
                        SharedPreferences.Editor datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        datos.putString("sUsuario", sUsuario);
                        datos.putString("sPerfil", sPerfil);

                        datos.apply();
                        if(sPerfil.equals("estilista")) {
                            Intent intent = new Intent(v.getContext(), Activity_MenuySelecciondeclientes.class);
                            startActivity(intent);
                        }
                        if (sPerfil.equals("usuario")) {
                            Intent intent = new Intent(v.getContext(), Activity_Menu.class);
                            startActivity(intent);
                        }
                    }
                    cursor.close();
                }
                else {
                    CharSequence text = getString(R.string.faltanDatos);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
            });
        botonRegistro.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                Intent intent = new Intent(v.getContext(), Activity_Registro.class);
                startActivity(intent);
            }
            });
        }
}




