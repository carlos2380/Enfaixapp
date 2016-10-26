package com.pes.enfaixapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Àlex on 26/10/2016.
 */

public class Usuari {

    private String nom;
    private String id;
    private String cognoms;
    private String psswd;
    private String correu;
    private Colla CollaConv;
    private Colla CollaUni;
    private ArrayList<Colla> CollesSeguides;


    public Usuari(String nom, String id, String cognoms, String psswd, String correu, Colla collaConv, Colla collaUni, ArrayList<Colla> collesSeguides) {
        this.nom = nom;
        this.id = id;
        this.cognoms = cognoms;
        this.psswd = psswd;
        this.correu = correu;
        CollaConv = collaConv;
        CollaUni = collaUni;
        CollesSeguides = collesSeguides;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getPsswd() {
        return psswd;
    }

    public void setPsswd(String psswd) {
        this.psswd = psswd;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public Colla getCollaConv() {
        return CollaConv;
    }

    public void setCollaConv(Colla collaConv) {
        CollaConv = collaConv;
    }

    public Colla getCollaUni() {
        return CollaUni;
    }

    public void setCollaUni(Colla collaUni) {
        CollaUni = collaUni;
    }

    public ArrayList<Colla> getCollesSeguides() {
        return CollesSeguides;
    }

    public void setCollesSeguides(ArrayList<Colla> collesSeguides) {
        CollesSeguides = collesSeguides;
    }
}
