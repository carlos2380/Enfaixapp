package com.pes.enfaixapp.Controllers;

import java.util.ArrayList;

/**
 * Created by carlos on 10/12/2016.
 */
public class ContextUser {

    private static String id;
    private static ArrayList<String> id_collaPerteneix;
    private static ArrayList<String> id_collaAdmin;



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

    public static ArrayList<String> getId_collaAdmin() {
        return id_collaAdmin;
    }

    public void setId_collaAdmin(ArrayList<String> id_collaAdmin) {
        this.id_collaAdmin = id_collaAdmin;
    }

    public static ArrayList<String> getId_collaPerteneix() {
        return id_collaPerteneix;
    }

    public void setId_collaPerteneix(ArrayList<String> id_collaPerteneix) {
        this.id_collaPerteneix = id_collaPerteneix;
    }
}
