package com.pes.enfaixapp.Models;

/**
 * Created by carlos on 15/11/2016.
 */
import java.util.Date;
import java.util.List;

/**
 * Created by carlos on 13/11/2016.
 */

public class Noticia {
    String titulo;
    String description;
    String url;
    Date date;
    int photoId;

    public Noticia() {
    }
    public Noticia(String name, String age, int photoId) {
        this.titulo = name;
        this.description = age;
        this.photoId = photoId;
    }

    public List<Noticia> noticias;

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}