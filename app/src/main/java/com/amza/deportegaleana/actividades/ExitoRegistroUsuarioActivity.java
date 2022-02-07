package com.amza.deportegaleana.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amza.deportegaleana.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ExitoRegistroUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exito_registro_usuario);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnAceptarExitoRegistroUsuario:
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),InicioSesionActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
