package com.amza.deportegaleanaadministrador.actividades;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.amza.deportegaleanaadministrador.MainActivity;
import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.adaptadores.AdapterNoticias;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.models.ObtenerNoticia;
import com.amza.deportegaleanaadministrador.models.noticia;
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
import java.util.ListIterator;

public class AgregarNoticiaActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ImageButton btnRegresarAgregarNoticia, btnAyudaAgregarNoticia;
    private EditText txtTituloAgregarNoticia, txtCuerpoAgregarNoticia;
    private Button btnGuardarNoticia;

    private ArrayList<ObtenerNoticia> listaNoticias;
    private ArrayList<ObtenerNoticia> listaFinal;

    private RecyclerView recyclerNoticias;

    private String tituloNoticia;
    private String cuerpoNoticia;
    private int dia;
    private int mes;
    private int year;
    private String fecha;

    private RelativeLayout cabeceraNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_agregar_noticia);

        listaNoticias = new ArrayList<>();
        listaFinal = new ArrayList<>();

        recyclerNoticias = findViewById(R.id.idRecyclerNoticas);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(this));

        txtTituloAgregarNoticia = findViewById(R.id.idtxtTituloAgregarNoticia);
        txtCuerpoAgregarNoticia = findViewById(R.id.idtxtCuerpoAgregarNoticia);

        btnGuardarNoticia = findViewById(R.id.idbtnGuardarAgregarNoticia);
        btnRegresarAgregarNoticia = findViewById(R.id.idbtnRegresarNoticias);
        btnAyudaAgregarNoticia = findViewById(R.id.idbtnAyudaNoticias);
        cabeceraNoticias = findViewById(R.id.idCabeceraNoticias);

        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerNoticias();
        eventoBotones();

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

                    listaNoticias.add(new ObtenerNoticia(titulo, fecha, cuerpo, R.drawable.ico_lista_noticias, R.drawable.separacion_lista));
                }

                ListIterator iter = listaNoticias.listIterator(listaNoticias.size());
                for(int i =0; i<=listaNoticias.size(); i++){
                    if(iter.hasPrevious()){
                        listaFinal.add((ObtenerNoticia) iter.previous());
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

    private void asignarValoresPreferencias() {
        cabeceraNoticias.setBackgroundColor(getResources().getColor(preferencias.colorTema));
        Drawable shape = (Drawable) btnGuardarNoticia.getBackground();
        shape.setColorFilter(getResources().getColor(preferencias.colorTema), PorterDuff.Mode.SRC);
    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void eventoBotones(){
        btnRegresarAgregarNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnAyudaAgregarNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAyudaNoticias().show();
            }
        });


        btnGuardarNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);

                fecha = mes+"/"+dia+"/"+year;

                tituloNoticia = txtTituloAgregarNoticia.getText().toString();
                cuerpoNoticia = txtCuerpoAgregarNoticia.getText().toString();

                if(tituloNoticia.equals("")||cuerpoNoticia.equals("")){
                    validacion();
                }
                else{
                    noticia n = new noticia();
                    n.setTitulo(tituloNoticia);
                    n.setCuerpo(cuerpoNoticia);
                    n.setFecha(fecha);
                    databaseReference.child("Primera Fuerza").child("Noticias").child(tituloNoticia).setValue(n);
                    limpiarCampos();
                }

            }
        });
    }

    private void limpiarCampos(){
        txtTituloAgregarNoticia.setText("");
        txtCuerpoAgregarNoticia.setText("");
    }

    private void validacion(){
        if(tituloNoticia.equals("")){
            txtTituloAgregarNoticia.setError("Título de la noticia requerido!");
        }
        if(cuerpoNoticia.equals("")){
            txtCuerpoAgregarNoticia.setError("El cuerpo de la noticia es necesario!");
        }
    }

    public AlertDialog dialogoAyudaNoticias(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("En este apartado de la aplicación podrás agregar avisos sobre la liga de la cual estas a cargo," +
                " por ejemplo, cuando se suspende un partido, la liga completa ó a un jugador o equipo, el motivo de la suspención, entre otras cosas.\n\n"+
                "Para hacerlo solo es necesario ingresar el título de la noticia en el campo correspondiente y redactar la noticia en el campo que le corresponde.\n" +
                "Una vez que tengas agregado un aviso en la base de datos, aparecerá en la parte inferior de la pantalla.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }

}
