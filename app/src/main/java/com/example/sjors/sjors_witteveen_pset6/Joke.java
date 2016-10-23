package com.example.sjors.sjors_witteveen_pset6;

/*
 * This class represents a Joke object consisting of two Strings: id and joke.
 *
 * By Sjors Witteveen
 */

public class Joke {

    private String id;
    private String joke;

    // constructor
    public Joke(String id, String joke) {
        this.id = id;
        this.joke = joke;
    }

    public String getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }

}
