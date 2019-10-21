package com.example.ropaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class prendasEjemplo extends AppCompatActivity {


    public void copiaImagen(int i) throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        // the file to be moved or copied
        String fileName = "p"+ i;
        InputStream in = App.context().getResources().openRawResource(getResources().getIdentifier(fileName,"raw", getPackageName()));
        File targetLocation = new File(root + "/saved_images/" + i + ".jpg");
        OutputStream out = new FileOutputStream(targetLocation);
        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        String TAG = "CopiaImagen " + i;
        Log.v(TAG, "Copy file successful.");
    }
}
