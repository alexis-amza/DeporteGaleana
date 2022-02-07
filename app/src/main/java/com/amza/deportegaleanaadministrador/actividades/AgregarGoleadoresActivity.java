package com.amza.deportegaleanaadministrador.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.amza.deportegaleanaadministrador.MainActivity;
import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.adaptadores.AdapterGoleadores;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.models.Goleador;
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

public class AgregarGoleadoresActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Goleador> listaFinal;
    private ArrayList<Goleador> listaGoleadores;
    private RecyclerView recyclerGoleadores;

    private ImageButton btnRegresarGoleadores, btnAyudaGoleadores, btnguardarGoleador, btnactualizarGoleador;
    private EditText txtNombreGoleador, txtGolesGoleador;
    private Spinner spinnerEquiposGoleador;

    private String nombre, goles, equipo;
    private RelativeLayout cabeceraGoleadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_agregar_goleadores);

        listaFinal = new ArrayList<>();
        listaGoleadores = new ArrayList<>();
        recyclerGoleadores = findViewById(R.id.idRecyclerGoleadores);
        recyclerGoleadores.setLayoutManager(new LinearLayoutManager(this));

        txtGolesGoleador = findViewById(R.id.idtxtGolesAgregarGoleador);
        txtNombreGoleador = findViewById(R.id.idtxtNombreAgregarGoleador);

        spinnerEquiposGoleador = findViewById(R.id.idSpinnerEquiposAgregarGoleador);
        llenarSpinner();

        btnAyudaGoleadores = findViewById(R.id.idbtnAyudaGoledores);
        btnRegresarGoleadores = findViewById(R.id.idbtnRegresarGoleadores);
        btnguardarGoleador = findViewById(R.id.idbtnGuardarAgregarGoleador);
        btnactualizarGoleador = findViewById(R.id.idbtnActualizarAgregarGoleador);
        cabeceraGoleadores = findViewById(R.id.idCabeceraGoleadores);


        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerGoleadores();
        eventoBotones();

    }

    private void asignarValoresPreferencias() {
        cabeceraGoleadores.setBackgroundColor(getResources().getColor(preferencias.colorTema));

    }

    private void llenarSpinner() {
        ArrayAdapter<CharSequence> equipos = ArrayAdapter.createFromResource(this,
                R.array.equipos_primera,R.layout.spinner_elementos);
        spinnerEquiposGoleador.setAdapter(equipos);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void obtenerGoleadores() {
        databaseReference.child("Primera Fuerza").child("Goleadores").orderByChild("goles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> goleadores = dataSnapshot.getChildren().iterator();
                listaGoleadores.clear();
                listaFinal.clear();
                while(goleadores.hasNext()){
                    DataSnapshot goleador = goleadores.next();

                    String nombre, goles, equipo;

                    nombre = goleador.child("nombre").getValue().toString();
                    goles = goleador.child("goles").getValue().toString();
                    equipo = goleador.child("equipo").getValue().toString();

                    listaGoleadores.add(new Goleador(nombre,goles,equipo,R.drawable.logolistagoleadores,R.drawable.separacion_lista));
                }


                ListIterator iter = listaGoleadores.listIterator(listaGoleadores.size());
                for(int i =0; i<=listaGoleadores.size(); i++){
                    if(iter.hasPrevious()){
                        listaFinal.add((Goleador) iter.previous());
                    }
                }

                AdapterGoleadores adapterGoleadores = new AdapterGoleadores(listaFinal);
                adapterGoleadores.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtNombreGoleador.setText(listaFinal.get(recyclerGoleadores.getChildAdapterPosition(v)).getNombre());
                        txtGolesGoleador.setText(listaFinal.get(recyclerGoleadores.getChildAdapterPosition(v)).getGoles());
                        equipo = listaFinal.get(recyclerGoleadores.getChildAdapterPosition(v)).getEquipo();
                    }
                });

                recyclerGoleadores.setAdapter(adapterGoleadores);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void eventoBotones(){
        btnAyudaGoleadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAyudaPartidos().show();
            }
        });


        btnRegresarGoleadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        spinnerEquiposGoleador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                equipo = "Selecciona";
            }
        });


        btnguardarGoleador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = txtNombreGoleador.getText().toString();
                goles = txtGolesGoleador.getText().toString();


                if(nombre.equals("")||goles.equals("")||equipo.equals("Selecciona")){
                    validarDatos();
                }
                else{
                    Goleador g = new Goleador();
                    g.setNombre(nombre);
                    g.setGoles(goles);
                    g.setEquipo(equipo);
                    databaseReference.child("Primera Fuerza").child("Goleadores").child(g.getNombre()).setValue(g);
                    Toast.makeText(getApplicationContext(),"Registro agregado con éxito",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }

            }
        });


        btnactualizarGoleador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txtNombreGoleador.getText().toString();
                goles = txtGolesGoleador.getText().toString();

                if(nombre.equals("")||goles.equals("")){
                    validarDatosActualizar();
                }
                else{
                    Goleador g = new Goleador();
                    g.setNombre(nombre);
                    g.setGoles(goles);
                    g.setEquipo(equipo);
                    databaseReference.child("Primera Fuerza").child("Goleadores").child(g.getNombre()).setValue(g);
                    Toast.makeText(getApplicationContext(),"Registro actualizado con éxito",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }
            }
        });


    }

    public AlertDialog dialogoAyudaPartidos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("En este apartado de la aplicación podrás agregar a los maximos" +
                " rompe redes de la liga actual.\n" +
                "Para hacerlo solo es necesario ingresar el nombre del jugador, la catidad de goles que marcó en el partido y" +
                " el equipo al que pertenece.\n\n" +
                "Una vez que tengas agregado algún goleador en la base de datos, aparecerá en la parte inferior de la pantalla, " +
                "y para editar sus goles solo es cuestion de seleccionar el jugador y en la opción de goles solo " +
                "agregar los que marcó en el partido.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }

    private void validarDatos(){
        if(nombre.equals("")){
            txtNombreGoleador.setError("Campo Requerido!");
        }
        if(goles.equals("")){
            txtGolesGoleador.setError("Campo Requerido!");
        }
        if(equipo.equals("Selecciona")){
            dialogoAlertaEquipo().show();
        }

    }


    private void limpiarCampos(){
        txtNombreGoleador.setText("");
        txtGolesGoleador.setText("");
        llenarSpinner();
    }

    private void validarDatosActualizar(){
        if(nombre.equals("")){
            txtNombreGoleador.setError("Campo Requerido!");
        }
        if(goles.equals("")){
            txtGolesGoleador.setError("Campo Requerido!");
        }
    }

    public AlertDialog dialogoAlertaEquipo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("Debes de seleccionar el equipo al que pertenece el jugador").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}
