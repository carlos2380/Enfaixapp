package com.pes.enfaixapp.Controllers;

/**
 * Created by carlos on 09/12/2016.
 */
public class CustomIntent {

    private String foto;
    private static CustomIntent ourInstance = new CustomIntent();

    public static CustomIntent getInstance() {
        return ourInstance;
    }

    private CustomIntent() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
