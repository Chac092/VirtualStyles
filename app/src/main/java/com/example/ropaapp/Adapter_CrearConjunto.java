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

public class Adapter_CrearConjunto extends RecyclerView.Adapter<Adapter_CrearConjunto.ViewHolder> {
    //Globales
    private View.OnClickListener onClickListener;
    private LayoutInflater inflador; //crea layout a partir de xml
    protected ArrayList<String> prendas;//prendas a visualizar
    private Context contexto;
    public int contador = 0;

    public Adapter_CrearConjunto(ArrayList<String> prendas) {
        this.prendas = prendas;
        this.contexto = contexto;
    }

    //creamos nuestro ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagenPrendaRecyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            imagenPrendaRecyclerView = itemView.findViewById(R.id.imagenPrendaRecyclerView);
        }
    }

    //creamos el viewholder con la vista de un elemento sin personalizar
    @Override
    public Adapter_CrearConjunto.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        //inflamos vista desde XML
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prenda_reciclerview, parent, false);
        v.setId(contador);
        v.setOnClickListener(onClickListener);
        return new Adapter_CrearConjunto.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Adapter_CrearConjunto.ViewHolder holder, int position) {
        String miPrenda = Activity_CrearConjunto.getMisPrendas().get(position);
        String path = "/storage/emulated/0/saved_images/" + miPrenda + ".jpg";
        Bitmap imagenPrenda = BitmapFactory.decodeFile(path);
        holder.imagenPrendaRecyclerView.setImageBitmap(imagenPrenda);
    }

    @Override
    public int getItemCount() {
        return Activity_CrearConjunto.getMisPrendas().size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
