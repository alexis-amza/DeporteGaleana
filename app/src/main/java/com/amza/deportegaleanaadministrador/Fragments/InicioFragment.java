package com.amza.deportegaleanaadministrador.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.actividades.TablaGeneralActivity;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.interfaces.IComunicaFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private View vista;
    private Activity actividad;
    private CardView cardTablaGeneral, cardPartidos, cardAvisos, cardGoleadores, cardAjustes, cardAcercaDe;
    private IComunicaFragments interfaceComunicaFragments;
    private RelativeLayout fondoMenu, fondoBanner;


    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        vista =inflater.inflate(R.layout.fragment_inicio, container, false);

        cardTablaGeneral = vista.findViewById(R.id.cardTablaGeneral);
        cardPartidos = vista.findViewById(R.id.cardPartidos);
        cardAvisos = vista.findViewById(R.id.cardAvisos);
        cardGoleadores = vista.findViewById(R.id.cardGoleadores);
        cardAjustes = vista.findViewById(R.id.cardAjustes);
        cardAcercaDe = vista.findViewById(R.id.cardAcercaDe);
        fondoMenu = vista.findViewById(R.id.idLayoutFondoMenu);
        fondoBanner = vista.findViewById(R.id.idbannerInicio);


        eventosMenu();

        return vista;
    }

    @Override
    public void onResume() {
        asignarValoresPreferencias();
        super.onResume();
    }

    private void asignarValoresPreferencias() {
        Drawable shape = (Drawable) fondoBanner.getBackground();
        shape.setColorFilter(getResources().getColor(preferencias.colorTema), PorterDuff.Mode.SRC);
    }

    private void eventosMenu() {

       cardTablaGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirTablaGeneral();
            }
        });


        cardPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirPartidos();
            }
        });

        cardAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirAvisos();
            }
        });

        cardGoleadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirGoleadores();
            }
        });

        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirAjustes();
            }
        });

        cardAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceComunicaFragments.abrirAcercaDe();
            }
        });

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
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) actividad;
        }
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
}
