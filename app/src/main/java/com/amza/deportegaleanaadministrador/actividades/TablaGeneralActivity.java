package com.amza.deportegaleanaadministrador.actividades;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.adaptadores.AdapterTablaGeneral;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.models.TablaGeneral;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class TablaGeneralActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    private ArrayList<TablaGeneral> listaTablaGeneral;
    private ArrayList<TablaGeneral> listaFinal;

    private ImageButton btnRegresarTablaGeneral, btnAyudaTablaGeneral;

    private RecyclerView recyclerTablaGeneral;
    private RelativeLayout cabeceraTablaGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabla_general);

        recyclerTablaGeneral = findViewById(R.id.idRecyclerTablaGeneral);
        recyclerTablaGeneral.setLayoutManager(new LinearLayoutManager(this));

        listaTablaGeneral = new ArrayList<>();
        listaFinal = new ArrayList<>();

        btnRegresarTablaGeneral = findViewById(R.id.idbtnRegresarTablaGeneral);
        btnAyudaTablaGeneral = findViewById(R.id.idbtnAyudaTablaGeneral);
        cabeceraTablaGeneral = findViewById(R.id.idCabeceraTablaGeneral);

        asignarValoresPreferencias();
        iniciarFirebase();
        obtenerTablaGeneral();
        eventoBotones();

    }

    private void asignarValoresPreferencias() {
        cabeceraTablaGeneral.setBackgroundColor(getResources().getColor(preferencias.colorTema));

    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void obtenerTablaGeneral() {
        databaseReference.child("Primera Fuerza").child("Tabla General").orderByChild("pts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> tablaGeneral = dataSnapshot.getChildren().iterator();
                listaTablaGeneral.clear();
                listaFinal.clear();
                while (tablaGeneral.hasNext()){
                    DataSnapshot tabla = tablaGeneral.next();

                    String equipo, pts, jj, jg, je, jp, gf, gc, dif;
                    equipo = tabla.child("equipo").getValue().toString();
                    pts = tabla.child("pts").getValue().toString();
                    jj = tabla.child("jj").getValue().toString();
                    jg = tabla.child("jg").getValue().toString();
                    je = tabla.child("je").getValue().toString();
                    jp = tabla.child("jp").getValue().toString();
                    gf = tabla.child("gf").getValue().toString();
                    gc = tabla.child("gc").getValue().toString();
                    dif = tabla.child("dif").getValue().toString();

                    listaTablaGeneral.add(new TablaGeneral(equipo, pts, jj, jg, je, jp, gf, gc, dif));
                }

                ListIterator iter = listaTablaGeneral.listIterator(listaTablaGeneral.size());
                for(int i=0; i<listaTablaGeneral.size(); i++){
                    if(iter.hasPrevious()){
                        listaFinal.add((TablaGeneral)iter.previous());
                    }

                }


                AdapterTablaGeneral adapterTabla = new AdapterTablaGeneral(listaFinal);
                recyclerTablaGeneral.setAdapter(adapterTabla);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void eventoBotones(){
        btnRegresarTablaGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAyudaTablaGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAyudaTabla().show();
            }
        });


    }


    public AlertDialog dialogoAyudaTabla(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("Aquí verás las estadísticas de los equipos y sus posiciones").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }


}
