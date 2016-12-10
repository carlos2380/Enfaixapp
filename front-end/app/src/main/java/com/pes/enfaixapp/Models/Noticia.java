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
    String date;

    public Noticia() {
    }
    public Noticia(String name, String age, int photoId) {
        this.titulo = name;
        this.description = age;
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}