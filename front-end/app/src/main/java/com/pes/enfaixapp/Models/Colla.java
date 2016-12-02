package com.pes.enfaixapp.Models;

/**
 * Created by Àlex on 24/10/2016.
 */

public class Colla {

    private int id;
    private String name;
    private int color;
    private String image;
    private boolean uni;

    public Colla() {
        super();
    }

    public Colla(int id, String title, int color) {
        this.id = id;
        this.name = title;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImagen(String image) {
        this.image = image;
    }

    public boolean isUniversitaria() {
        return uni;
    }

    public void setUniversitaria(Boolean uni) {
        this.uni = uni;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Colla colla = (Colla) o;

        return id == colla.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
