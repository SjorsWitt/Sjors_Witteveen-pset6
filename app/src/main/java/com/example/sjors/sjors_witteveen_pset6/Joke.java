package com.example.sjors.sjors_witteveen_pset6;

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
