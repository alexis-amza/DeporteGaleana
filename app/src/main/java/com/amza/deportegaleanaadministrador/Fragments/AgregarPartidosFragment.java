package com.amza.deportegaleanaadministrador.Fragments;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.adaptadores.AdapterAgregarPartidos;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.models.AgregarPartido;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgregarPartidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgregarPartidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarPartidosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<AgregarPartido> listaFinal;
    ArrayList<AgregarPartido> listaAgregarPartido;
    RecyclerView recyclerViewAgregarPartido;

    FirebaseAnalytics firebaseAnalytics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    private int sYear, sMonth, sDay;


    View vista;
    Spinner spinnerEquipos1, spinnerEquipos2, spinnerHoras, spinnerLugarDeJuego;
    EditText txtFechaAgregarPartido, txtNumeroJornadaAgregarPartido, txtNumeroPartidoAgregarPartido;
    ImageButton btnCalendrarioAgregarPartido;
    Button btnGuardarPartidos;
    RadioButton RadioBtnEquipo1, RadioBtnEquipo2;

    String numJornada, numPartido, equipo1, equipo2, localEquipo1="", localEquipo2="", hora, fecha, lugar;

    private OnFragmentInteractionListener mListener;

    public AgregarPartidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarPartidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarPartidosFragment newInstance(String param1, String param2) {
        AgregarPartidosFragment fragment = new AgregarPartidosFragment();
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

        vista = inflater.inflate(R.layout.fragment_agregar_partidos, container, false);

        listaFinal = new ArrayList<>();
        listaAgregarPartido = new ArrayList<>();
        recyclerViewAgregarPartido = vista.findViewById(R.id.idRecyclerAgregarPatido);
        recyclerViewAgregarPartido.setLayoutManager(new LinearLayoutManager(getContext()));

        txtFechaAgregarPartido = vista.findViewById(R.id.idtxtFechaAgregarPartido);
        txtNumeroJornadaAgregarPartido = vista.findViewById(R.id.idtxtNumeroJornadaAgregarPartido);
        txtNumeroPartidoAgregarPartido = vista.findViewById(R.id.idtxtNumeroPartidoAgregarPartido);

        RadioBtnEquipo1 = vista.findViewById(R.id.idRadioBtnLocalEquipo1AgregarPartido);
        RadioBtnEquipo2 = vista.findViewById(R.id.idRadioBtnLocalEquipo2AgregarPartido);

        btnCalendrarioAgregarPartido = vista.findViewById(R.id.idbtnCalendrarioAgregarPartido);
        btnGuardarPartidos = vista.findViewById(R.id.idbtnGuardarAgregarPartido);

        spinnerEquipos1 = vista.findViewById(R.id.idSpinnerEquipo1AgregarPartido);
        spinnerEquipos2 = vista.findViewById(R.id.idSpinnerEquipo2AgregarPartido);
        spinnerHoras = vista.findViewById(R.id.idSpinnerHoraAgregarPartido);
        spinnerLugarDeJuego = vista.findViewById(R.id.idSpinnerLugarAgregarPartido);

        iniciarFirebase();
        asignarValoresPreferencias();
        llenarSpinners();
        obtenerPartidos();
        eventoBotones();


        return vista;
    }

    private void asignarValoresPreferencias() {
        Drawable shape = (Drawable) btnGuardarPartidos.getBackground();
        shape.setColorFilter(getResources().getColor(preferencias.colorTema), PorterDuff.Mode.SRC);
    }

    private void obtenerPartidos() {
        databaseReference.child("Primera Fuerza").child("Proximos Partidos").orderByChild("hora").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> partidos = dataSnapshot.getChildren().iterator();
                listaAgregarPartido.clear();
                listaFinal.clear();
                while (partidos.hasNext()){
                    DataSnapshot partido = partidos.next();

                    String equipo1, equipo2, jornada, numPartido, local1, local2, hora, fecha, lugar;

                    equipo1 = partido.child("equipo1").getValue().toString();
                    equipo2 = partido.child("equipo2").getValue().toString();
                    jornada = partido.child("jornada").getValue().toString();
                    numPartido = partido.child("numPartido").getValue().toString();
                    local1 = partido.child("localEquipo1").getValue().toString();
                    local2 = partido.child("localEquipo2").getValue().toString();
                    hora = partido.child("hora").getValue().toString();
                    fecha = partido.child("fecha").getValue().toString();
                    lugar = partido.child("lugar").getValue().toString();

                    listaAgregarPartido.add(new AgregarPartido(jornada, numPartido, equipo1, equipo2, local1,
                            local2, hora, fecha, lugar, R.drawable.separacion_lista));
                }

                AdapterAgregarPartidos adapterAgregarPartidos = new AdapterAgregarPartidos(listaAgregarPartido);
                recyclerViewAgregarPartido.setAdapter(adapterAgregarPartidos);
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


    public void eventoBotones(){


        spinnerEquipos1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipo1 =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                equipo1 = "Selecciona";
            }
        });

        spinnerEquipos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipo2 =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                equipo2 = "Selecciona";
            }
        });

        spinnerHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hora =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hora = "Selecciona";
            }
        });

        spinnerLugarDeJuego.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lugar =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                lugar = "Selecciona";
            }
        });

        btnCalendrarioAgregarPartido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                sDay = c.get(Calendar.DAY_OF_MONTH);
                sMonth = c.get(Calendar.MONTH);
                sYear = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFechaAgregarPartido.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },sYear, sMonth, sDay);
                datePickerDialog.show();
            }
        });



        btnGuardarPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RadioBtnEquipo1.isChecked()){
                    localEquipo1 = "Local";
                    localEquipo2 = "Visitante";
                }
                else if(RadioBtnEquipo2.isChecked()){
                    localEquipo2 = "Local";
                    localEquipo1 = "Visitante";
                }

                numJornada = txtNumeroJornadaAgregarPartido.getText().toString();
                numPartido = txtNumeroPartidoAgregarPartido.getText().toString();
                fecha = txtFechaAgregarPartido.getText().toString();

                if(numJornada.equals("")||numPartido.equals("")||equipo1.equals("Selecciona")||equipo2.equals("Selecciona")||hora.equals("Selecciona")||
                        fecha.equals("")||lugar.equals("Selecciona")||(localEquipo1.equals("")&&localEquipo2.equals(""))||equipo1.equals(equipo2)){
                    validacion();
                }
                else{
                    AgregarPartido AP = new AgregarPartido();
                    AP.setJornada(numJornada);
                    AP.setNumPartido(numPartido);
                    AP.setEquipo1(equipo1);
                    AP.setEquipo2(equipo2);
                    AP.setHora(hora);
                    AP.setFecha(fecha);
                    AP.setLugar(lugar);
                    AP.setLocalEquipo1(localEquipo1);
                    AP.setLocalEquipo2(localEquipo2);
                    databaseReference.child("Primera Fuerza").child("Proximos Partidos").child("Partido "+AP.getNumPartido()).setValue(AP);
                    Toast.makeText(getContext(),"Partido agregardo con exito",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }

            }
        });

    }


    private void limpiarCampos(){
        txtNumeroJornadaAgregarPartido.setText("");
        txtNumeroPartidoAgregarPartido.setText("");
        txtFechaAgregarPartido.setText("");
        RadioBtnEquipo1.setChecked(false);
        RadioBtnEquipo2.setChecked(false);
        llenarSpinners();
    }


    private void validacion(){
        if(numJornada.equals("")){
            txtNumeroJornadaAgregarPartido.setError("Número de jornada requerido!");
        }
        if(numPartido.equals("")){
            txtNumeroPartidoAgregarPartido.setError("Número de partido requerido!");
        }
        if(equipo1.equals("Selecciona")){
            dialogoAlertaEquipos().show();
        }
        if(equipo2.equals("Selecciona")){
            dialogoAlertaEquipos().show();
        }
        if(hora.equals("Selecciona")){
            dialogoAlertaHora().show();
        }
        if(fecha.equals("")){
            txtFechaAgregarPartido.setError("Fecha Requerida!");
        }
        if(lugar.equals("Selecciona")){
            dialogoAlertaLugar().show();
        }
        if(localEquipo1.equals("")&&localEquipo2.equals("")){
            dialogoAlertaLocal().show();
        }
        if(equipo1.equals(equipo2)){
            dialogoAlertaEquiposIguales().show();
        }
    }


    private void llenarSpinners() {
        ArrayAdapter<CharSequence> equipos = ArrayAdapter.createFromResource(getContext(),
                R.array.equipos_primera_agregar_partidos,R.layout.spinner_elementos);
        spinnerEquipos1.setAdapter(equipos);
        spinnerEquipos2.setAdapter(equipos);


        ArrayAdapter<CharSequence> horas = ArrayAdapter.createFromResource(getContext(),
                R.array.horas_partidosPrimera,R.layout.spinner_elementos);
        spinnerHoras.setAdapter(horas);

        ArrayAdapter<CharSequence> lugar = ArrayAdapter.createFromResource(getContext(),
                R.array.lugarDeJuegos,R.layout.spinner_elementos);
        spinnerLugarDeJuego.setAdapter(lugar);

    }


    public AlertDialog dialogoAlertaEquipos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de seleccionar los equipos que se enfrentarán").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public AlertDialog dialogoAlertaHora(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de seleccionar la hora del partido").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public AlertDialog dialogoAlertaLugar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de seleccionar el campo donde se jugará el partido").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public AlertDialog dialogoAlertaLocal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Debes de seleccionar que equipo es el local").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public AlertDialog dialogoAlertaEquiposIguales(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Los equipos a enfrentarse no pueden ser el mismo").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
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
