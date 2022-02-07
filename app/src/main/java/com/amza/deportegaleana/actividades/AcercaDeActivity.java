package com.amza.deportegaleana.actividades;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;

public class AcercaDeActivity extends AppCompatActivity {

    private RelativeLayout bannerAcercade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        bannerAcercade = findViewById(R.id.idBannerAcercaDe);

        asignarValoresPreferencias();
    }

    private void asignarValoresPreferencias() {
        Drawable shape = (Drawable) bannerAcercade.getBackground();
        shape.setColorFilter(getResources().getColor(preferencias.colorTema), PorterDuff.Mode.SRC);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnRegresarAcercaDe:
                finish();
                break;
        }
    }
}
