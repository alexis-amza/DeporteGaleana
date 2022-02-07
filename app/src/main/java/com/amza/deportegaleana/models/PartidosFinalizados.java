package com.amza.deportegaleana.models;

public class PartidosFinalizados {

    private String equipoLocal;
    private String equipoVisitante;
    private String golesLocal;
    private String golesVisitante;
    private String hora;
    private String fecha;
    private String campo;
    private int separador;

    public PartidosFinalizados() {
    }

    public PartidosFinalizados(String equipoLocal, String equipoVisitante, String golesLocal, String golesVisitante, String hora, String fecha, String campo, int separador) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.hora = hora;
        this.fecha = fecha;
        this.campo = campo;
        this.separador = separador;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public String getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(String golesLocal) {
        this.golesLocal = golesLocal;
    }

    public String getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(String golesVisitante) {
        this.golesVisitante = golesVisitante;
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

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public int getSeparador() {
        return separador;
    }

    public void setSeparador(int separador) {
        this.separador = separador;
    }
}
