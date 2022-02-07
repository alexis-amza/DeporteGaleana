package com.amza.deportegaleanaadministrador.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.models.TablaGeneral;

import java.util.ArrayList;

public class AdapterTablaGeneral extends RecyclerView.Adapter<AdapterTablaGeneral.ViewHolderTablaGeneral> {

    ArrayList<TablaGeneral> listaTablaGeneral;

    public AdapterTablaGeneral(ArrayList<TablaGeneral> listaTablaGeneral) {
        this.listaTablaGeneral = listaTablaGeneral;
    }

    @NonNull
    @Override
    public ViewHolderTablaGeneral onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listatablageneral, null, false);
        return new ViewHolderTablaGeneral(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTablaGeneral viewHolderTablaGeneral, int i) {
        viewHolderTablaGeneral.equipo.setText(listaTablaGeneral.get(i).getEquipo());
        viewHolderTablaGeneral.pts.setText(listaTablaGeneral.get(i).getPts());
        viewHolderTablaGeneral.jj.setText(listaTablaGeneral.get(i).getJj());
        viewHolderTablaGeneral.jg.setText(listaTablaGeneral.get(i).getJg());
        viewHolderTablaGeneral.je.setText(listaTablaGeneral.get(i).getJe());
        viewHolderTablaGeneral.jp.setText(listaTablaGeneral.get(i).getJp());
        viewHolderTablaGeneral.gf.setText(listaTablaGeneral.get(i).getGf());
        viewHolderTablaGeneral.gc.setText(listaTablaGeneral.get(i).getGc());
        viewHolderTablaGeneral.dif.setText(listaTablaGeneral.get(i).getDif());
    }

    @Override
    public int getItemCount() {
        return listaTablaGeneral.size();
    }

    public class ViewHolderTablaGeneral extends RecyclerView.ViewHolder {

        TextView equipo, pts, jj, jg, je, jp, gf,gc,dif;

        public ViewHolderTablaGeneral(@NonNull View itemView) {
            super(itemView);

            equipo = itemView.findViewById(R.id.idEquipoListaTablaGeneral);
            pts = itemView.findViewById(R.id.idPtsListaTablaGeneral);
            jj = itemView.findViewById(R.id.idJJListaTablaGeneral);
            jg = itemView.findViewById(R.id.idJGListaTablaGeneral);
            je = itemView.findViewById(R.id.idJEListaTablaGeneral);
            jp = itemView.findViewById(R.id.idJPListaTablaGeneral);
            gf = itemView.findViewById(R.id.idGFListaTablaGeneral);
            gc = itemView.findViewById(R.id.idGCListaTablaGeneral);
            dif = itemView.findViewById(R.id.idDifListaTablaGeneral);
        }
    }
}
