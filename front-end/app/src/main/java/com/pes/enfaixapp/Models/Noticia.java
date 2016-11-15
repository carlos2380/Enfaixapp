package com.pes.enfaixapp.Models;

/**
 * Created by carlos on 15/11/2016.
 */
import java.util.List;

/**
 * Created by carlos on 13/11/2016.
 */

public class Noticia {
    String titulo;
    String contenido;
    String url;
    int photoId;


    public Noticia(String name, String age, int photoId) {
        this.titulo = name;
        this.contenido = age;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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
}