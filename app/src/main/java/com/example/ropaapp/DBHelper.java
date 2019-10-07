package com.example.ropaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "XopAppDB.db";


    public static class entidadUsuario implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        //public static final String COLUMN_NAME_IDUSUARIO = "idUsuario";
        public static final String COLUMN_NAME_PERFIL = "Perfil";
        public static final String COLUMN_NAME_CONTRASENYA = "Contraseña";
    }

    public static class entidadPrenda implements BaseColumns {
        public static final String TABLE_NAME = "prenda";
        //public static final String COLUMN_NAME_IDPRENDA = "idPrenda";
        public static final String COLUMN_NAME_CATEGORIA = "Categoria";
        public static final String COLUMN_NAME_ESTILO = "Estilo";
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
                    entidadUsuario._ID + " INTEGER PRIMARY KEY," +
                    entidadUsuario.COLUMN_NAME_CONTRASENYA + " TEXT," +
                    entidadUsuario.COLUMN_NAME_PERFIL + " TEXT)";

    private static final String SQL_DELETE_TABLE_USUARIO =
            "DROP TABLE IF EXISTS " + entidadUsuario.TABLE_NAME;


    private static final String SQL_CREATE_TABLE_PRENDA =
            "CREATE TABLE " + entidadPrenda.TABLE_NAME + " (" +
                    entidadPrenda._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    entidadPrenda.COLUMN_NAME_CATEGORIA + " TEXT," +
                    entidadPrenda.COLUMN_NAME_ESTILO + " TEXT," +
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


    //Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TABLE_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_PRENDA);
        db.execSQL(SQL_CREATE_TABLE_CONJUNTO);
    }

    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_TABLE_CONJUNTO);
        db.execSQL(SQL_DELETE_TABLE_PRENDA);
        db.execSQL(SQL_DELETE_TABLE_USUARIO);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}



