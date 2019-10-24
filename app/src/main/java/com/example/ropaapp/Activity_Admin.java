package com.example.ropaapp;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
    private void savePdf() {

        String sUsuario = "";
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario =datos.getString("sUsuario",null);
        System.out.println(sUsuario);
        //Consultamos el precio de en la base de datos
        String precio = "90";
        String[] projectionFactura = {DBHelper.entidadPrecio.COLUMN_NAME_PRECIO};
        String selectionFactura = DBHelper.entidadPrecio.COLUMN_NAME_IDUSUARIO + "= ?";
        System.out.println(sUsuario);
        String[] selectionArgsFactura = {sUsuario};
        Cursor cursorFacturas = db.query(DBHelper.entidadPrecio.TABLE_NAME, projectionFactura, selectionFactura, selectionArgsFactura, null, null, null);
        System.out.println(sUsuario);
        System.out.println(cursorFacturas.getCount());
        //Guardamos esos datos
        if (cursorFacturas.moveToNext()) {
            precio = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow(DBHelper.entidadPrecio.COLUMN_NAME_PRECIO));
            System.out.println(precio);
        }

        String Factura =
                sUsuario +"\n"+
                        "Ornilla Doctor Kalea, 2\n" +
                        "48004\n" +
                        "Bilbo, Bizkaia\n" +
                        "\n" +
                        "                                                                                   Virtual Style\n" +
                        "                                                                               Raudna-Loodi\n" +
                        "                                                                                           69680\n" +
                        "                                                                           Viljandi maakond\n" +
                        "                                                                                          Estonia\n" +
                        "\n" +
                        "\n" +
                        "|------------------------------------------------------------------------------------------|\n" +
                        "|NOMBRE            |CANTIDAD                  |PRECIO                            |\n" +
                        "|-----------------------|-------------------------------|----------------------------------|\n" +
                        "|Cuota mensual   |1                                   |Variable                            |\n" +
                        "|                           |                                     |                                        |\n" +
                        "|                       |                               |IVA                                  21% |\n" +
                        "|------------------------------------------------------------------------------------------|\n" +
                        "|TOTAL           |                                        |"+precio+"                                       |\n" +
                        "|------------------------------------------------------------------------------------------|\n";
        Document mDoc =new Document();
        File ruta = new File("/storage/emulated/0/saved_pdf/");
        if(!ruta.exists()){
            ruta.mkdir();
        }
        String mFilePath= "/storage/emulated/0/saved_pdf/"+"Nuevafactura"+".pdf";
        try{
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            mDoc.open();
            mDoc.addAuthor("Goazen");
            mDoc.add(new Paragraph(Factura));
            mDoc.close();
            CharSequence text = "pdf descargado";
            Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
            toast.show();
        }
        catch (Exception e){
            System.out.println(e);
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow2, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menuItemnomina){
            savePdf();
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