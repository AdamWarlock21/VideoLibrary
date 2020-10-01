package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Inventory extends Activity implements View.OnClickListener {


    MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        View mainButton = findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        mainButton.bringToFront();

        TextView main = (TextView) findViewById(R.id.maininventory);

        ArrayList<Movie> movies = new ArrayList<>(db.getAllMovies());

        for(Movie movie : movies){
            main.append(movie.toString()+" \n");
        }

        db.getAllMovies();


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
        if (v.getId() == R.id.main_button) {
            Intent I = new Intent(getApplicationContext(), ManageSystem.class);
            startActivity(I);
        }


    }

}