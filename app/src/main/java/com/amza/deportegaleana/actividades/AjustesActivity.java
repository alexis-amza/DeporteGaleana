package com.amza.deportegaleana.actividades;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.fragments.AjustesFragment;

public class AjustesActivity extends AppCompatActivity {

    private RelativeLayout cabeceraAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        cabeceraAjustes = findViewById(R.id.idCabeceraAjustes);

        getSupportFragmentManager().beginTransaction().replace(R.id.idContenedorAjustes,new AjustesFragment()).commit();

    }

    private void asignarValoresPreferencias() {
        cabeceraAjustes.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnRegresarAjustes:
                finish();
                break;

            case R.id.idbtnAyudaAjustes:
                dialogoAyudaAjustes().show();
                break;
        }
    }

    public AlertDialog dialogoAyudaAjustes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ayuda").setMessage("Desde aqui podrás ver los datos de tu cuenta, así como cambiar el tema de la aplicación.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }

    @Override
    protected void onResume() {
        asignarValoresPreferencias();
        super.onResume();
    }
}
