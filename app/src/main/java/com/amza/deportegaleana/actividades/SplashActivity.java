package com.amza.deportegaleana.actividades;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amza.deportegaleana.MainActivity;
import com.amza.deportegaleana.R;
import com.facebook.AccessToken;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(AccessToken.getCurrentAccessToken()!=null || firebaseAuth.getCurrentUser() != null){
                    goInicioApp();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, InicioSesionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);

    }

    private void goInicioApp() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
