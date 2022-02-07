package com.amza.deportegaleana.clases;

import android.content.Context;
import android.content.SharedPreferences;

import com.amza.deportegaleana.R;

public class preferencias {
    public static final String COLOR_TEMA="colorTema";

    public static int colorTema;

    public static void obtenerPreferencias(SharedPreferences preferences, Context context){

        String msjError = "ok";

        try{
            colorTema=Integer.parseInt(preferences.getString(COLOR_TEMA,""+ R.color.colorPrimary));
        }catch (NumberFormatException e){
            msjError = "Error en el color del tema";
        }

    }

    public static void asignarPreferenciasTema(SharedPreferences preferences, Context context){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COLOR_TEMA,""+colorTema);
        editor.commit();
    }
}
