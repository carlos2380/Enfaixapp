package com.pes.enfaixapp.Models;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Àlex on 25/11/2016.
 */

public class Esdeveniment {

    private int id;
    private String titol;
    private String descripcio;
    private String foto;
    private String direccio; //localitzacio
    private String date;
    private int usuari;  //usuari que crea l'esdeveniment
    private int colla;    //colla a la que pertany l'esdeveniment

    public Esdeveniment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUsuari() {
        return usuari;
    }

    public void setUsuari(int usuari) {
        this.usuari = usuari;
    }

    public int getColla() {
        return colla;
    }

    public void setColla(int colla) {
        this.colla = colla;
    }
}
