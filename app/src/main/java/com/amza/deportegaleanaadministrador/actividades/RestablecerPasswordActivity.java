package com.amza.deportegaleanaadministrador.actividades;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amza.deportegaleanaadministrador.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerPasswordActivity extends AppCompatActivity {

    private EditText correo;
    private Button btnreestablerPassword;
    private FirebaseAuth firebaseAuth;
    private String email = "";
    private ProgressDialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_password);

        firebaseAuth = FirebaseAuth.getInstance();

        dialogo = new ProgressDialog(this);

        correo = findViewById(R.id.idtxtReestablecerPassword);
        btnreestablerPassword = findViewById(R.id.idbtnReestablecerContrasena);


        btnreestablerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = correo.getText().toString();

                if (!email.isEmpty()){
                    dialogo.setMessage("Un momento por favor...");
                    dialogo.setCanceledOnTouchOutside(false);
                    dialogo.show();
                    resetPassword();
                }
                else{
                    dialogoAlertaCorreo().show();
                }
            }
        });
    }

    private void resetPassword() {

        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(getApplicationContext(),ExitoRestablecerPasswordActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    dialogoErrorEnviarCorreo().show();
                }
                dialogo.dismiss();
            }
        });

    }



    public AlertDialog dialogoAlertaCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("Debes de ingresar tu correo electrónico").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    public AlertDialog dialogoErrorEnviarCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("No fue posible enviar el correo de restablecer constraseña, verifica tu conexión e intentalo más tarde").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
