package com.example.sjors.sjors_witteveen_pset6;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ReadJokeFromJsonURL extends AsyncTask<Void, Void, Void> {

    private Jokes jokes;
    private URL url;
    private JSONObject randomJoke;
    private TextView jokeText;
    private Button saveButton;

    public ReadJokeFromJsonURL(TextView jokeText, Button saveButton, URL url) {
        this.url = url;
        this.jokeText = jokeText;
        this.saveButton = saveButton;
        jokes = Jokes.getInstance();
    }

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

    @Override
    protected void onPostExecute(Void result) {

        // retrieve JSON item info
        if (randomJoke != null) {
            try {
                if (randomJoke.getString("type").equals("success")) {
                    JSONObject value = randomJoke.getJSONObject("value");
                    String idString = value.getString("id");
                    String jokeString = value.getString("joke").replaceAll("&quot;", "\"");

                    Joke joke = new Joke(idString, jokeString);
                    jokes.setActiveJoke(joke);

                    jokeText.setText(jokeString);
                    saveButton.setEnabled(true);
                } else {
                    jokeText.setText(randomJoke.getString("type"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onPostExecute(result);
    }
}