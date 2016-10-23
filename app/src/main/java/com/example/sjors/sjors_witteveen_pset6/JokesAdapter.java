package com.example.sjors.sjors_witteveen_pset6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * This class is a custom ArrayAdapter. It takes an ArrayList of Joke objects and sets it to the
 * joke_list_item layout.
 *
 * By Sjors Witteveen
 */

public class JokesAdapter extends ArrayAdapter<Joke> {

    // constructor
    public JokesAdapter(Context context, ArrayList<Joke> jokes) {
        super(context, R.layout.joke_list_item, jokes);
    }

    // adapting array to fit joke_list_item layout
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.joke_list_item, parent, false);

        TextView jokeText = (TextView) view.findViewById(R.id.joke_text);

        jokeText.setText(getItem(position).getJoke());

        return view;
    }
}