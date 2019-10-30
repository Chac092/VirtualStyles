package com.example.ropaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ropaapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Usuario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Usuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Usuario extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText cuota;
    EditText nombre;
    EditText contraseña;
    Spinner usuarios;
    Button borrar;
    Button aceptar;

    ArrayList<String> nombreusu = new ArrayList<>();
    SQLiteDatabase db;
    DBHelper dbHelper;

    private OnFragmentInteractionListener mListener;

    public Fragment_Usuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Administrador.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Usuario newInstance(String param1, String param2) {
        Fragment_Usuario fragment = new Fragment_Usuario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getActivity().getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        cuota = getView().findViewById(R.id.ETsalarioUsuario);
        nombre = getView().findViewById(R.id.ETnombreUsuario);
        contraseña = getView().findViewById(R.id.ETcontraseñaUsuario);

        usuarios = getView().findViewById(R.id.spinner);
        usuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargar_usuario();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aceptar = getView().findViewById(R.id.botonGuardarUsuario);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        borrar = getView().findViewById(R.id.botonEliminarUsuario);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });
        CojerUsuarios();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void CojerUsuarios() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadUsuario._ID};
        String selection = DBHelper.entidadUsuario.COLUMN_NAME_PERFIL + "= ?";
        String[] selectionArgs = {"usuario"};
        String sortOrder = DBHelper.entidadUsuario._ID + " DESC";
        Cursor cursor = db.query(DBHelper.entidadUsuario.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            nombreusu.add(nombre);
            System.out.println(nombre);
        }
        ArrayAdapter dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nombreusu);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        usuarios.setAdapter(dataAdapter);
    }
    public void guardar(){
        nombreusu.clear();
        String dinero = cuota.getText().toString();
        String idusuario = nombre.getText().toString();
        String Contraseña = contraseña.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadUsuario._ID};
        String selection = DBHelper.entidadUsuario._ID + "= ?";
        String[] selectionArgs = {idusuario};
        String sortOrder = DBHelper.entidadUsuario._ID + " DESC";
        Cursor cursor = db.query(DBHelper.entidadUsuario.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        System.out.println(cursor.getCount());
        if (cursor.getCount()>0){
            ContentValues values = new ContentValues();
            values.put(DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA,Contraseña);
            String selectionUpdateUsuario = DBHelper.entidadUsuario._ID+ " LIKE ?";
            String [] selectionArgsUpdateUsuario = {idusuario};
            int countUsuario = db.update(DBHelper.entidadUsuario.TABLE_NAME,values,selectionUpdateUsuario,selectionArgsUpdateUsuario);
            System.out.println(countUsuario);



            ContentValues valuesfacturas = new ContentValues();
            valuesfacturas.put(DBHelper.entidadPrecio.COLUMN_NAME_PRECIO,dinero);
            String selectionUpdateFactura = DBHelper.entidadFactura.COLUMN_NAME_IDUSUARIO+ " LIKE ?";
            String [] selectionArgsUpdateFactura = {"usuario"};
            int countFacturas = db.update(DBHelper.entidadPrecio.TABLE_NAME,valuesfacturas,selectionUpdateFactura,selectionArgsUpdateFactura);
            System.out.println(countFacturas);
        }else{
            String SQL_INSERT_USUARIO =
                    "INSERT INTO " + DBHelper.entidadUsuario.TABLE_NAME + " (" +
                            DBHelper.entidadUsuario._ID + ", " +
                            DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
                            DBHelper.entidadUsuario.COLUMN_NAME_PERFIL + ") " +
                            "VALUES ('" + idusuario + "', '" + Contraseña + "', 'usuario')";
            db.execSQL(SQL_INSERT_USUARIO);
        }
        CojerUsuarios();
    }
    public void cargar_usuario(){
        //pedimos el usuario y contraseña
        String usuario =  usuarios.getSelectedItem().toString();
        String idUsuario="jorje";
        String contraseñausu="jorje";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadUsuario._ID,DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA};
        String selection = DBHelper.entidadUsuario._ID + "= ?";
        String[] selectionArgs = {usuario};
        String sortOrder = DBHelper.entidadUsuario._ID + " DESC";
        Cursor cursor = db.query(DBHelper.entidadUsuario.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        //Guardamos esos datos
        while (cursor.moveToNext()) {
            idUsuario = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadUsuario._ID));
            contraseñausu = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA));
        }



        //Pedimos la cantidad de lo que cobra
        String precio = "90";
        String[] projectionFactura = {DBHelper.entidadPrecio.COLUMN_NAME_PRECIO};
        String selectionFactura = DBHelper.entidadPrecio.COLUMN_NAME_IDUSUARIO + "= ?";
        System.out.println(idUsuario);
        String[] selectionArgsFactura = {"usuario"};
        Cursor cursorFacturas = db.query(DBHelper.entidadPrecio.TABLE_NAME, projectionFactura, selectionFactura, selectionArgsFactura, null, null, null);
        System.out.println(idUsuario);
        System.out.println(cursorFacturas.getCount());
        //Guardamos esos datos
        if (cursorFacturas.moveToNext()) {
            precio = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow(DBHelper.entidadPrecio.COLUMN_NAME_PRECIO));
            System.out.println(precio);
        }


        //Asignamos los datos
        cuota.setText(precio);
        nombre.setText(idUsuario);
        contraseña.setText(contraseñausu);

    }
    public void borrar(){
        nombreusu.clear();
        String idusuario = nombre.getText().toString();
        String selectionborrar = DBHelper.entidadUsuario._ID + " = ?" ;
        String [] selectionArgsBorrar = {idusuario};
        int deletedRows = db.delete(DBHelper.entidadUsuario.TABLE_NAME,selectionborrar,selectionArgsBorrar);
        cargar_usuario();
    }
}

