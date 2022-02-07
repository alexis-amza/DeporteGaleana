package com.amza.deportegaleanaadministrador.actividades;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.amza.deportegaleanaadministrador.R;
import com.amza.deportegaleanaadministrador.clases.preferencias;

public class CambiarTemaActivity extends AppCompatActivity {

    private RelativeLayout layoutCabecera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_tema);
        layoutCabecera=findViewById(R.id.idCabeceraCambiarTema);
        asignarValoresPreferencias();
    }

    private void asignarValoresPreferencias() {
        layoutCabecera.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnRegresarTema:
                finish();
                break;

            case R.id.idCardColorPrincipal:
                preferencias.colorTema=R.color.colorPrimary;
                break;

            case R.id.idCardRosaProfundo:
                preferencias.colorTema=R.color.colorRosaProfundo;
                break;

            case R.id.idCardCoral:
                preferencias.colorTema=R.color.colorCoral;
                break;

            case R.id.idCardRojoNaranja:
                preferencias.colorTema=R.color.colorRojoNaranja;
                break;

            case R.id.idCardPurpura:
                preferencias.colorTema=R.color.colorPurpura;
                break;

            case R.id.idCardTurquesaOscuro:
                preferencias.colorTema=R.color.colorTurquesaOscura;
                break;

            case R.id.idCardAzulMarino:
                preferencias.colorTema=R.color.colorAzulMarino;
                break;

            case R.id.idCardChocolate:
                preferencias.colorTema=R.color.colorChocolate;
                break;

            case R.id.idCardVerdeAzulado:
                preferencias.colorTema=R.color.colorVerdeAzulado;
                break;

            case R.id.idbtnAyudaCambiarTema:
                dialogoAyudaCambiarTema().show();
                break;

        }

        layoutCabecera.setBackgroundColor(getResources().getColor(preferencias.colorTema));

    }

    public AlertDialog dialogoAyudaCambiarTema(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("Desde aqui podrás cambiar el color de la aplicación al que más te plazca.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }


    @Override
    protected void onDestroy() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencias.asignarPreferenciasTema(preferences,getApplicationContext());
        super.onDestroy();
    }
}
