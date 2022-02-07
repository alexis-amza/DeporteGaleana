package com.amza.deportegaleanaadministrador.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.adaptadores.AdapterFinalizarPartido;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.models.ActualizarTablaGeneralLocal;
import com.amza.deportegaleanaadministrador.models.ActualizarTablaGeneralVisitante;
import com.amza.deportegaleanaadministrador.models.AgregarPartido;
import com.amza.deportegaleanaadministrador.models.FinalizarPartido;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    FirebaseAnalytics firebaseAnalytics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<AgregarPartido> listaPartidos;
    ArrayList<ActualizarTablaGeneralLocal> listaActualizarTabla;

    RecyclerView recyclerPartidosFinalizados;

    int ptsL, ptsV,JJL, JJV, JGL, JGV, JEL, JEV, JPL, JPV, GFL, GFV, GCL, GCV, DifL, DifV,
            ptsLocal, ptsVisitante,JJLocal, JJVisitante, JGLocal, JGVisitante, JELocal, JEVisitante, JPLocal,
            JPVisitante, GFLocal, GFVisitante, GCLocal, GCVisitante, DifLocal, DifVisitante;


    public String Jornada, numPartido, GolesLocal, GolesVisitante, EquipoLocal, EquipoVisitante, Hora, Fecha, Lugar;

    View vista;
    TextView txtEquipoLocalPartidosFinalzados, txtEquipoVisiantePartidosFinalizados,
            txtHoraPartidosFinalizados, txtFechaPartidosFinalizados, txtLugaPartidosFinalizados;
    EditText txtGolesLocalPartidosFinalizados, txtGolesVisitantePartidosFinalizados;
    Button btnGuardarFinalizadPartidos;


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
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_partidos_finalizados, container, false);

        listaPartidos = new ArrayList<>();
        listaActualizarTabla = new ArrayList<>();

        recyclerPartidosFinalizados = vista.findViewById(R.id.idRecyclerPartidosFinalizados);
        recyclerPartidosFinalizados.setLayoutManager(new LinearLayoutManager(getContext()));

        txtEquipoLocalPartidosFinalzados = vista.findViewById(R.id.idtxtEquipoLocalPartidosFinalizados);
        txtEquipoVisiantePartidosFinalizados = vista.findViewById(R.id.idtxtEquipoVisitantePartidosFinalizados);
        txtHoraPartidosFinalizados = vista.findViewById(R.id.idtxtHoraPartidosFinalizados);
        txtFechaPartidosFinalizados = vista.findViewById(R.id.idtxtFechaPartidosFinalizados);
        txtLugaPartidosFinalizados = vista.findViewById(R.id.idtxtLugarPartidosFinalizados);
        txtGolesLocalPartidosFinalizados = vista.findViewById(R.id.idtxtGolesLocalPartidosFinalizados);
        txtGolesVisitantePartidosFinalizados = vista.findViewById(R.id.idtxtGolesVisitantePartidosFinalizados);

        btnGuardarFinalizadPartidos = vista.findViewById(R.id.idbtnGuardarResultadoPartidos);

        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerPartidos();
        eventoBotones();


        return vista;
    }

    private void asignarValoresPreferencias() {
        Drawable shape = (Drawable) btnGuardarFinalizadPartidos.getBackground();
        shape.setColorFilter(getResources().getColor(preferencias.colorTema), PorterDuff.Mode.SRC);
    }

    private void eventoBotones() {

        btnGuardarFinalizadPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GolesLocal = txtGolesLocalPartidosFinalizados.getText().toString();
                GolesVisitante = txtGolesVisitantePartidosFinalizados.getText().toString();
                Hora = txtHoraPartidosFinalizados.getText().toString();
                Fecha = txtFechaPartidosFinalizados.getText().toString();
                Lugar = txtLugaPartidosFinalizados.getText().toString();

                if(GolesLocal.equals("")||GolesVisitante.equals("")|| (EquipoLocal.equals("Equipo Local")&& EquipoVisitante.equals("Equipo Visitante")
                        && Hora.equals("00:00")&& Fecha.equals("00/00/0000")&&Lugar.equals("Lugar de juego"))){
                    validacion();
                }
                else{
                    FinalizarPartido FP = new FinalizarPartido();
                    FP.setEquipoLocal(EquipoLocal);
                    FP.setEquipoVisitante(EquipoVisitante);
                    FP.setGolesLocal(GolesLocal);
                    FP.setGolesVisitante(GolesVisitante);
                    FP.setHora(Hora);
                    FP.setFecha(Fecha);
                    FP.setLugar(Lugar);
                    FP.setJornada(Jornada);
                    FP.setNumPartido(numPartido);
                    databaseReference.child("Primera Fuerza").child("Resultados Jornadas").child("Partido "+FP.getJornada()+FP.getNumPartido()).setValue(FP);

                    Toast.makeText(getContext(),"Resultado del partido guardado con éxito",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    validarGanador();
                }
            }
        });

    }


    private void obtenerDatosLocal() {

        databaseReference.child("Primera Fuerza").child("Tabla General").child(EquipoLocal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   if(dataSnapshot.exists()){
                    ptsLocal = Integer.parseInt(dataSnapshot.child("pts").getValue().toString());
                    JJLocal = Integer.parseInt(dataSnapshot.child("jj").getValue().toString());
                    JGLocal = Integer.parseInt(dataSnapshot.child("jg").getValue().toString());
                    JELocal = Integer.parseInt(dataSnapshot.child("je").getValue().toString());
                    JPLocal = Integer.parseInt(dataSnapshot.child("jp").getValue().toString());
                    GFLocal = Integer.parseInt(dataSnapshot.child("gf").getValue().toString());
                    GCLocal = Integer.parseInt(dataSnapshot.child("gc").getValue().toString());
                    DifLocal = Integer.parseInt(dataSnapshot.child("dif").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Primera Fuerza").child("Tabla General").child(EquipoVisitante).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ptsVisitante = Integer.parseInt(dataSnapshot.child("pts").getValue().toString());
                    JJVisitante = Integer.parseInt(dataSnapshot.child("jj").getValue().toString());
                    JGVisitante = Integer.parseInt(dataSnapshot.child("jg").getValue().toString());
                    JEVisitante = Integer.parseInt(dataSnapshot.child("je").getValue().toString());
                    JPVisitante = Integer.parseInt(dataSnapshot.child("jp").getValue().toString());
                    GFVisitante = Integer.parseInt(dataSnapshot.child("gf").getValue().toString());
                    GCVisitante = Integer.parseInt(dataSnapshot.child("gc").getValue().toString());
                    DifVisitante = Integer.parseInt(dataSnapshot.child("dif").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void validarGanador() {

        if(Integer.parseInt(GolesLocal)>Integer.parseInt(GolesVisitante)){
            ptsL = ptsLocal + 3;
            JJL = JJLocal + 1;
            JGL = JGLocal + 1;
            JEL = JELocal;
            JPL = JPLocal;
            GFL = GFLocal + Integer.parseInt(GolesLocal);
            GCL = GCLocal + Integer.parseInt(GolesVisitante);
            DifL = DifLocal+(Integer.parseInt(GolesLocal)-Integer.parseInt(GolesVisitante));

            ptsV = ptsVisitante;
            JJV = JJVisitante + 1;
            JGV = JGVisitante;
            JEV = JEVisitante;
            JPV = JPVisitante + 1;
            GFV = GFVisitante + Integer.parseInt(GolesVisitante);
            GCV = GCVisitante + Integer.parseInt(GolesLocal);
            DifV = DifVisitante+(Integer.parseInt(GolesVisitante)-Integer.parseInt(GolesLocal));
        }

        else if(Integer.parseInt(GolesVisitante)>Integer.parseInt(GolesLocal)){
            ptsV = ptsVisitante + 3;
            JJV = JJVisitante + 1;
            JGV = JGVisitante + 1;
            JEV = JEVisitante;
            JPV = JPVisitante;
            GFV = GFVisitante + Integer.parseInt(GolesVisitante);
            GCV = GCVisitante + Integer.parseInt(GolesLocal);
            DifV = DifVisitante+(Integer.parseInt(GolesVisitante)-Integer.parseInt(GolesLocal));

            ptsL = ptsLocal;
            JJL = JJLocal + 1;
            JGL = JGLocal;
            JEL = JELocal;
            JPL = JPLocal + 1;
            GFL = GFLocal + Integer.parseInt(GolesLocal);
            GCL = GCLocal + Integer.parseInt(GolesVisitante);
            DifL = DifLocal+(Integer.parseInt(GolesLocal)-Integer.parseInt(GolesVisitante));
        }

        else if(GolesLocal.equals(GolesVisitante)){
            ptsL = ptsLocal + 1;
            JJL = JJLocal + 1;
            JGL = JGLocal;
            JEL = JELocal + 1;
            JPL = JPLocal;
            GFL = GFLocal + Integer.parseInt(GolesLocal);
            GCL = GCLocal + Integer.parseInt(GolesVisitante);
            DifL = DifLocal+(Integer.parseInt(GolesLocal)-Integer.parseInt(GolesVisitante));

            ptsV = ptsVisitante + 1;
            JJV = JJVisitante + 1;
            JGV = JGVisitante;
            JEV = JEVisitante + 1;
            JPV = JPVisitante;
            GFV = GFVisitante + Integer.parseInt(GolesVisitante);
            GCV = GCVisitante + Integer.parseInt(GolesLocal);
            DifV = DifVisitante+(Integer.parseInt(GolesVisitante)-Integer.parseInt(GolesLocal));
        }

        actualizarTablaGeneralLocal();

    }

    private void actualizarTablaGeneralLocal() {

        if(EquipoLocal.equals("Descansa")||EquipoVisitante.equals("Descansa")){

        }else{
            Map<String, Object> equipoLocal = new HashMap<>();

            equipoLocal.put("equipo", EquipoLocal);
            equipoLocal.put("pts", ptsL);
            equipoLocal.put("jj", JJL);
            equipoLocal.put("jg", JGL);
            equipoLocal.put("je", JEL);
            equipoLocal.put("jp", JPL);
            equipoLocal.put("gf", GFL);
            equipoLocal.put("gc", GCL);
            equipoLocal.put("dif", DifL);

            databaseReference.child("Primera Fuerza").child("Tabla General").child(EquipoLocal).updateChildren(equipoLocal);

        }

        actualizarTablaGeneralVisitante();

    }

    private void actualizarTablaGeneralVisitante() {

        if(EquipoLocal.equals("Descansa")||EquipoVisitante.equals("Descansa")){

        }else{
            Map<String, Object> equipoVisitante = new HashMap<>();

            equipoVisitante.put("equipo", EquipoVisitante);
            equipoVisitante.put("pts", ptsV);
            equipoVisitante.put("jj", JJV);
            equipoVisitante.put("jg", JGV);
            equipoVisitante.put("je", JEV);
            equipoVisitante.put("jp", JPV);
            equipoVisitante.put("gf", GFV);
            equipoVisitante.put("gc", GCV);
            equipoVisitante.put("dif", DifV);

            databaseReference.child("Primera Fuerza").child("Tabla General").child(EquipoVisitante).updateChildren(equipoVisitante);
        }

    }


    private void limpiarCampos() {
        txtEquipoLocalPartidosFinalzados.setText("Equipo Local");
        txtEquipoVisiantePartidosFinalizados.setText("Equipo Visitante");
        txtHoraPartidosFinalizados.setText("00:00");
        txtFechaPartidosFinalizados.setText("00/00/0000");
        txtLugaPartidosFinalizados.setText("Lugar de juego");
        txtGolesLocalPartidosFinalizados.setText("");
        txtGolesVisitantePartidosFinalizados.setText("");
    }


    private void validacion() {
        if(GolesLocal.equals("")){
            txtGolesLocalPartidosFinalizados.setError("Es necesario capturar los goles del equipo local");
            dialogoAlertaGolesLocal().show();
        }
        if(GolesVisitante.equals("")){
            txtGolesVisitantePartidosFinalizados.setError("Es necesario capturar los goles del equipo visitante");
            dialogoAlertaGolesVisitante().show();
        }
        if(EquipoLocal.equals("Equipo Local")&& EquipoVisitante.equals("Equipo Visitante")
                && Hora.equals("00:00")&& Fecha.equals("00/00/0000")&&Lugar.equals("Lugar de juego")){
            dialogoAlertaPartidoNoSeleccionado().show();
        }

    }


    public AlertDialog dialogoAlertaGolesLocal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de ingresar los goles del equipo local").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    public AlertDialog dialogoAlertaGolesVisitante(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de ingresar los goles del equipo visitante").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    public AlertDialog dialogoAlertaPartidoNoSeleccionado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes seleccionar el partido al que registraras su resultado de la lista inferior").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    private void obtenerPartidos() {
        databaseReference.child("Primera Fuerza").child("Proximos Partidos").orderByChild("hora").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> partidos = dataSnapshot.getChildren().iterator();
                listaPartidos.clear();
                while (partidos.hasNext()){
                    DataSnapshot partido = partidos.next();

                    String Jornada, numPartido, Equipo1, Equipo2, EquipoLocal1, EquipoLocal2, Hora, Fecha, lugar;

                    Jornada = partido.child("jornada").getValue().toString();
                    numPartido = partido.child("numPartido").getValue().toString();
                    Equipo1 = partido.child("equipo1").getValue().toString();
                    Equipo2 = partido.child("equipo2").getValue().toString();
                    EquipoLocal1 = partido.child("localEquipo1").getValue().toString();
                    EquipoLocal2 = partido.child("localEquipo2").getValue().toString();
                    Hora = partido.child("hora").getValue().toString();
                    Fecha = partido.child("fecha").getValue().toString();
                    lugar = partido.child("lugar").getValue().toString();

                    listaPartidos.add(new AgregarPartido(Jornada, numPartido, Equipo1, Equipo2, EquipoLocal1, EquipoLocal2, Hora,
                            Fecha, lugar, R.drawable.separacion_lista));
                }
                AdapterFinalizarPartido adapterFinalizarPartido = new AdapterFinalizarPartido(listaPartidos);
                adapterFinalizarPartido.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtEquipoLocalPartidosFinalzados.setText(listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getEquipo1());
                        txtEquipoVisiantePartidosFinalizados.setText(listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getEquipo2());
                        txtHoraPartidosFinalizados.setText(listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getHora());
                        txtFechaPartidosFinalizados.setText(listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getFecha());
                        txtLugaPartidosFinalizados.setText(listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getLugar());
                        Jornada = listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getJornada();
                        numPartido = listaPartidos.get(recyclerPartidosFinalizados.getChildAdapterPosition(v)).getNumPartido();

                        EquipoLocal = txtEquipoLocalPartidosFinalzados.getText().toString();
                        EquipoVisitante = txtEquipoVisiantePartidosFinalizados.getText().toString();
                        obtenerDatosLocal();
                    }
                });
                recyclerPartidosFinalizados.setAdapter(adapterFinalizarPartido);
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
