package com.amza.deportegaleana.actividades;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.adaptadores.AdapterGoleadores;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.models.Goleador;
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

public class GoleadoresActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Goleador> listaFinal;
    private ArrayList<Goleador> listaGoleadores;

    private RecyclerView recyclerGoleadores;

    private ImageButton btnRegresarGoleadores, btnAyudaGoleadores, btnguardarGoleador, btnactualizarGoleador;

    private RelativeLayout cabeceraGoleadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goleadores);

        listaFinal = new ArrayList<>();
        listaGoleadores = new ArrayList<>();

        recyclerGoleadores = findViewById(R.id.idRecyclerGoleadores);
        recyclerGoleadores.setLayoutManager(new LinearLayoutManager(this));
        

        btnAyudaGoleadores = findViewById(R.id.idbtnAyudaGoledores);
        btnRegresarGoleadores = findViewById(R.id.idbtnRegresarGoleadores);
        cabeceraGoleadores = findViewById(R.id.idCabeceraGoleadores);


        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerGoleadores();

    }

    private void asignarValoresPreferencias() {
        cabeceraGoleadores.setBackgroundColor(getResources().getColor(preferencias.colorTema));
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
                recyclerGoleadores.setAdapter(adapterGoleadores);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnRegresarGoleadores:
                finish();
                break;

            case R.id.idbtnAyudaGoledores:
                dialogoAyudaPartidos().show();
                break;
        }
    }

    public AlertDialog dialogoAyudaPartidos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("En este apartado de la aplicación podrás ver los máximos romperedes del torneo.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }
}
