package com.example.ropaapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
        Context context = GlobalApplication.getAppContext();
        System.out.println(context);
        InputStream in = context.getResources().openRawResource(context.getResources().getIdentifier(fileName,"raw", context.getPackageName()));
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
