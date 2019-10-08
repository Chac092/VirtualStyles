package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_Pagar extends AppCompatActivity {

    Button botonPagar;
    EditText ETnumeroTarjeta;
    EditText ETfechaCaducidad;
    EditText ETcodigoSeguridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);

        Intent i = getIntent();
        final String sUsuario = i.getStringExtra("sUsuario");
        final String sContrasenya = i.getStringExtra("sContrasenya");
        //System.out.println(sUsuario +" "+ sContrasenya);

        ETnumeroTarjeta = findViewById(R.id.ETnumeroTarjeta);
        ETfechaCaducidad = findViewById(R.id.ETfechaCaducidad);
        ETcodigoSeguridad = findViewById(R.id.ETcodigoSeguridad);

        botonPagar = findViewById(R.id.botonPagar);
        botonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO comprobar valores de la tarjeta
            String sNumeroTarjeta = ETnumeroTarjeta.getText().toString();
            String sFechaCaducidad = ETfechaCaducidad.getText().toString();
            String sCodigoSeguridad = ETcodigoSeguridad.getText().toString();

            Intent intent = new Intent (v.getContext(), Activity_Terminos.class);
            intent.putExtra("sUsuario", sUsuario);
            intent.putExtra("sContrasenya", sContrasenya);
            intent.putExtra("sNumeroTarjeta", sNumeroTarjeta);
            intent.putExtra("sFechaCaducidad", sFechaCaducidad);
            intent.putExtra("sCodigoSeguridad", sCodigoSeguridad);
            startActivity(intent);
            }
        });

    }
}
