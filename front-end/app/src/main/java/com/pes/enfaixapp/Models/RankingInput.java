package com.pes.enfaixapp.Models;

/**
 * Created by Marc on 10/01/2017.
 */

public class RankingInput {
    private String name;
    private int punctuation;
    private int pos;

    public RankingInput() {
    }

    public RankingInput(String name, int punctuation) {
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
