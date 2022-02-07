package com.amza.deportegaleanaadministrador.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.models.AgregarPartido;

import java.util.ArrayList;

public class AdapterAgregarPartidos extends RecyclerView.Adapter<AdapterAgregarPartidos.ViewHolderAgregarPartidos> {

    ArrayList<AgregarPartido> listaAgregarPartido;

    public AdapterAgregarPartidos(ArrayList<AgregarPartido> listaAgregarPartido) {
        this.listaAgregarPartido = listaAgregarPartido;
    }

    @Override
    public ViewHolderAgregarPartidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listaagregarpartido,null,false);
        return new ViewHolderAgregarPartidos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAgregarPartidos viewHolderAgregarPartidos, int i) {
        if(listaAgregarPartido.get(i).getLocalEquipo1().equals("Local")){
            viewHolderAgregarPartidos.equipoLocal.setText(listaAgregarPartido.get(i).getEquipo1());
            viewHolderAgregarPartidos.equipoVisitante.setText(listaAgregarPartido.get(i).getEquipo2());
            viewHolderAgregarPartidos.hora.setText(listaAgregarPartido.get(i).getHora());
            viewHolderAgregarPartidos.fecha.setText(listaAgregarPartido.get(i).getFecha());
            viewHolderAgregarPartidos.lugar.setText(listaAgregarPartido.get(i).getLugar());
            viewHolderAgregarPartidos.separador.setImageResource(listaAgregarPartido.get(i).getSeparador());
        }
        else{
            viewHolderAgregarPartidos.equipoLocal.setText(listaAgregarPartido.get(i).getEquipo2());
            viewHolderAgregarPartidos.equipoVisitante.setText(listaAgregarPartido.get(i).getEquipo1());
            viewHolderAgregarPartidos.hora.setText(listaAgregarPartido.get(i).getHora());
            viewHolderAgregarPartidos.fecha.setText(listaAgregarPartido.get(i).getFecha());
            viewHolderAgregarPartidos.lugar.setText(listaAgregarPartido.get(i).getLugar());
            viewHolderAgregarPartidos.separador.setImageResource(listaAgregarPartido.get(i).getSeparador());
        }
    }

    @Override
    public int getItemCount() {
        return listaAgregarPartido.size();
    }

    public class ViewHolderAgregarPartidos extends RecyclerView.ViewHolder {

        TextView equipoLocal, equipoVisitante, hora, fecha, lugar;
        ImageView separador;

        public ViewHolderAgregarPartidos(@NonNull View itemView) {
            super(itemView);
            equipoLocal = itemView.findViewById(R.id.idEquipoLocalListaAgregarPartido);
            equipoVisitante = itemView.findViewById(R.id.idEquipoVisitanteListaAgregarPartido);
            hora = itemView.findViewById(R.id.idHoraListaAgregarPartido);
            fecha = itemView.findViewById(R.id.idFechaListaAgregarPartido);
            lugar = itemView.findViewById(R.id.idLugarListaAgregarPartido);
            separador = itemView.findViewById(R.id.idimagenSeparacionListaAgregarPartido);

        }
    }
}