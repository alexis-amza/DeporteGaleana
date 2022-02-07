package com.amza.deportegaleana.actividades;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;

public class ExitoRestablecerPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exito_restablecer_password);
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnAceptarReestablecerContrasena:
                Intent intent = new Intent(getApplicationContext(),InicioSesionActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
