package com.example.sjors.sjors_witteveen_pset6;

import java.util.ArrayList;

/*
 * This class is a singleton object consisting of an ArrayList of all saved Joke objects and a Joke
 * that is currently displayed in the MainActivity.
 *
 * By Sjors Witteveen
 */

public class Jokes {

    private ArrayList<Joke> jokes = new ArrayList<>();

    // Joke currently displayed in MainActivity
    private Joke activeJoke;

    private static Jokes ourInstance = new Jokes();

    public static Jokes getInstance() {
        return ourInstance;
    }

    // constructor
    private Jokes() {
    }

    // clear ArrayList
    public void clear() {
        jokes.clear();
    }

    // save activeJoke to jokes ArrayList
    public void saveActiveJoke() {
        jokes.add(activeJoke);
    }

    // add Joke to jokes ArrayList
    public void saveJoke(Joke joke) {
        jokes.add(joke);
    }

    // replaces old activeJoke with new activeJoke
    public void setActiveJoke(Joke activeJoke) {
        this.activeJoke = activeJoke;
    }

    public ArrayList<Joke> getJokes() {
        return jokes;
    }

    public Joke getActiveJoke() {
        return activeJoke;
    }

}
