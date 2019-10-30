package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Activity_Pagar extends AppCompatActivity {

    Button botonPagar;
    EditText ETnumeroTarjeta;
    EditText ETfechaCaducidad;
    EditText ETcodigoSeguridad;
    ProgressBar barraProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);
        barraProgreso = findViewById(R.id.barraProgesoPagar);
        barraProgreso.setMax(100);
        barraProgreso.setProgress(66);
        Intent i = getIntent();
        final String sUsuario = i.getStringExtra("sUsuario");
        final String sContrasenya = i.getStringExtra("sContrasenya");
        //System.out.println(sUsuario +" "+ sContrasenya);
        //damos contenido a los atributos
        ETnumeroTarjeta = findViewById(R.id.ETnumeroTarjeta);
        ETfechaCaducidad = findViewById(R.id.ETfechaCaducidad);
        ETcodigoSeguridad = findViewById(R.id.ETcodigoSeguridad);
        //Ponemos los listeners
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
}
