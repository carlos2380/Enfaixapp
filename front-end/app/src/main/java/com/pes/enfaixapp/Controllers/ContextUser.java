package com.pes.enfaixapp.Controllers;

/**
 * Created by carlos on 10/12/2016.
 */
public class ContextUser {

    private static String id;

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
