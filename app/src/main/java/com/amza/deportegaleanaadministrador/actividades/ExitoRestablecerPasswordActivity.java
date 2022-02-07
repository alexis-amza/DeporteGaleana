package com.amza.deportegaleanaadministrador.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amza.deportegaleanaadministrador.R;

public class ExitoRestablecerPasswordActivity extends AppCompatActivity {

    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exito_restablecer_password);

        btnAceptar = findViewById(R.id.idbtnAceptarReestablecerContrasena);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InicioSesionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
