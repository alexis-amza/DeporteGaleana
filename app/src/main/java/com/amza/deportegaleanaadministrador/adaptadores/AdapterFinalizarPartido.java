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

public class AdapterFinalizarPartido extends RecyclerView.Adapter<AdapterFinalizarPartido.ViewHolderFinalizarPartido>
                                    implements View.OnClickListener {

    ArrayList<AgregarPartido> listaFinalizarPartido;
    private View.OnClickListener listener;

    public AdapterFinalizarPartido(ArrayList<AgregarPartido> listaFinalizarPartido) {
        this.listaFinalizarPartido = listaFinalizarPartido;
    }

    @NonNull
    @Override
    public AdapterFinalizarPartido.ViewHolderFinalizarPartido onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listaagregarpartido,null,false);

        vista.setOnClickListener(this);
        return new ViewHolderFinalizarPartido(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFinalizarPartido.ViewHolderFinalizarPartido viewHolderFinalizarPartido, int i) {
        viewHolderFinalizarPartido.local.setText(listaFinalizarPartido.get(i).getEquipo1());
        viewHolderFinalizarPartido.visitante.setText(listaFinalizarPartido.get(i).getEquipo2());
        viewHolderFinalizarPartido.hora.setText(listaFinalizarPartido.get(i).getHora());
        viewHolderFinalizarPartido.fecha.setText(listaFinalizarPartido.get(i).getFecha());
        viewHolderFinalizarPartido.lugar.setText(listaFinalizarPartido.get(i).getLugar());
        viewHolderFinalizarPartido.separador.setImageResource(listaFinalizarPartido.get(i).getSeparador());
    }

    @Override
    public int getItemCount() {
        return listaFinalizarPartido.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderFinalizarPartido extends RecyclerView.ViewHolder {
        TextView local, visitante, hora, fecha, lugar;
        ImageView separador;

        public ViewHolderFinalizarPartido(@NonNull View itemView) {
            super(itemView);
            local = itemView.findViewById(R.id.idEquipoLocalListaAgregarPartido);
            visitante = itemView.findViewById(R.id.idEquipoVisitanteListaAgregarPartido);
            hora = itemView.findViewById(R.id.idHoraListaAgregarPartido);
            fecha = itemView.findViewById(R.id.idFechaListaAgregarPartido);
            lugar = itemView.findViewById(R.id.idLugarListaAgregarPartido);
            separador = itemView.findViewById(R.id.idimagenSeparacionListaAgregarPartido);
        }
    }
}
