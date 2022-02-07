package com.amza.deportegaleana.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.adaptadores.AdapterNoticias;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.models.Goleador;
import com.amza.deportegaleana.models.Noticia;
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

public class NoticiasActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Noticia> listaFinal;
    private ArrayList<Noticia> listaNoticias;
    private RecyclerView recyclerNoticias;

    private RelativeLayout cabeceraNoticias;

    public String titulo="", fecha="", cuerpo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        listaNoticias = new ArrayList<>();
        listaFinal = new ArrayList<>();
        recyclerNoticias = findViewById(R.id.idRecyclerNoticias);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(this));
        cabeceraNoticias = findViewById(R.id.idCabeceraNoticias);

        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerNoticias();

    }

    private void asignarValoresPreferencias() {
        cabeceraNoticias.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }

    private void obtenerNoticias() {
        databaseReference.child("Primera Fuerza").child("Noticias").orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> noticias = dataSnapshot.getChildren().iterator();
                listaFinal.clear();
                listaNoticias.clear();

                while (noticias.hasNext()){
                    DataSnapshot noticia = noticias.next();

                    String titulo, fecha, cuerpo;

                    titulo = noticia.child("titulo").getValue().toString();
                    fecha = noticia.child("fecha").getValue().toString();
                    cuerpo = noticia.child("cuerpo").getValue().toString();

                    listaNoticias.add(new Noticia(titulo, fecha, cuerpo, R.drawable.ico_lista_noticias, R.drawable.separacion_lista));
                }

                ListIterator iter = listaNoticias.listIterator(listaNoticias.size());
                for(int i =0; i<=listaNoticias.size(); i++){
                    if(iter.hasPrevious()){
                        listaFinal.add((Noticia) iter.previous());
                    }
                }

                AdapterNoticias adapterNoticias = new AdapterNoticias(listaFinal);
                recyclerNoticias.setAdapter(adapterNoticias);

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
            case R.id.idbtnRegresarNoticias:
                finish();
                break;

            case R.id.idbtnAyudaNoticas:
                dialogoAyudaNoticia().show();
                break;
        }
    }

    public AlertDialog dialogoAyudaNoticia(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("En este apartado de la aplicaión podrás ver los avisos publicados por el administrador de la liga correspondiente.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }
}
