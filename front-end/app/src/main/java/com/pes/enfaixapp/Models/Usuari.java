package com.pes.enfaixapp.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Àlex on 26/10/2016.
 */

public class Usuari implements Serializable {

    private String nom;
    private int id;
    private String cognoms;
    private String psswd;
    private String correu;
    private List<Integer> collesALesQuePertany;
    private List<Integer> collesSeguides;

    public Usuari() {
        collesSeguides = new ArrayList<>();
        collesALesQuePertany = new ArrayList<>();
    }

    public Usuari(String nom, int id, String cognoms, String psswd, String correu, ArrayList<Integer> collesSeguides) {
        this.nom = nom;
        this.id = id;
        this.cognoms = cognoms;
        this.psswd = psswd;
        this.correu = correu;
        this.collesSeguides = collesSeguides;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Integer> getCollesSeguides() {
        return collesSeguides;
    }

    public void addCollesSeguides(int collaSeguida) {
        collesSeguides.add(collaSeguida);
    }

    public List<Integer> getCollesALesQuePertany() {
        return collesALesQuePertany;
    }

    public void setCollesALesQuePertany(ArrayList<Integer> collesALesQuePertany) {
        this.collesALesQuePertany = collesALesQuePertany;
    }

    public void addCollaQuePertany(int collaConvEscollida) {
        collesALesQuePertany.add(collaConvEscollida);
    }
}
