package com.pes.enfaixapp.Models;

/**
 * Created by carlos on 11/01/2017.
 */

public class Ranking {
    private String name;
    private int punctuation;
    private int pos;

    public Ranking() {
    }

    public Ranking(int punctuation, String name,int pos) {
        this.name = name;
        this.punctuation = punctuation;
        this.pos = pos;
    }

    public Ranking(String name, int punctuation) {
        this.name = name;
        this.punctuation = punctuation;
        this.pos = -1;
    }

    public String getName() {
        return this.name;
    }

    public int getPunctuation() {
        return this.punctuation;
    }

    public int getPos () {
        return this.pos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPunctuation (int punctuation) {
        this.punctuation = punctuation;
    }

    public void setPos (int pos) {
        this.pos = pos;
    }
}
