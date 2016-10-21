package com.example.sjors.sjors_witteveen_pset6;

import java.util.ArrayList;

public class Jokes {

    private ArrayList<Joke> jokes = new ArrayList<>();
    private Joke activeJoke;

    private static Jokes ourInstance = new Jokes();

    public static Jokes getInstance() {
        return ourInstance;
    }

    private Jokes() {
    }

    public void clear() {
        jokes.clear();
    }

    public void saveActiveJoke() {
        jokes.add(activeJoke);
    }

    public void saveJoke(Joke joke) {
        jokes.add(joke);
    }

    public ArrayList<Joke> getJokes() {
        return jokes;
    }

    public void setActiveJoke(Joke activeJoke) {
        this.activeJoke = activeJoke;
    }

    public Joke getActiveJoke() {
        return activeJoke;
    }

}
