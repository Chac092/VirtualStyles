package com.example.ropaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.ropaapp.R.drawable;
import static com.example.ropaapp.R.layout;

public class Adaptador extends RecyclerView.Adapter <Adaptador.ViewHolder> {
    private View.OnClickListener onClickListener;
    private LayoutInflater inflador; //crea layout a partir de xml
    protected ArrayList<String> lista;//libros a visualizar
    private Context contexto;
    public int contador = 0;

    public Adaptador(ArrayList<String> idFotos) {
        this.lista = idFotos;
        this.contexto = contexto;
    }

    //creamos nuestro ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = itemView.findViewById(R.id.Imagerecicle);

        }
    }
    //creamos el viewholder con la vista de un elemento sin personalizar

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        //inflamos vista desde XML
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.elementoselecto, parent, false);
        v.setId(contador);
        v.setOnClickListener(onClickListener);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String numprenda = Activity_Seleccion_Prenda.getIdfotos().get(position);
        Bitmap FTO = BitmapFactory.decodeFile("/storage/emulated/0/saved_images/"+numprenda+".jpg");
        System.out.println("dentro de onbindholder");
        holder.portada.setImageBitmap(FTO);
    }

    @Override
    public int getItemCount() {
        return Activity_Seleccion_Prenda.getIdfotos().size();

    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void ponerfoto(String foto) {
        ImageView raton;

    }
}






