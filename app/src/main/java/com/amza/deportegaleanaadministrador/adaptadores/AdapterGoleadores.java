package com.amza.deportegaleanaadministrador.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.models.Goleador;

import java.util.ArrayList;

public class AdapterGoleadores extends RecyclerView.Adapter<AdapterGoleadores.ViewHolderGoleadores>
                implements View.OnClickListener {

    ArrayList<Goleador> listaGolaeadores;
    private View.OnClickListener listener;

    public AdapterGoleadores(ArrayList<Goleador> listaGolaeadores) {
        this.listaGolaeadores = listaGolaeadores;
    }

    @Override
    public ViewHolderGoleadores onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listagoleadores,null,false);

        vista.setOnClickListener(this);

        return new ViewHolderGoleadores(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGoleadores viewHolderGoleadores, int i) {
        viewHolderGoleadores.NombreGoleador.setText(listaGolaeadores.get(i).getNombre());
        viewHolderGoleadores.GolesGoleador.setText(listaGolaeadores.get(i).getGoles());
        viewHolderGoleadores.EquipoGoleador.setText(listaGolaeadores.get(i).getEquipo());
        viewHolderGoleadores.logoGoleador.setImageResource(listaGolaeadores.get(i).getImagen());
        viewHolderGoleadores.Separador.setImageResource(listaGolaeadores.get(i).getSeparacion());
    }

    @Override
    public int getItemCount() {
        return listaGolaeadores.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderGoleadores extends RecyclerView.ViewHolder {

        TextView NombreGoleador, GolesGoleador, EquipoGoleador;
        ImageView logoGoleador, Separador;

        public ViewHolderGoleadores(@NonNull View itemView) {
            super(itemView);
            NombreGoleador = itemView.findViewById(R.id.idNombreListaGoleador);
            GolesGoleador = itemView.findViewById(R.id.idGolesListaGoleador);
            EquipoGoleador = itemView.findViewById(R.id.idEquipoListaGoleador);
            logoGoleador = itemView.findViewById(R.id.idImagenListaGoleadores);
            Separador = itemView.findViewById(R.id.idImagenSeparacionListaGoleador);
        }

    }
}
