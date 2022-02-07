package com.amza.deportegaleanaadministrador.models;

public class ObtenerNoticia {

    String titulo;
    String fecha;
    String cuerpo;
    int foto;
    int separacion;

    public ObtenerNoticia() {
    }

    public ObtenerNoticia(String titulo, String fecha, String cuerpo, int foto, int separacion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.foto = foto;
        this.separacion = separacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getSeparacion() {
        return separacion;
    }

    public void setSeparacion(int separacion) {
        this.separacion = separacion;
    }
}
