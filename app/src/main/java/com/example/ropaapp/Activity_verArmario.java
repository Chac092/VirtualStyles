package com.example.ropaapp;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Path;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Messenger;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.ArrayList;
        import java.util.List;

        import static java.security.AccessController.getContext;

public class Activity_verArmario extends AppCompatActivity {
    ImageView fotillo;
    DBHelper dbHelper;
    String usuario;
    ImageView fotoPoner;
    SQLiteDatabase db;
    LinearLayout layout;
    int a =1;
    private static final int RQS_OPEN_IMAGE = 1;
    ArrayList<String> Fotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_armario);
        Intent intent = getIntent();
        usuario = intent.getStringExtra("NombreUsuario");
        layout = findViewById(R.id.layout);
        checkDocumentPermission();
        checkreadPermission();
        recojerFotos();
        ponerLasFotos();

    }

    public void recojerFotos(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadPrenda._ID};
        String selection = DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO+"= ?";
        String[] selectionArgs = {usuario};
        String sortOrder = DBHelper.entidadPrenda._ID+" DESC";
        Cursor cursor = db.query(DBHelper.entidadPrenda.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        while(cursor.moveToNext()){
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            Fotos.add(nombre);
            System.out.println(nombre);
        }
    }

    public void ponerLasFotos(){
       for(int i=0;i<Fotos.size();i++){
          int e= i+1;
          Bitmap FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+e+".jpg");
          fotoPoner = new ImageView(this);
          fotoPoner.setId(a);
          fotoPoner.setMinimumHeight(900);
          fotoPoner.setMinimumWidth(1200);
          fotoPoner.setImageBitmap(FTO);
          layout.addView(fotoPoner);
          fotoPoner.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String idFtot = Integer.toString(v.getId());
                  Intent intent = new Intent(v.getContext(), Activity_Conjuntos.class);
                  intent.putExtra("nombrePrenda",)
              }
          });
          a=a+1;
          System.out.println("Entro");
       }
    }
    //Este metodo sirve para dar permisos de manejar documentos en la app
    private void checkDocumentPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.MANAGE_DOCUMENTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    //Aqui pediremos los permisos de lectura
    private void checkreadPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
}




