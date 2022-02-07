package com.amza.deportegaleana.models;

public class TablaGeneral {

    private String equipo;
    private String pts;
    private String jj;
    private String jg;
    private String je;
    private String jp;
    private String gf;
    private String gc;
    private String dif;

    public TablaGeneral() {
    }

    public TablaGeneral(String equipo, String pts, String jj, String jg, String je, String jp, String gf, String gc, String dif) {
        this.equipo = equipo;
        this.pts = pts;
        this.jj = jj;
        this.jg = jg;
        this.je = je;
        this.jp = jp;
        this.gf = gf;
        this.gc = gc;
        this.dif = dif;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getGf() {
        return gf;
    }

    public void setGf(String gf) {
        this.gf = gf;
    }

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
    }
}

