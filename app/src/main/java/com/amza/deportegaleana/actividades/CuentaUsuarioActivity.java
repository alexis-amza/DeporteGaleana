package com.amza.deportegaleana.actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;
import com.facebook.login.LoginManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        obtenerUsuario();
    }

    private void obtenerUsuario() {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            txtCorreoUsuario.setText(user.getEmail());
            txtNombreUsuario.setText(user.getDisplayName());
        }

    }

    private void asignarValoresPreferencias() {
        cabecereUsuario.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
                finish();
        }
    }
}
