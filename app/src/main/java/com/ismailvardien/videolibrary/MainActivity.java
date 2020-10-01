package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View CreateButton = findViewById(R.id.createaccount_button);
        CreateButton.setOnClickListener(this);

        View HoldButton = findViewById(R.id.placehold_button);
        HoldButton.setOnClickListener(this);

        View cancelButton = findViewById(R.id.cancelhold_button);
        cancelButton.setOnClickListener(this);

        View manageButton = findViewById(R.id.managesystem_button);
        manageButton.setOnClickListener(this);



        ArrayList<Movie> movies = new ArrayList<>(db.getAllMovies());
        ArrayList<User> users = new ArrayList<>(db.getAllUsers());

        if(movies.size()<1){
            Movie Avengers = new Movie("Avengers Endgame", "The Russo Brothers", "123-ABC-101", 1.00);
            Movie Pokemon = new Movie("Detective Pikachu", "Rob Letterman", "ABCDEF-09", 0.75);
            Movie Eholmes = new Movie("Enola Holmes", "Harry Bradbeer", "CDE-777-123", 2.00);
            Movie Bwidow = new Movie("Black Widow", "Cate Shortland", "BLW-726-109", 2.50);
            Movie Ththree = new Movie("The Conjuring 3", "Michael Chaves", "THC-689-009", 2.75);
            db.addMovie(Avengers);
            db.addMovie(Pokemon);
            db.addMovie(Eholmes);
            db.addMovie(Bwidow);
            db.addMovie(Ththree);
        }

        if(users.size()<1){
            User ismail = new User("ivee21!","ismail21!");
            User aneeqa = new User("abrahams22!","aneeqa22!");
            User ebrahiem = new User("mugetsu13!","ebi1601!");
            User admin = new User("!admin2", "!admin2");
            db.addUser(ismail);
            db.addUser(aneeqa);
            db.addUser(ebrahiem);
            db.addUser(admin);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.createaccount_button) {
            Intent I = new Intent(getApplicationContext(), CreateClass.class);
            startActivity(I);
        }
        else if (v.getId() == R.id.placehold_button) {
            Intent I = new Intent(getApplicationContext(),PlaceHold.class);
            startActivity(I);
        }
        else if (v.getId() == R.id.cancelhold_button) {
            Intent I = new Intent(getApplicationContext(),LoginCancel.class);
            startActivity(I);
        }
        else if (v.getId() == R.id.managesystem_button) {
            Intent I = new Intent(getApplicationContext(),LoginAdmin.class);
            startActivity(I);
        }

    }

}
