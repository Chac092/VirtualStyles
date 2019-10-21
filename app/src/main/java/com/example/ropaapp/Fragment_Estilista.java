package com.example.ropaapp;

import android.R.layout;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Estilista.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Estilista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Estilista extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner usuarios;
    Button borrar;
    Button aceptar;
    DBHelper dbHelper;
    ArrayList<String> nombreusu = new ArrayList<>();
    SQLiteDatabase db;
    EditText salario;
    EditText nombre;
    EditText contraseña;
    private OnFragmentInteractionListener mListener;

    public Fragment_Estilista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Estilista.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Estilista newInstance(String param1, String param2) {
        Fragment_Estilista fragment = new Fragment_Estilista();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__estilista, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Creation of an instance of SQLiteOpenHelper type Class object (DatabaseOpenHelper)
        dbHelper = new DBHelper(getActivity().getBaseContext());
        //We get a writable database. If not exist, onCreate is called
        db = dbHelper.getWritableDatabase();
        salario = getView().findViewById(R.id.ETsalarioEstilista);
        nombre = getView().findViewById(R.id.ETnombreEstilista);
        contraseña = getView().findViewById(R.id.ETcontraseñaEstilista);

        usuarios = getView().findViewById(R.id.spinner);
        usuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aceptar = getView().findViewById(R.id.botonGuardarEstilista);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        borrar = getView().findViewById(R.id.botonEliminarEstilista);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CojerEstilistas();
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
    public void CojerEstilistas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.entidadUsuario._ID};
        String selection = DBHelper.entidadUsuario.COLUMN_NAME_PERFIL + "= ?";
        String[] selectionArgs = {"estilista"};
        String sortOrder = DBHelper.entidadUsuario._ID + " DESC";
        Cursor cursor = db.query(DBHelper.entidadUsuario.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.entidadPrenda._ID));
            nombreusu.add(nombre);
            System.out.println(nombre);
        }
        ArrayAdapter adaptador = new ArrayAdapter<String>(getContext(), layout.simple_spinner_dropdown_item, nombreusu);
        usuarios.setAdapter(adaptador);
    }
    public void guardar(){
        String dinero = salario.getText().toString();
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
            valuesfacturas.put(DBHelper.entidadFactura.COLUMN_NAME_IMPORTE,dinero);
            String selectionUpdateFactura = DBHelper.entidadFactura.COLUMN_NAME_IDUSUARIO+ " LIKE ?";
            String [] selectionArgsUpdateFactura = {idusuario};
            int countFacturas = db.update(DBHelper.entidadFactura.TABLE_NAME,valuesfacturas,selectionUpdateFactura,selectionArgsUpdateFactura);
            System.out.println(countFacturas);
        }else{
             String SQL_INSERT_ESTILISTA =
                    "INSERT INTO " + DBHelper.entidadUsuario.TABLE_NAME + " (" +
                            DBHelper.entidadUsuario._ID + ", " +
                            DBHelper.entidadUsuario.COLUMN_NAME_CONTRASENYA + ", " +
                            DBHelper.entidadUsuario.COLUMN_NAME_PERFIL + ") " +
                            "VALUES ('" + idusuario + "', '" + Contraseña + "', 'estilista')";
             db.execSQL(SQL_INSERT_ESTILISTA);
        }
    }
}
