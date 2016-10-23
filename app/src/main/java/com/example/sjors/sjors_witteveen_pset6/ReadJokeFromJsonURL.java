package com.example.sjors.sjors_witteveen_pset6;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/*
 * This class takes a URL, TextView and SaveButton as parameter. The id and joke are read from the
 * JSON retrieved from the URL. Once retrieved, these Strings are saved to Jokes singleton and
 * displayed in the TextView. Finally, the Button is enabled once the joke is displayed.
 *
 * By Sjors Witteveen
 */

public class ReadJokeFromJsonURL extends AsyncTask<Void, Void, Void> {

    private Jokes jokes;
    private URL url;
    private JSONObject randomJoke;
    private TextView jokeText;
    private Button saveButton;

    // constructor
    public ReadJokeFromJsonURL(TextView jokeText, Button saveButton, URL url) {
        this.url = url;
        this.jokeText = jokeText;
        this.saveButton = saveButton;
        jokes = Jokes.getInstance();
    }

    // show user the joke is being loaded by editing the TextView
    @Override
    protected void onPreExecute() {
        jokeText.setText(R.string.loading);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        // read from the URL
        String str = "";
        try {
            Scanner scan = new Scanner(url.openStream());
            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // build a JSON object
        try {
            randomJoke = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // retrieve JSON item info and set TextView and Button
    @Override
    protected void onPostExecute(Void result) {

        if (randomJoke != null) {
            try {
                if (randomJoke.getString("type").equals("success")) {
                    JSONObject value = randomJoke.getJSONObject("value");
                    String idString = value.getString("id");
                    String jokeString = value.getString("joke").replaceAll("&quot;", "\"");

                    // set retrieved Joke as active joke
                    Joke joke = new Joke(idString, jokeString);
                    jokes.setActiveJoke(joke);

                    // display retrieved joke in TextView and enable saveButton
                    jokeText.setText(jokeString);
                    saveButton.setEnabled(true);
                } else {
                    // display error if something went wrong
                    jokeText.setText(randomJoke.getString("type"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onPostExecute(result);
    }
}