package com.pes.enfaixapp.Models;

/**
 * Created by Àlex on 24/10/2016.
 */

public class Colla {

    private int id;
    private String name;
    private String color;
    private String image;
    private boolean uni;
    private String descripcio;
    private String email;
    private String telefono;
    private String direccio;

    public void setValue(int value) {
        this.value = value;
    }

    private int value;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    private String web;
    private boolean seleccionada;

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    private boolean seleccionadaSeguida;

    public Colla() {
        super();
    }

    public Colla(int id, String title, String color) {
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {return id;}

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public boolean isSeleccionadaSeguida() {
        return seleccionadaSeguida;
    }

    public void setSeleccionadaSeguida(boolean seleccionadaSeguida) {
        this.seleccionadaSeguida = seleccionadaSeguida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Colla colla = (Colla) o;

        return id == colla.id;

    }
    public int getValue(){
        return this.value;
    }
    @Override
    public int hashCode() {
        return id;
    }


}
