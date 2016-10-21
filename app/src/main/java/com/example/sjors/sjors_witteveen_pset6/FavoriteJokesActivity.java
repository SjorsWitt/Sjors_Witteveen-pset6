package com.example.sjors.sjors_witteveen_pset6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FavoriteJokesActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteJokesActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Jokes jokes;
    private FirebaseUser user;
    private DatabaseReference jokesReference;
    private ValueEventListener jokesListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_jokes);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        jokes = Jokes.getInstance();
        user = mAuth.getCurrentUser();
        jokesReference = database.getReference("/users/" + user.getUid() + "/jokes/");

        final ListView jokeList = (ListView) findViewById(R.id.joke_list);
        final JokesAdapter adapter = new JokesAdapter(this, jokes.getJokes());
        jokeList.setAdapter(adapter);

        // ValueEventListener
        // if data has changed, refresh jokes singleton and notify adapter
        jokesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jokes.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Joke joke = new Joke(child.getKey(), (String) child.getValue());
                    jokes.saveJoke(joke);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        jokeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // removes ToDoItem from ToDoItems ArrayList and notifies adapter
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Joke joke = (Joke) jokeList.getItemAtPosition(position);
                jokesReference.child(joke.getId()).removeValue();
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        jokesReference.addValueEventListener(jokesListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (jokesListener != null) {
            jokesReference.removeEventListener(jokesListener);
        }
    }
}
