package com.example.ropaapp;

        import androidx.appcompat.app.AppCompatActivity;
        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.Toast;

public class Activity_verArmario extends AppCompatActivity {
    ImageView fotillo;
    Uri path;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_armario);
        fotillo = findViewById(R.id.Fotoprenda);

        path = Uri.parse("content://com.android.providers.media.documents/document/image%A43");
        //Drawable d = Drawable.createFromPath("content://com.android.providers.media.documents/document/image%A43");
        //Bitmap bmp =  BitmapFactory.decodeFile();
        //Drawable d = new BitmapDrawable(getResources(), bmp);
        //fotillo.setImageURI(path);
        checkPermission();
        //ponerFoto();
    }
    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                ponerFoto();
            }
        }
        return;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
                ponerFoto();
            } else {
                Toast.makeText(this, "Permissions are not granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public void ponerFoto(){
        System.out.println("llego");
        fotillo.setImageURI(path);
    }
}
