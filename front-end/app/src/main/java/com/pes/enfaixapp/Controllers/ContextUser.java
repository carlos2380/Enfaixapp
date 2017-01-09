package com.pes.enfaixapp.Controllers;

import java.util.ArrayList;

/**
 * Created by carlos on 10/12/2016.
 */
public class ContextUser {


    private static String id;

    private String nom;
    private String cognoms;
    private String email;
    private String id_collaUni;
    private String id_collaConv;



    private ArrayList<String> collesPertany;


    public String getId_collaUni() {
        return id_collaUni;
    }

    public void setId_collaUni(String id_collaUni) {
        this.id_collaUni = id_collaUni;
    }

    public String getId_collaConv() {
        return id_collaConv;
    }

    public void setId_collaConv(String id_collaConv) {
        this.id_collaConv = id_collaConv;
    }


    private static ContextUser ourInstance = new ContextUser();

    public static ContextUser getInstance() {
        return ourInstance;
    }

    private ContextUser() {
    }

    public static String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCollesPertany(ArrayList<String> collesPertany) {
        this.collesPertany = collesPertany;
    }
    public ArrayList<String> getCollesPertany() {
        return collesPertany;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
