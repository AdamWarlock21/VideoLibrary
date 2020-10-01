package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Log extends Activity implements View.OnClickListener {

    // creates a database for the app
    MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        TextView main = (TextView) findViewById(R.id.maininventory);

        View mainButton = findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        mainButton.bringToFront();

        ArrayList<Transaction> transactions = new ArrayList<>(db.getAllTransactions());

        for(Transaction transaction: transactions){

            if(transaction.getTypeNumber()==1||transaction.getTypeNumber()==2) {
                main.append("\n\nTransaction Number: " + transaction.getId());
                main.append("\n     Reservation Number: " + transaction.getReservation());
                main.append("\n     Username: " + transaction.getUsername());
                main.append("\n     Transaction Type: " + transaction.getType());
                main.append("\n     Movie Title: " + transaction.getTitle());
                main.append("\n     Pick Up: " + transaction.getPickUpDate());
                main.append("\n     Return: " + transaction.getDropOffDate());
                main.append("\n     Transaction Date: " + transaction.getDate());
                main.append("\n     Transaction Time: " + transaction.getTime());
            }
            else{
                main.append("\n\nTransaction Number: " + transaction.getId());
                main.append("\n     Username: " + transaction.getUsername());
                main.append("\n     Transaction Type: " + transaction.getType());
                main.append("\n     Transaction Date: " + transaction.getDate());
                main.append("\n     Transaction Time: " + transaction.getTime());
            }
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
            Intent I = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(I);
        }
    }

}
