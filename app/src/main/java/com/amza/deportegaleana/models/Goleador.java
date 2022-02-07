package com.amza.deportegaleana.models;

public class Goleador {

    private String nombre;
    private String goles;
    private String equipo;
    private int imagen;
    private int separacion;

    public Goleador() {
    }

    public Goleador(String nombre, String goles, String equipo, int imagen, int separacion) {
        this.nombre = nombre;
        this.goles = goles;
        this.equipo = equipo;
        this.imagen = imagen;
        this.separacion = separacion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getSeparacion() {
        return separacion;
    }

    public void setSeparacion(int separacion) {
        this.separacion = separacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGoles() {
        return goles;
    }

    public void setGoles(String goles) {
        this.goles = goles;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
