package com.example.sjors.sjors_witteveen_pset6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private Jokes jokes;
    private FirebaseUser user;
    private DatabaseReference jokesReference;

    private boolean doubleBackToExitPressedOnce = false;

    private TextView jokeText;
    private TextView loggedInAs;
    private Button saveButton;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeText = (TextView) findViewById(R.id.joke_text);
        loggedInAs = (TextView) findViewById(R.id.logged_in_as);
        saveButton = (Button) findViewById(R.id.save_button);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);

        // disable ability to save welcome message
        saveButton.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        jokes = Jokes.getInstance();
        user = mAuth.getCurrentUser();

        if (jokes.getActiveJoke() != null) {
            jokeText.setText(jokes.getActiveJoke().getJoke());
        }
        if (user != null) {
            String loggedInAsString = getString(R.string.logged_in_as) + " " + user.getDisplayName();
            loggedInAs.setText(loggedInAsString);

            jokesReference = database.getReference("/users/" + user.getUid() + "/jokes/");
        }

        // AuthStateListener
        // finish MainActivity when user is logged out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    MainActivity.this.finish();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add favorites button to action bar
        getMenuInflater().inflate(R.menu.button_favorites, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, FavoriteJokesActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Opened your favorite jokes list!",
                Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            mAuth.signOut();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_back_pressed, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void onRandomButtonClick(View view) {
        saveButton.setEnabled(false);
        try {
            String urlString = "http://api.icndb.com/jokes/random/?";
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();
            if (!firstNameString.isEmpty()) {
                urlString += "&firstName=" + URLEncoder.encode(firstNameString, "UTF-8");
            }
            if (!lastNameString.isEmpty()) {
                urlString += "&lastName=" + URLEncoder.encode(lastNameString, "UTF-8");
            }
            new ReadJokeFromJsonURL(jokeText, saveButton, new URL(urlString)).execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onSave(View view) {
        saveButton.setEnabled(false);
        Toast.makeText(this, R.string.saved_joke, Toast.LENGTH_SHORT).show();

        jokes.saveActiveJoke();
        jokesReference.child(jokes.getActiveJoke().getId())
                .setValue(jokes.getActiveJoke().getJoke());
    }

    public void signOut(View view) {
        mAuth.signOut();
    }
}
