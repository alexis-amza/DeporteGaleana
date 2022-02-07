package com.amza.deportegaleana.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.models.Noticia;

public class MostrarNoticiaActivity extends AppCompatActivity {

    TextView titulo, fecha, cuerpo;

    public String tit, cue, fe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_noticia);

        titulo = findViewById(R.id.idtxtTituloNoticiaMostrarNoticia);
        fecha = findViewById(R.id.idFechaNoticiaMostrarNoticia);
        cuerpo = findViewById(R.id.idCuerpoNoticiaMostrarNoticia);

        mostrarNoticia();
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getCue() {
        return cue;
    }

    public void setCue(String cue) {
        this.cue = cue;
    }

    public String getFe() {
        return fe;
    }

    public void setFe(String fe) {
        this.fe = fe;
    }

    private void mostrarNoticia() {

        titulo.setText(getTit());
        fecha.setText(getFe());
        cuerpo.setText(getCue());

    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idbtnRegresarMostrarNoticias:
                finish();
                break;
        }
    }
}
