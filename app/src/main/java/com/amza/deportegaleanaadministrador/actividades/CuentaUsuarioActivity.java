package com.amza.deportegaleanaadministrador.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class CuentaUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView txtNombreUsuario, txtCorreoUsuario;

    private RelativeLayout cabecereUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_usuario);

        cabecereUsuario = findViewById(R.id.idCabeceraCuentaUsuario);
        txtNombreUsuario = findViewById(R.id.idtxtNombreUsuario);
        txtCorreoUsuario = findViewById(R.id.idtxtCorreoUsuario);

        iniciarFirebase();
        asignarValoresPreferencias();
        obtenerDatosUsuario();
    }

    private void obtenerDatosUsuario() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String nombre = user.getDisplayName();
        String correo = user.getEmail();

        txtCorreoUsuario.setText(correo);
        txtNombreUsuario.setText(nombre);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void asignarValoresPreferencias() {
        cabecereUsuario.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.idbtnRegresarCuentaUsuario:
                finish();
                break;

            case R.id.idBtnCerrarSesion:
                firebaseAuth.signOut();
                Intent intent = new Intent(this, InicioSesionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
    }


}
