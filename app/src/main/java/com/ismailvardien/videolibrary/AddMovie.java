package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;




public class AddMovie extends Activity implements View.OnClickListener {


    MySQLiteHelper db = new MySQLiteHelper(this);
    ArrayList<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmovie);

        View addBookButton = findViewById(R.id.addmovie_button);
        addBookButton.setOnClickListener(this);

        movies = new ArrayList<>(db.getAllMovies());
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

    String title;
    String director;
    String movieCode;
    String cost;
    double price;

    EditText titleEdit;
    EditText directorEdit;
    EditText movieCodeEdit;
    EditText priceEdit;

    public void onClick(View v) {

        titleEdit = (EditText) findViewById(R.id.title);
        title = titleEdit.getText().toString();

        directorEdit = (EditText) findViewById(R.id.director);
        director = directorEdit.getText().toString();

        movieCodeEdit = (EditText) findViewById(R.id.movieCode);
        movieCode = movieCodeEdit.getText().toString();

        priceEdit = (EditText) findViewById(R.id.price);
        cost = priceEdit.getText().toString();


        boolean containsDec = false;
        boolean bookExists = false;
        if (v.getId() == R.id.addmovie_button) {
            for (Movie movie : movies) {
                if (movie.getMovieCode().equals(movieCode)) {
                    bookExists = true;
                    break;
                }
            }
            //Check for a duplicate book in existing movies
            if (bookExists) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Information is not valid, movie already exists");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent I = new Intent(getApplicationContext(), ManageSystem.class);
                                startActivity(I);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else{

                for (int i = 0; i < cost.length(); i++) {
                    if (cost.charAt(i) == '.') {
                        containsDec = true;
                        break;
                    }
                }
                if (containsDec) {
                    price = Double.parseDouble(cost);

                    Movie addMovie = new Movie(title, director, movieCode, price);
                    db.addMovie(addMovie);
                    //Success Alert
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Successfully added movie, Check Inventory ");
                    dlgAlert.setTitle("Video Library System");
                    dlgAlert.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent I = new Intent(getApplicationContext(), ManageSystem.class);
                                    startActivity(I);
                                }
                            });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                //No decimal exists
                else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Sorry The cost is invalid! Needs a Decimal");
                    dlgAlert.setTitle("Video Library System");
                    dlgAlert.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }
        }
    }
}