package com.example.ropaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VirtualStyle.db";

    //Aqui crearemos las ordenes para mas adelante crear la tabla Facturas
    public static class entidadFactura implements BaseColumns{
        public static final String TABLE_NAME = "Facturas";
        //public static final String COLUMN_NAME_IDFACTURA = "idFactura";
        public static final String COLUMN_NAME_IMPORTE = "Dinero" ;
        public static final String COLUMN_NAME_FECHA="Fecha";
        public static final String COLUMN_NAME_IDUSUARIO="idUsuario";
    }

    public static class entidadUsuario implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        //public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
        public static final String COLUMN_NAME_PERFIL = "Perfil";
        public static final String COLUMN_NAME_CONTRASENYA = "Contraseña";
    }
    public static class entidadPrecio implements BaseColumns {
        public static final String TABLE_NAME = "precio";
        public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
        public static final String COLUMN_NAME_PRECIO = "precio";
    }

    public static class entidadTarjeta implements BaseColumns {
        public static final String TABLE_NAME = "tarjeta";
        //public static final String COLUMN_NAME_NUMERO_TARJETA = "numeroTarjeta";
        public static final String COLUMN_NAME_CADUCIDAD = "Caducidad";
        public static final String COLUMN_NAME_CODIGO_SEGURIDAD = "codigoSeguridad";
        public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
    }

    public static class entidadPrenda implements BaseColumns {
        public static final String TABLE_NAME = "prenda";
        //public static final String COLUMN_NAME_IDPRENDA = "idPrenda";
        public static final String COLUMN_NAME_CATEGORIA = "Categoria";
        public static final String COLUMN_NAME_ESTADO = "Estado";
        public static final String COLUMN_NAME_FAVORITO = "Favorito";
        public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
    }

    public static class entidadConjunto implements BaseColumns {
        public static final String TABLE_NAME = "conjunto";
        //public static final String COLUMN_NAME_IDCONJUNTO = "idConjunto";
        public static final String COLUMN_NAME_PRENDA1 = "Prenda1";
        public static final String COLUMN_NAME_PRENDA2 = "Prenda2";
        public static final String COLUMN_NAME_PRENDA3 = "Prenda3";
        public static final String COLUMN_NAME_PRENDA4 = "Prenda4";
        public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
    }

    private static final String SQL_CREATE_TABLE_USUARIO =
            "CREATE TABLE " + entidadUsuario.TABLE_NAME + " (" +
                    entidadUsuario._ID + " TEXT PRIMARY KEY," +
                    entidadUsuario.COLUMN_NAME_CONTRASENYA + " TEXT," +
                    entidadUsuario.COLUMN_NAME_PERFIL + " TEXT)";

    private static final String SQL_DELETE_TABLE_USUARIO =
            "DROP TABLE IF EXISTS " + entidadUsuario.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_PRECIO =
            "CREATE TABLE " + entidadPrecio.TABLE_NAME + " (" +
                    entidadPrecio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadPrecio.COLUMN_NAME_IDUSUARIO + " TEXT," +
                    entidadPrecio.COLUMN_NAME_PRECIO+ " INTEGER)";

    private static final String SQL_DELETE_TABLE_PRECIO =
            "DROP TABLE IF EXISTS " + entidadPrecio.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_FACTURAS =
            "CREATE TABLE " + entidadFactura.TABLE_NAME + " (" +
                    entidadFactura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadFactura.COLUMN_NAME_IMPORTE + " INTEGER," +
                    entidadFactura.COLUMN_NAME_FECHA + " TEXT," +
                    entidadFactura.COLUMN_NAME_IDUSUARIO + " INTEGER," +
                    "FOREIGN KEY('" + entidadFactura.COLUMN_NAME_IDUSUARIO + "') REFERENCES '" + entidadUsuario.TABLE_NAME + "'('" + entidadUsuario._ID + "'))";

    private static final String SQL_DELETE_TABLE_FACTURAS = "DROP TABLE IF EXISTS " +  entidadFactura.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_TARJETA =
            "CREATE TABLE " + entidadTarjeta.TABLE_NAME + " (" +
                    entidadTarjeta._ID + " INTEGER PRIMARY KEY," +
                    entidadTarjeta.COLUMN_NAME_CADUCIDAD + " TEXT," +
                    entidadTarjeta.COLUMN_NAME_CODIGO_SEGURIDAD + " TEXT," +
                    entidadTarjeta.COLUMN_NAME_IDUSUARIO + " INTEGER," +
                    "FOREIGN KEY('" + entidadTarjeta.COLUMN_NAME_IDUSUARIO + "') REFERENCES '" + entidadUsuario.TABLE_NAME + "'('" + entidadUsuario._ID + "'))";

    private static final String SQL_DELETE_TABLE_TARJETA =
            "DROP TABLE IF EXISTS " + entidadTarjeta.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_PRENDA =
            "CREATE TABLE " + entidadPrenda.TABLE_NAME + " (" +
                    entidadPrenda._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadPrenda.COLUMN_NAME_CATEGORIA + " INTEGER," +
                    entidadPrenda.COLUMN_NAME_ESTADO + " INTEGER," +
                    entidadPrenda.COLUMN_NAME_FAVORITO + " INTEGER," +
                    entidadPrenda.COLUMN_NAME_IDUSUARIO + " INTEGER," +
                    "FOREIGN KEY('" + entidadPrenda.COLUMN_NAME_IDUSUARIO + "') REFERENCES '" + entidadUsuario.TABLE_NAME + "'('" + entidadUsuario._ID + "'))";


    private static final String SQL_DELETE_TABLE_PRENDA =
            "DROP TABLE IF EXISTS " + entidadPrenda.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_CONJUNTO =
            "CREATE TABLE " + entidadConjunto.TABLE_NAME + " (" +
                    entidadConjunto._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadConjunto.COLUMN_NAME_PRENDA1 + " INTEGER," +
                    entidadConjunto.COLUMN_NAME_PRENDA2 + " INTEGER," +
                    entidadConjunto.COLUMN_NAME_PRENDA3 + " INTEGER," +
                    entidadConjunto.COLUMN_NAME_PRENDA4 + " INTEGER," +
                    entidadConjunto.COLUMN_NAME_IDUSUARIO + " INTEGER," +

                    "FOREIGN KEY('" + entidadConjunto.COLUMN_NAME_PRENDA1 + "') REFERENCES '" + entidadPrenda.TABLE_NAME + "'('" + entidadPrenda._ID + "')," +
                    "FOREIGN KEY('" + entidadConjunto.COLUMN_NAME_PRENDA2 + "') REFERENCES '" + entidadPrenda.TABLE_NAME + "'('" + entidadPrenda._ID + "')," +
                    "FOREIGN KEY('" + entidadConjunto.COLUMN_NAME_PRENDA3 + "') REFERENCES '" + entidadPrenda.TABLE_NAME + "'('" + entidadPrenda._ID + "')," +
                    "FOREIGN KEY('" + entidadConjunto.COLUMN_NAME_PRENDA4 + "') REFERENCES '" + entidadPrenda.TABLE_NAME + "'('" + entidadPrenda._ID + "')," +
                    "FOREIGN KEY('" + entidadConjunto.COLUMN_NAME_IDUSUARIO + "') REFERENCES '" + entidadUsuario.TABLE_NAME + "'('" + entidadUsuario._ID + "'))" ;


    private static final String SQL_DELETE_TABLE_CONJUNTO =
            "DROP TABLE IF EXISTS " + entidadConjunto.TABLE_NAME;

    //TODO Chapuza para crear datos de inicio
    //Estilista
    private static final String SQL_INSERT_ESTILISTA =
            "INSERT INTO " + entidadUsuario.TABLE_NAME + " (" +
            entidadUsuario._ID + ", " +
            entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
            entidadUsuario.COLUMN_NAME_PERFIL + ") " +
            "VALUES ('estilista', 'estilista', 'estilista')";
    //Usuario: ADAN
    private static final String SQL_INSERT_ADAN =
            "INSERT INTO " + entidadUsuario.TABLE_NAME + " (" +
                    entidadUsuario._ID + ", " +
                    entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
                    entidadUsuario.COLUMN_NAME_PERFIL + ") " +
                    "VALUES ('adan', 'manzana', 'usuario')";
    //Usuario: EVA
    private static final String SQL_INSERT_EVA =
            "INSERT INTO " + entidadUsuario.TABLE_NAME + " (" +
                    entidadUsuario._ID + ", " +
                    entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
                    entidadUsuario.COLUMN_NAME_PERFIL + ") " +
                    "VALUES ('eva', 'manzana', 'usuario')";
    //Admin: Admin
    private static final String SQL_INSERT_ADMIN =
            "INSERT INTO " + entidadUsuario.TABLE_NAME + " (" +
                    entidadUsuario._ID + ", " +
                    entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
                    entidadUsuario.COLUMN_NAME_PERFIL + ") " +
                    "VALUES ('admin', 'admin', 'admin')";
    //usuario : 5
    private static final String SQL_INSERT_PRECIOUSU =
            "INSERT INTO " + entidadPrecio.TABLE_NAME + " (" +
                    entidadPrecio.COLUMN_NAME_IDUSUARIO + ", " +
                    entidadPrecio.COLUMN_NAME_PRECIO + ") " +
                    "VALUES ('usuario', '5')";
    //estilista: 1
    private static final String SQL_INSERT_PRECIOESTILISTA =
            "INSERT INTO " + entidadPrecio.TABLE_NAME + " (" +
                    entidadPrecio.COLUMN_NAME_IDUSUARIO + ", " +
                    entidadPrecio.COLUMN_NAME_PRECIO + ") " +
                    "VALUES ('estilista', '1')";
    //estilista: 1
    private static final String SQL_INSERT_PRECIOADMIN =
            "INSERT INTO " + entidadPrecio.TABLE_NAME + " (" +
                    entidadPrecio.COLUMN_NAME_IDUSUARIO + ", " +
                    entidadPrecio.COLUMN_NAME_PRECIO + ") " +
                    "VALUES ('admin', '8')";


    //Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TABLE_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_TARJETA);
        db.execSQL(SQL_CREATE_TABLE_PRENDA);
        db.execSQL(SQL_CREATE_TABLE_CONJUNTO);
        db.execSQL(SQL_CREATE_TABLE_FACTURAS);
        db.execSQL(SQL_CREATE_TABLE_PRECIO);
        db.execSQL(SQL_INSERT_PRECIOUSU);//TODO Chapuza
        db.execSQL(SQL_INSERT_PRECIOESTILISTA);//TODO Chapuza
        db.execSQL(SQL_INSERT_ADAN);//TODO Chapuza
        db.execSQL(SQL_INSERT_EVA);//TODO Chapuza
        db.execSQL(SQL_INSERT_ESTILISTA);//TODO Chapuza
        db.execSQL(SQL_INSERT_PRECIOADMIN);//TODO Chapuza
        db.execSQL(SQL_INSERT_ADMIN); //TODO Chapuza
        creaPrendas(db);
    }

    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_TABLE_CONJUNTO);
        db.execSQL(SQL_DELETE_TABLE_PRENDA);
        db.execSQL(SQL_DELETE_TABLE_TARJETA);
        db.execSQL(SQL_DELETE_TABLE_USUARIO);
        db.execSQL(SQL_DELETE_TABLE_FACTURAS);
        db.execSQL(SQL_DELETE_TABLE_PRECIO);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void creaPrendas(SQLiteDatabase db) {
        //System.out.println("VAMOOOOOOOOOOOO");
        // Tenemos 20 prendas, primero las de adan en orden cabeza, pecho, piernas, pies y luego las de eva en el mismo orden
        String sUsuario = "adan";
        int categoria;
        boolean estado = true;
        boolean favorito = false;
        for (int i = 1; i <= 40; i++) {
            try {
                prendasEjemplo pe = new prendasEjemplo();
                pe.copiaImagen(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i > 20){sUsuario = "eva";}
            //Calculamos la categoria de la prenda (1 cabeza, 2 pecho, 3 piernas, 4 pies)
            categoria = 1+ ((i-1)/5);
            if (categoria > 4) {categoria -= 4;}
            //dejamos una de cada 5 prendas sin clasificar
            if (i%5 == 0) {estado = false;} else {estado = true;}
            //System.out.println(i + " : " + categoria + " : " + sUsuario + " : " + estado);

            //Insertar los datos en la BD
            ContentValues values = new ContentValues();
            values.put(DBHelper.entidadPrenda._ID, i);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_CATEGORIA,categoria);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_ESTADO, estado);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_FAVORITO, false);
            values.put(DBHelper.entidadPrenda.COLUMN_NAME_IDUSUARIO,sUsuario);
            long newRowId = db.insert(DBHelper.entidadPrenda.TABLE_NAME, null, values);
        }
    }



}



