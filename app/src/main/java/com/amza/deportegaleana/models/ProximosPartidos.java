package com.amza.deportegaleana.models;

public class ProximosPartidos {

    private String jornada;
    private String numPartido;
    private String equipo1;
    private String equipo2;
    private String localEquipo1;
    private String localEquipo2;
    private String hora;
    private String fecha;
    private String lugar;
    private int separador;

    public ProximosPartidos() {
    }

    public ProximosPartidos(String jornada, String numPartido, String equipo1, String equipo2, String localEquipo1, String localEquipo2, String hora, String fecha, String lugar, int separador) {
        this.jornada = jornada;
        this.numPartido = numPartido;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.localEquipo1 = localEquipo1;
        this.localEquipo2 = localEquipo2;
        this.hora = hora;
        this.fecha = fecha;
        this.lugar = lugar;
        this.separador = separador;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getNumPartido() {
        return numPartido;
    }

    public void setNumPartido(String numPartido) {
        this.numPartido = numPartido;
    }

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public String getLocalEquipo1() {
        return localEquipo1;
    }

    public void setLocalEquipo1(String localEquipo1) {
        this.localEquipo1 = localEquipo1;
    }

    public String getLocalEquipo2() {
        return localEquipo2;
    }

    public void setLocalEquipo2(String localEquipo2) {
        this.localEquipo2 = localEquipo2;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getSeparador() {
        return separador;
    }

    public void setSeparador(int separador) {
        this.separador = separador;
    }
}
