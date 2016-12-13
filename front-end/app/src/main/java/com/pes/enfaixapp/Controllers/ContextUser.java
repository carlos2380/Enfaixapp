package com.pes.enfaixapp.Controllers;

/**
 * Created by carlos on 10/12/2016.
 */
public class ContextUser {

    private static String id;
    private String id_collaUni;
    private String id_collaConv;


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

}
