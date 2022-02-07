package com.amza.deportegaleana.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.adaptadores.AdapterPartidosFinalizados;
import com.amza.deportegaleana.adaptadores.AdapterProximosPartidos;
import com.amza.deportegaleana.models.Goleador;
import com.amza.deportegaleana.models.PartidosFinalizados;
import com.amza.deportegaleana.models.ProximosPartidos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartidosFinalizadosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartidosFinalizadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartidosFinalizadosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private View vista;

    private ArrayList<PartidosFinalizados> listaPartidosFinalizados;

    private RecyclerView recyclerPatidosFinalizados;

    private OnFragmentInteractionListener mListener;

    public PartidosFinalizadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartidosFinalizadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartidosFinalizadosFragment newInstance(String param1, String param2) {
        PartidosFinalizadosFragment fragment = new PartidosFinalizadosFragment();
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
        vista = inflater.inflate(R.layout.fragment_partidos_finalizados, container, false);

        listaPartidosFinalizados = new ArrayList<>();

        recyclerPatidosFinalizados = vista.findViewById(R.id.idRecyclerPartidosFinalizados);
        recyclerPatidosFinalizados.setLayoutManager(new LinearLayoutManager(getContext()));

        iniciarFirebase();
        obtenerResultados();

        return vista;

    }

    private void obtenerResultados() {
        databaseReference.child("Primera Fuerza").child("Resultados Jornadas").orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> finalizados = dataSnapshot.getChildren().iterator();
                listaPartidosFinalizados.clear();
                while (finalizados.hasNext()){
                    DataSnapshot finalizado = finalizados.next();

                    String equipoLocal, equipoVisitante, golesLocal, golesVisitante, hora, fecha, lugar;

                    equipoLocal =finalizado.child("equipoLocal").getValue().toString();
                    equipoVisitante = finalizado.child("equipoVisitante").getValue().toString();
                    golesLocal = finalizado.child("golesLocal").getValue().toString();
                    golesVisitante = finalizado.child("golesVisitante").getValue().toString();
                    hora = finalizado.child("hora").getValue().toString();
                    fecha = finalizado.child("fecha").getValue().toString();
                    lugar = finalizado.child("lugar").getValue().toString();

                    listaPartidosFinalizados.add(new PartidosFinalizados(equipoLocal, equipoVisitante, golesLocal, golesVisitante,
                            hora, fecha, lugar, R.drawable.separacion_lista));

                    AdapterPartidosFinalizados adapterPartidosFinalizados = new AdapterPartidosFinalizados(listaPartidosFinalizados);
                    recyclerPatidosFinalizados.setAdapter(adapterPartidosFinalizados);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
