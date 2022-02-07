package com.amza.deportegaleanaadministrador.models;

public class FinalizarPartido {
    private String Jornada;
    private String numPartido;
    private String EquipoLocal;
    private String EquipoVisitante;
    private String GolesLocal;
    private String GolesVisitante;
    private String Hora;
    private String Fecha;
    private String Lugar;

    public FinalizarPartido() {
    }

    public FinalizarPartido(String jornada, String numPartido, String equipoLocal, String equipoVisitante,
                            String golesLocal, String golesVisitante, String hora, String fecha, String lugar) {
        Jornada = jornada;
        this.numPartido = numPartido;
        EquipoLocal = equipoLocal;
        EquipoVisitante = equipoVisitante;
        GolesLocal = golesLocal;
        GolesVisitante = golesVisitante;
        Hora = hora;
        Fecha = fecha;
        Lugar = lugar;
    }

    public String getJornada() {
        return Jornada;
    }

    public void setJornada(String jornada) {
        Jornada = jornada;
    }

    public String getNumPartido() {
        return numPartido;
    }

    public void setNumPartido(String numPartido) {
        this.numPartido = numPartido;
    }

    public String getEquipoLocal() {
        return EquipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        EquipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return EquipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        EquipoVisitante = equipoVisitante;
    }

    public String getGolesLocal() {
        return GolesLocal;
    }

    public void setGolesLocal(String golesLocal) {
        GolesLocal = golesLocal;
    }

    public String getGolesVisitante() {
        return GolesVisitante;
    }

    public void setGolesVisitante(String golesVisitante) {
        GolesVisitante = golesVisitante;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }
}
