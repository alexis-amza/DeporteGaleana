package com.amza.deportegaleana;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.amza.deportegaleana.actividades.AcercaDeActivity;
import com.amza.deportegaleana.actividades.AjustesActivity;
import com.amza.deportegaleana.actividades.ContenedorInstruccionesActivity;
import com.amza.deportegaleana.actividades.ContenedorPartidosActivity;
import com.amza.deportegaleana.actividades.GoleadoresActivity;
import com.amza.deportegaleana.actividades.InicioSesionActivity;
import com.amza.deportegaleana.actividades.NoticiasActivity;
import com.amza.deportegaleana.actividades.TablaGeneralActivity;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.fragments.InicioFragment;
import com.amza.deportegaleana.interfaces.IComunicaFragments;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener {

    private Fragment fragmentInicio;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencias.obtenerPreferencias(preferences,this);

        fragmentInicio = new InicioFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void abrirTablaGeneral() {
        Intent intent = new Intent(getApplicationContext(), TablaGeneralActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirPartidos() {
        Intent intent = new Intent(getApplicationContext(), ContenedorPartidosActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirNoticias() {
        Intent intent = new Intent(getApplicationContext(), NoticiasActivity.class);
        startActivity(intent);    }

    @Override
    public void abrirGoleadores() {
        Intent intent = new Intent(getApplicationContext(), GoleadoresActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirAjustes() {
        Intent intent = new Intent(getApplicationContext(), AjustesActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirAcercaDe() {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode== KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la aplicación?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }

}
