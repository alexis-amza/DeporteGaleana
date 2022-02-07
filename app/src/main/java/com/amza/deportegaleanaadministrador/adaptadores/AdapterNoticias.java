package com.amza.deportegaleanaadministrador.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.models.ObtenerNoticia;

import java.util.ArrayList;

public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.ViewHolderNoticias> {

    ArrayList<ObtenerNoticia> listaNoticias;

    public AdapterNoticias(ArrayList<ObtenerNoticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }


    @NonNull
    @Override
    public ViewHolderNoticias onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listanoticias, null, false);

        return new ViewHolderNoticias(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNoticias viewHolderNoticias, int i) {
        viewHolderNoticias.titulo.setText(listaNoticias.get(i).getTitulo());
        viewHolderNoticias.fecha.setText(listaNoticias.get(i).getFecha());
        viewHolderNoticias.cuerpo.setText(listaNoticias.get(i).getCuerpo());
        viewHolderNoticias.separador.setImageResource(listaNoticias.get(i).getSeparacion());
        viewHolderNoticias.logoNoticia.setImageResource(listaNoticias.get(i).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }


    public class ViewHolderNoticias extends RecyclerView.ViewHolder {

        TextView titulo, fecha, cuerpo;
        ImageView separador, logoNoticia;

        public ViewHolderNoticias(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.idtxtTituloNoticiaLista);
            fecha = itemView.findViewById(R.id.idFechaNoticiaLista);
            cuerpo = itemView.findViewById(R.id.idCuerpoNoticiaLista);
            separador = itemView.findViewById(R.id.idImagenSeparacionNoticiaLista);
            logoNoticia = itemView.findViewById(R.id.idImagenNoticiaLista);
        }
    }
}
