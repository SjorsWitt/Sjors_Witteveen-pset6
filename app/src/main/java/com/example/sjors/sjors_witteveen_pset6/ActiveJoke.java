package com.example.sjors.sjors_witteveen_pset6;

import java.util.ArrayList;

/*
 * This class is a singleton object consisting of an ArrayList of all saved Joke objects and a Joke
 * that is currently displayed in the MainActivity.
 *
 * By Sjors Witteveen
 */

public class ActiveJoke {

    // Joke currently displayed in MainActivity
    private Joke activeJoke;

    private static ActiveJoke ourInstance = new ActiveJoke();

    public static ActiveJoke getInstance() {
        return ourInstance;
    }

    // constructor
    private ActiveJoke() {
    }

    // replaces old activeJoke with new activeJoke
    public void setActiveJoke(Joke activeJoke) {
        this.activeJoke = activeJoke;
    }

    public Joke getActiveJoke() {
        return activeJoke;
    }

}
