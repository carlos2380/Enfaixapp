package com.pes.enfaixapp.Controllers;

import com.pes.enfaixapp.Models.Colla;

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
    private String id_collaSwitch;



    private ArrayList<Colla> collesPertany;

    public ArrayList<Colla> getCollesSegueix() {
        return collesSegueix;
    }

    public void setCollesSegueix(ArrayList<Colla> collesSegueix) {
        this.collesSegueix = collesSegueix;
    }

    private ArrayList<Colla> collesSegueix;

    public ArrayList<Colla> getCollesAdmin() {
        return collesAdmin;
    }

    public void setCollesAdmin(ArrayList<Colla> collesAdmin) {
        this.collesAdmin = collesAdmin;
    }

    private ArrayList<Colla> collesAdmin;


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

    public void setCollesPertany(ArrayList<Colla> collesPertany) {
        this.collesPertany = collesPertany;
    }
    public ArrayList<Colla> getCollesPertany() {
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

    public String getId_collaSwitch() {
        return id_collaSwitch;
    }

    public void setId_collaSwitch(String id_collaSwitch) {
        this.id_collaSwitch = id_collaSwitch;
    }

    public void getIdColla(String nomColla) {

    }


}
