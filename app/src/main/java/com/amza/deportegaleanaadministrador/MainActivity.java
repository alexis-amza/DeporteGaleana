package com.amza.deportegaleanaadministrador;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.KeyEvent;

import com.amza.deportegaleanaadministrador.Fragments.InicioFragment;
import com.amza.deportegaleanaadministrador.actividades.AcercaDeActivity;
import com.amza.deportegaleanaadministrador.actividades.AgregarGoleadoresActivity;
import com.amza.deportegaleanaadministrador.actividades.AgregarNoticiaActivity;
import com.amza.deportegaleanaadministrador.actividades.AjustesActivity;
import com.amza.deportegaleanaadministrador.actividades.ContenedorPartidosActivity;
import com.amza.deportegaleanaadministrador.actividades.TablaGeneralActivity;
import com.amza.deportegaleanaadministrador.clases.preferencias;
import com.amza.deportegaleanaadministrador.interfaces.IComunicaFragments;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener {

    private Fragment fragmentInicio;

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencias.obtenerPreferencias(preferences,this);

        iniciarFirebase();

        fragmentInicio = new InicioFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
    public void abrirAvisos() {
        Intent intent = new Intent(getApplicationContext(), AgregarNoticiaActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirGoleadores() {
        Intent intent = new Intent(getApplicationContext(), AgregarGoleadoresActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirAjustes() {
        Intent intent = new Intent(getApplicationContext(), AjustesActivity.class);
        startActivity(intent);
    }

    @Override
    public void abrirAcercaDe() {
        Intent intent = new Intent(getApplicationContext(), AcercaDeActivity.class);
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