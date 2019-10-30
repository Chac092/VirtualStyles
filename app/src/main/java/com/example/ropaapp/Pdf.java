package com.example.ropaapp;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

public class Pdf {
    /*final String MY_PREFS_NAME = "File";
    SharedPreferences datos = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    String sPerfil = datos.getString("sPerfil",null);
    String sUsuario = datos.getString("sUsuario",null);*/
    SQLiteDatabase db;
    DBHelper dbHelper;


    public void savePdf(String sUsuario,String sPerfil,Context contexto) {
        dbHelper = new DBHelper(contexto);
        db = dbHelper.getWritableDatabase();
        //Consultamos el precio de en la base de datos
        String precio = "90";
        String[] projectionFactura = {DBHelper.entidadPrecio.COLUMN_NAME_PRECIO};
        String selectionFactura = DBHelper.entidadPrecio.COLUMN_NAME_IDUSUARIO + "= ?";
        System.out.println(sUsuario);
        String[] selectionArgsFactura = {sUsuario};
        if(sPerfil.equals("estilista")){
            System.out.println("Estamos en armario y nos acaba de cargar "+sUsuario);
            selectionArgsFactura[0] = sUsuario;
        }else if(sPerfil.equals("usuario")){
            selectionArgsFactura [0]= "usuario";
        }
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
            Toast toast = Toast.makeText(contexto, text, Toast.LENGTH_LONG);
            toast.show();
        }
        catch (Exception e){
            System.out.println(e);
            Toast.makeText(contexto,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
