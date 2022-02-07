package com.amza.deportegaleana.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.models.PartidosFinalizados;

import java.util.ArrayList;

public class AdapterPartidosFinalizados extends RecyclerView.Adapter<AdapterPartidosFinalizados.ViewHolderPartidosFinalizados> {


    ArrayList<PartidosFinalizados> listaPartidosFinalizados;

    public AdapterPartidosFinalizados(ArrayList<PartidosFinalizados> listaPartidosFinalizados) {
        this.listaPartidosFinalizados = listaPartidosFinalizados;
    }

    @NonNull
    @Override
    public ViewHolderPartidosFinalizados onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listapartidosfinalizados, null, false);

        return new ViewHolderPartidosFinalizados(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPartidosFinalizados viewHolderPartidosFinalizados, int i) {
        viewHolderPartidosFinalizados.equipoLocal.setText(listaPartidosFinalizados.get(i).getEquipoLocal());
        viewHolderPartidosFinalizados.equipoVisitante.setText(listaPartidosFinalizados.get(i).getEquipoVisitante());
        viewHolderPartidosFinalizados.golesLocal.setText(listaPartidosFinalizados.get(i).getGolesLocal());
        viewHolderPartidosFinalizados.golesVisitante.setText(listaPartidosFinalizados.get(i).getGolesVisitante());
        viewHolderPartidosFinalizados.hora.setText(listaPartidosFinalizados.get(i).getHora());
        viewHolderPartidosFinalizados.fecha.setText(listaPartidosFinalizados.get(i).getFecha());
        viewHolderPartidosFinalizados.campo.setText(listaPartidosFinalizados.get(i).getCampo());
        viewHolderPartidosFinalizados.separador.setImageResource(listaPartidosFinalizados.get(i).getSeparador());
    }

    @Override
    public int getItemCount() {
        return listaPartidosFinalizados.size();
    }

    public class ViewHolderPartidosFinalizados extends RecyclerView.ViewHolder {

        TextView equipoLocal, equipoVisitante, golesLocal, golesVisitante, hora, fecha, campo;
        ImageView separador;

        public ViewHolderPartidosFinalizados(@NonNull View itemView) {
            super(itemView);

            equipoLocal = itemView.findViewById(R.id.idtxtEquipoLocalListaFinalizados);
            equipoVisitante = itemView.findViewById(R.id.idtxtEquipoVisitanteListaFinalizados);
            golesLocal = itemView.findViewById(R.id.idtxtGolesLocalListaFinalizados);
            golesVisitante = itemView.findViewById(R.id.idtxtGolesVisitanteListaFinalizados);
            hora = itemView.findViewById(R.id.idHoraListaFinalizados);
            fecha = itemView.findViewById(R.id.idFechaListaFinalizados);
            campo = itemView.findViewById(R.id.idLugarListaFinalizados);
            separador = itemView.findViewById(R.id.idimagenSeparacionListaFinalizados);

        }
    }
}
