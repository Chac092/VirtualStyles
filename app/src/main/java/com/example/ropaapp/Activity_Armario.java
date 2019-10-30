package com.example.ropaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import java.io.File;
import java.io.FileOutputStream;

public class Activity_Armario extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;

    String sUsuario;
    String sPerfil;
    Bitmap bmp;
    Button botonGorro;
    Button botonCamisa;
    Button botonPantalones;
    Button botonZapatos;
    String usuarioArmario;
    FloatingActionButton subirFoto;
    FloatingActionButton consultarFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getWritableDatabase();
        Permisos();
        Intent intent = getIntent();
        //Cojeremos el id del usuario logueado
        final String MY_PREFS_NAME = "File";
        SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sUsuario = datos.getString("sUsuario",null);
        sPerfil = datos.getString("sPerfil",null);
        usuarioArmario = datos.getString("usuarioArmario",null);
        //System.out.println("USUARIOARMARIO_A: " + usuarioArmario);
        

        botonGorro = findViewById(R.id.botonGorro);
        botonGorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Seleccion_Prenda.class);
                intent.putExtra("categoria", "1");
                startActivityForResult(intent, 0);

            }
        });
        botonCamisa = findViewById(R.id.botonCamisa);
        botonCamisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Activity_Seleccion_Prenda.class);
            intent.putExtra("categoria", "2");
            startActivityForResult(intent, 0);

            }
        });

        botonPantalones = findViewById(R.id.botonPantalones);
        botonPantalones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Seleccion_Prenda.class);
                intent.putExtra("categoria", "3");
                startActivityForResult(intent, 0);

            }
        });
        botonZapatos = findViewById(R.id.botonZapatos);
        botonZapatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Seleccion_Prenda.class);
                intent.putExtra("categoria", "4");
                startActivityForResult(intent, 0);

            }
        });

        consultarFotos = findViewById(R.id.BTNPendientes);
        consultarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_rellenarPrendas.class);
                intent.putExtra("NombreUsuario", usuarioArmario);
                startActivityForResult(intent, 0);
            }
        });

        subirFoto = findViewById(R.id.BTNSubirFoto);
        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarfoto();
            }
        });
        if (sPerfil.equals("estilista")) {
            //subirFoto.setVisibility(View.INVISIBLE);
            sUsuario = intent.getStringExtra("NombreUsuario");
        }else if (sPerfil.equals("usuario")){
           //consultarFotos.setVisibility(View.INVISIBLE);
        }

    }

    //Este metodo lo usaremos para sacar la foto
    public void sacarfoto(){
        //Mediante un intente llamaremos a la camara para sacar una foto
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Una vez sacada esa foto vamos a cojerla del intent y la guardaremos en forma de bitmap
            Bundle ext = data.getExtras();
            bmp = (Bitmap) ext.get("data");
            //System.out.println("exito");
            //guardarFoto();
            saveTempBitmap(bmp);
        }
    }
      //Aqui comprobaremos si tenemos los permisos de escritura y si no lo tenemos los pediremos
    private void Permisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisocamara = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permisocamara != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO, "0");
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_FAVORITO,"0");
        //System.out.println(sUsuario);
        values.put(DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO,sUsuario);
        long newRowId = db.insert(DBHelper.entidadPrenda.TABLE_NAME, null, values);
        String IDfoto = String.valueOf(newRowId);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = IDfoto +".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
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
            savePdf();
        }else if (id == R.id.menuItem2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.menuItemfactura){
            savePdf();
        }
        return super.onOptionsItemSelected(item);
    }
    private void savePdf() {
        //Consultamos el precio de en la base de datos
        String precio = "90";
        String[] projectionFactura = {DBHelper.entidadPrecio.COLUMN_NAME_PRECIO};
        String selectionFactura = DBHelper.entidadPrecio.COLUMN_NAME_IDUSUARIO + "= ?";
        //System.out.println(sUsuario);
        String[] selectionArgsFactura = {sUsuario};
        if(sPerfil.equals("estilista")){
            //System.out.println("Estamos en armario y nos acaba de cargar "+sUsuario);
            selectionArgsFactura[0] = sUsuario;
        }else if(sPerfil.equals("usuario")){
            selectionArgsFactura [0]= "usuario";
        }
        Cursor cursorFacturas = db.query(DBHelper.entidadPrecio.TABLE_NAME, projectionFactura, selectionFactura, selectionArgsFactura, null, null, null);
        //System.out.println(sUsuario);
        //System.out.println(cursorFacturas.getCount());
        //Guardamos esos datos
        if (cursorFacturas.moveToNext()) {
            precio = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow(DBHelper.entidadPrecio.COLUMN_NAME_PRECIO));
            //System.out.println(precio);
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
                //System.out.println(e);
                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
    }
}
