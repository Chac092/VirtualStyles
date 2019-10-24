package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Activity_Pagar extends AppCompatActivity {

    Button botonPagar;
    EditText ETnumeroTarjeta;
    EditText ETfechaCaducidad;
    EditText ETcodigoSeguridad;
    TextView precio;
    ProgressBar barraProgreso;
    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getApplication().getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        barraProgreso = findViewById(R.id.barraProgesoPagar);
        barraProgreso.setMax(100);
        barraProgreso.setProgress(66);
        precio = findViewById(R.id.cantidad);
        Intent i = getIntent();
        final String sUsuario = i.getStringExtra("sUsuario");
        final String sContrasenya = i.getStringExtra("sContrasenya");
        //System.out.println(sUsuario +" "+ sContrasenya);
        actualizarPrecio();
        ETnumeroTarjeta = findViewById(R.id.ETnumeroTarjeta);
        ETfechaCaducidad = findViewById(R.id.ETfechaCaducidad);
        ETcodigoSeguridad = findViewById(R.id.ETcodigoSeguridad);

        botonPagar = findViewById(R.id.botonPagar);
        botonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO comprobar valores de la tarjeta
                long iNumeroTarjeta = Long.parseLong(ETnumeroTarjeta.getText().toString());
                String sFechaCaducidad = ETfechaCaducidad.getText().toString();
                int iCodigoSeguridad = Integer.parseInt(ETcodigoSeguridad.getText().toString());

                Intent intent = new Intent (v.getContext(), Activity_Terminos.class);
                intent.putExtra("sUsuario", sUsuario);
                intent.putExtra("sContrasenya", sContrasenya);
                intent.putExtra("iNumeroTarjeta", iNumeroTarjeta);
                intent.putExtra("sFechaCaducidad", sFechaCaducidad);
                intent.putExtra("iCodigoSeguridad", iCodigoSeguridad);
                startActivity(intent);
            }
        });
    }
    public void actualizarPrecio(){
        String dinero = "90";
        String[] projectionFactura = {DBHelper.entidadPrecio.COLUMN_NAME_PRECIO};
        String selectionFactura = DBHelper.entidadPrecio.COLUMN_NAME_IDUSUARIO + "= ?";
        String[] selectionArgsFactura = {"usuario"};
        Cursor cursorFacturas = db.query(DBHelper.entidadPrecio.TABLE_NAME, projectionFactura, selectionFactura, selectionArgsFactura, null, null, null);
        //Guardamos esos datos
        if (cursorFacturas.moveToNext()) {
            dinero = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow(DBHelper.entidadPrecio.COLUMN_NAME_PRECIO));
        }
        precio.setText(dinero + "€");
    }
}
