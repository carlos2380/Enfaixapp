package com.pes.enfaixapp.Models;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Àlex on 25/11/2016.
 */

public class Esdeveniment {

    private String titol;
    private String descripcio;
    private String foto;
    private String direccio; //localitzacio
    private Date date;
    private Usuari usuari;  //usuari que crea l'esdeveniment
    private Colla colla;    //colla a la que pertany l'esdeveniment
    private int numAssistents;
    private float latitut;          //long i lat per la localització
    private float longitut;


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

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Colla getColla() {
        return colla;
    }

    public void setColla(Colla colla) {
        this.colla = colla;
    }

    public int getNumAssistents() {
        return numAssistents;
    }

    public void setNumAssistents(int numAssistents) {
        this.numAssistents = numAssistents;
    }

    public float getLatitut() {
        return latitut;
    }

    public void setLatitut(float latitut) {
        this.latitut = latitut;
    }

    public float getLongitut() {
        return longitut;
    }

    public void setLongitut(float longitut) {
        this.longitut = longitut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
