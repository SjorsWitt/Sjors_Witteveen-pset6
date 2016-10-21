/*
package com.example.sjors.sjors_witteveen_pset6;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;

public class JokesListener implements ValueEventListener {

    private Jokes jokes;

    //constructor
    public JokesListener() {
        jokes = Jokes.getInstance();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        jokes.clear();
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            jokes.add((String) child.getValue());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
    }
}
*/