package com.amza.deportegaleana.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.models.ProximosPartidos;

import java.util.ArrayList;

public class AdapterProximosPartidos extends RecyclerView.Adapter<AdapterProximosPartidos.ViewHolderProximosPartidos> {

    ArrayList<ProximosPartidos> listaProximosPartidos;

    public AdapterProximosPartidos(ArrayList<ProximosPartidos> listaProximosPartidos) {
        this.listaProximosPartidos = listaProximosPartidos;
    }

    @Override
    public ViewHolderProximosPartidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listapartidos, null, false);

        return new ViewHolderProximosPartidos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProximosPartidos viewHolderProximosPartidos, int i) {
        viewHolderProximosPartidos.equipoLocal.setText(listaProximosPartidos.get(i).getEquipo1());
        viewHolderProximosPartidos.equipoVisitante.setText(listaProximosPartidos.get(i).getEquipo2());
        viewHolderProximosPartidos.hora.setText(listaProximosPartidos.get(i).getHora());
        viewHolderProximosPartidos.fecha.setText(listaProximosPartidos.get(i).getFecha());
        viewHolderProximosPartidos.lugar.setText(listaProximosPartidos.get(i).getLugar());
        viewHolderProximosPartidos.separador.setImageResource(listaProximosPartidos.get(i).getSeparador());
    }

    @Override
    public int getItemCount() {
        return listaProximosPartidos.size();
    }

    public class ViewHolderProximosPartidos extends RecyclerView.ViewHolder {

        TextView equipoLocal, equipoVisitante, hora, fecha, lugar;
        ImageView separador;

        public ViewHolderProximosPartidos(@NonNull View itemView) {
            super(itemView);
            equipoLocal = itemView.findViewById(R.id.idEquipoLocalListaPartidos);
            equipoVisitante = itemView.findViewById(R.id.idEquipoVisitanteListaPartidos);
            hora = itemView.findViewById(R.id.idHoraListaPartidos);
            fecha = itemView.findViewById(R.id.idFechaListaPartidos);
            lugar = itemView.findViewById(R.id.idLugarListaPartidos);
            separador = itemView.findViewById(R.id.idimagenSeparacionListaPartidos);
        }
    }
}
