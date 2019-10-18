package com.example.ropaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "XopAppDB.db";

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

    private static final String SQL_CREATE_TABLE_FACTURAS =
            "CREATE TABLE " + entidadFactura.TABLE_NAME + " (" +
                    entidadFactura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadFactura.COLUMN_NAME_IMPORTE + " INTEGER," +
                    entidadFactura.COLUMN_NAME_FECHA + " TEXT," +
                    entidadFactura.COLUMN_NAME_IDUSUARIO + " INTEGER," +
                    "FOREIGN KEY('" + entidadFactura.COLUMN_NAME_IDUSUARIO + "') REFERENCES '" + entidadUsuario.TABLE_NAME + "'('" + entidadUsuario._ID + "'))";

    private static final String SQL_DELETE_TABLE_FACTURAS = "DROP TABLE IF EXISTS " +  entidadUsuario.TABLE_NAME;

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
        db.execSQL(SQL_INSERT_ADAN);//TODO Chapuza
        db.execSQL(SQL_INSERT_EVA);//TODO Chapuza
        db.execSQL(SQL_INSERT_ESTILISTA); //TODO Chapuza


    }

    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_TABLE_CONJUNTO);
        db.execSQL(SQL_DELETE_TABLE_PRENDA);
        db.execSQL(SQL_DELETE_TABLE_TARJETA);
        db.execSQL(SQL_DELETE_TABLE_USUARIO);
        db.execSQL(SQL_DELETE_TABLE_FACTURAS);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}



