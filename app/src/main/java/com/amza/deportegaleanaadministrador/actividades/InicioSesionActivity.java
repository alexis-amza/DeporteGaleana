package com.amza.deportegaleanaadministrador.actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amza.deportegaleanaadministrador.MainActivity;
import com.amza.deportegaleanaadministrador.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class InicioSesionActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAnalytics firebaseAnalytics;

    private EditText txtcorreo, txtpassword;
    private Button btnlogin, btncambioPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        txtcorreo = findViewById(R.id.idtxtCorreoLogin);
        txtpassword = findViewById(R.id.idtxtPasswordLogin);

        btnlogin = findViewById(R.id.idBtnIniciarSesion);
        btncambioPassword = findViewById(R.id.idBtnRestablecerContrasena);


        iniciarFirebase();
        eventoBotones();
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    iniciarAplicacion();
                }
            }
        };
    }

    private void eventoBotones() {

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo, password;

                correo = txtcorreo.getText().toString();
                password = txtpassword.getText().toString();

                if(correo.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"Debes llenar todos los campos del formulario",Toast.LENGTH_LONG).show();
                }
                if(!correo.equals("isai_amaro50@outlook.com")){
                    Toast.makeText(getApplicationContext(),"El usuario con el que intentas ingresar no tinene permisos de administrador",Toast.LENGTH_LONG).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"El correo y/o la contraseña son incorrectos, verifica los datos y vuelve a intentarlo",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        btncambioPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RestablecerPasswordActivity.class);
                startActivity(intent);
            }
        });


    }



    private void iniciarAplicacion() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

}
