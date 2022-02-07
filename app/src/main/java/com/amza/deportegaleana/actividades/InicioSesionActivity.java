package com.amza.deportegaleana.actividades;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amza.deportegaleana.MainActivity;
import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.models.UsuariosFacebook;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InicioSesionActivity extends AppCompatActivity {


    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText txtCorreoUsuario,txtPasswordUsuario;

    private RelativeLayout bannerInicioSesion;
    private Button btnInicioSesion;
    private ProgressDialog dialogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        txtCorreoUsuario = findViewById(R.id.idtxtCorreoLogin);
        txtPasswordUsuario = findViewById(R.id.idtxtPasswordLogin);
        btnInicioSesion = findViewById(R.id.idBtnIniciarSesion);
        bannerInicioSesion = findViewById(R.id.idbannerInicioSesion);

        dialogo = new ProgressDialog(this);

        iniciarFirebase();


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){

                    if(!user.isEmailVerified()){
                        dialogo.dismiss();
                        firebaseAuth.signOut();
                        dialogoCorreoNoVerificado().show();
                    }else{
                        dialogo.dismiss();
                        goInicioApp();
                    }

                }
            }
        };

    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    private void goInicioApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idBtnIniciarSesion:

                String correo, password;

                FirebaseUser user = firebaseAuth.getCurrentUser();


                correo = txtCorreoUsuario.getText().toString();
                password = txtPasswordUsuario.getText().toString();

                dialogo.setMessage("Un momento por favor...");
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();

                if(correo.equals("") || password.equals("")){
                    dialogoLlenaCampos().show();
                    dialogo.dismiss();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){
                                dialogoCredencialesIncorrectas().show();
                                dialogo.dismiss();
                            }

                        }

                    });
                }
                break;

            case R.id.idBtnRestablecerContrasena:
                Intent intent = new Intent(getApplicationContext(), ReestablecerPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.idBtnRegistrarUsuarioNuevo:
                Intent intent2 = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                startActivity(intent2);
                break;

        }
    }


    public AlertDialog dialogoCorreoNoVerificado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error").setMessage("El usuario con el que intentas ingresar no ha verificado su dirección de correo, verifica tu bandeja de correo electrónico y sigue los pasos para verificar tu cuenta.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }


    public AlertDialog dialogoLlenaCampos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error").setMessage("Debes llenar todos los campos del formulario").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }


    public AlertDialog dialogoCredencialesIncorrectas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error").setMessage("El correo y/o la contraseña son incorrectos, verifica los datos y vuelve a intentarlo").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }
}
