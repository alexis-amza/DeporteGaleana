package com.amza.deportegaleana.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.amza.deportegaleana.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarUsuarioActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    private CheckBox checkBoxRecaptcha;
    private EditText txtemail, txtpassword;

    private GoogleApiClient googleApiClient;

    private String SiteKey = "6LfRDdcZAAAAALcGBG32zLS0x2yq6iGUPtX_VI3E";
    private int control_recaptcha = 1;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressDialog dialogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        dialogo = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        checkBoxRecaptcha = findViewById(R.id.id_recaptcha_registrarUsuario);
        txtemail = findViewById(R.id.idCorreoNuevoUsuario);
        txtpassword = findViewById(R.id.idPasswordNuevoUsuario);

        googleApiClient = new GoogleApiClient.Builder(this).addApi(SafetyNet.API).addConnectionCallbacks(RegistrarUsuarioActivity.this).build();
        googleApiClient.connect();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    dialogo.dismiss();
                    Intent intent = new Intent(getApplicationContext(),ExitoRegistroUsuarioActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        checkBoxRecaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxRecaptcha.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, SiteKey).setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                        @Override
                        public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                            Status status = recaptchaTokenResult.getStatus();
                            if((status !=null) && status.isSuccess()){
                                checkBoxRecaptcha.setTextColor(Color.GREEN);
                                control_recaptcha = 2;
                            }
                        }
                    });
                }else{
                    checkBoxRecaptcha.setTextColor(Color.RED);
                    control_recaptcha = 1;
                    Toast.makeText(getApplicationContext(),"Ocurrio un error, vuelve a intentarlo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.idbtnRegistrarUsuarioNuevo:

                dialogo.setMessage("Un momento por favor...");
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();

                if(control_recaptcha==2){

                    String correo, pas;

                    correo = txtemail.getText().toString();
                    pas = txtpassword.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(correo, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Ocurrió un error al intentar registrar el usuario, revisa tu conexión a internet",Toast.LENGTH_LONG).show();
                                dialogo.dismiss();
                            }else{
                                firebaseAuth.setLanguageCode("es");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Debes completas el recaptcha",Toast.LENGTH_SHORT).show();
                    dialogo.dismiss();
                }
                break;
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

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
