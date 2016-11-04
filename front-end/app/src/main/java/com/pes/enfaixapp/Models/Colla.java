package com.pes.enfaixapp.Models;

/**
 * Created by Àlex on 24/10/2016.
 */

public class Colla {

    private String nom;
    private String description;
    private int imagen;
    private boolean checked;

    public Colla() {
        super();
    }

    public Colla(String title, String description, int imagen) {
        super();
        this.nom = title;
        this.description = description;
        this.imagen = imagen;
        this.checked = false;
    }


    public Colla(String title, boolean checked, int imagen) {
        super();
        this.nom = title;
        this.checked = checked;
        this.imagen = imagen;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String title) {
        this.nom = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setChecked() {
        this.checked = true;
    }

    public boolean getChecked() {
        return checked;
    }

}
