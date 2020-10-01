package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Confirm extends Activity implements View.OnClickListener {

    int reservation = 0;


    MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);


        ArrayList<Transaction> transactions = new ArrayList<>(db.getAllTransactions());
        for(Transaction transaction : transactions){
            if(transaction.getTypeNumber()==1){reservation++;}
        }

        View test = findViewById(R.id.confirm_button);
        test.setOnClickListener(this);

        TextView main = (TextView) findViewById(R.id.main);


        Bundle extras= getIntent().getExtras();

        String pickUpYear = extras.getString("pickUpYear");
        String pickUpMonth = extras.getString("pickUpMonth");
        String pickUpDay = extras.getString("pickUpDay");
        String pickUpHour = extras.getString("pickUpHour");
        String pickUpAmPm = extras.getString("pickUpAmPm");

        String dropOffYear = extras.getString("dropOffYear");
        String dropOffMonth = extras.getString("dropOffMonth");
        String dropOffDay = extras.getString("dropOffDay");
        String dropOffHour = extras.getString("dropOffHour");
        String dropOffAmPm = extras.getString("dropOffAmPm");

        String loggedUsername = extras.getString("username");
        String movieTitle = extras.getString("title");
        double rentalTotal = extras.getDouble("rentalTotal");


        String pickUpDateTime;
        pickUpDateTime = getMonthNumber(pickUpMonth) +"/" + pickUpDay + "/" + pickUpYear+ " (" + pickUpHour +" "+ pickUpAmPm + ")";

        String dropOffDateTime;
        dropOffDateTime = getMonthNumber(dropOffMonth) +"/" + dropOffDay + "/" + dropOffYear + " (" + dropOffHour +" "+ dropOffAmPm + ")";



        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        main.setText("");
        main.append("Username: " + loggedUsername + " \n");
        main.append("Pick Up/Date Time: " + pickUpDateTime + " \n");
        main.append("Return/Date Time: " + dropOffDateTime + " \n");
        main.append("Movie Title: " + movieTitle + " \n");
        main.append("Reservation Number: " + (reservation+1) + " \n");
        main.append("Total Rental Cost: " + formatter.format(rentalTotal) + " \n");
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

        if (v.getId() == R.id.confirm_button) {

            Intent I = new Intent(getApplicationContext(), MainActivity.class);

            ArrayList<Movie> movies = new ArrayList<>(db.getAllMovies());


            Bundle extras= getIntent().getExtras();
            //PickUp
            int pickUpDayOfYear = extras.getInt("pickUpDayOfYear");
            String pickUpYear = extras.getString("pickUpYear");
            String pickUpMonth = extras.getString("pickUpMonth");
            String pickUpDay = extras.getString("pickUpDay");
            String pickUpHour = extras.getString("pickUpHour");
            String pickUpAmPm = extras.getString("pickUpAmPm");
            //DropOff
            int dropOffDayOfYear = extras.getInt("dropOffDayOfYear");
            String dropOffYear = extras.getString("dropOffYear");
            String dropOffMonth = extras.getString("dropOffMonth");
            String dropOffDay = extras.getString("dropOffDay");
            String dropOffHour = extras.getString("dropOffHour");
            String dropOffAmPm = extras.getString("dropOffAmPm");
            //Transaction
            int rentalHours = extras.getInt("rentalHours");
            String loggedUsername = extras.getString("username");
            int loggedId = extras.getInt("id");
            String bookTitle = extras.getString("title");
            double rentalTotal = extras.getDouble("rentalTotal");


            String pickUpDateTime;
            pickUpDateTime = getMonthNumber(pickUpMonth) +"/" + pickUpDay + "/" + pickUpYear+ " (" + pickUpHour +" "+ pickUpAmPm + ")";

            String dropOffDateTime;
            dropOffDateTime = getMonthNumber(dropOffMonth) +"/" + dropOffDay + "/" + dropOffYear + " (" + dropOffHour +" "+ dropOffAmPm + ")";



            for(int i = 0; i< movies.size(); i++){
                if(movies.get(i).getTitle().equals(bookTitle)){
                    System.out.println("getAll This is the movie by Title: " + movies.get(i).getTitle());
                    String[] fifteen;

                    fifteen = movies.get(i).getFifteen();


                    for (int j = pickUpDayOfYear; j < dropOffDayOfYear+1; j++) {

                        fifteen[j] = "1";

                        movies.get(i).setFifteenString(fifteen);
                    }


                    db.updateBook(movies.get(i));
                }
            }




            TimeStamp timeStamp = new TimeStamp();

            Transaction transaction = new Transaction(loggedUsername, 1, rentalTotal, bookTitle, timeStamp.getDate(),
                    timeStamp.getTime(), pickUpDateTime, dropOffDateTime, pickUpDayOfYear, dropOffDayOfYear, (reservation+1));



            db.addTransaction(transaction);
            startActivity(I);
        }
    }

    public int getMonthNumber(String month){
        if(month.equals("January")){
            return 1;
        }
        else if(month.equals("February")){
            return 2;
        }
        else if(month.equals("March")){
            return 3;
        }
        else if(month.equals("April")){
            return 4;
        }
        else if(month.equals("May")){
            return 5;
        }
        else if(month.equals("June")){
            return 6;
        }
        else if(month.equals("July")){
            return 7;
        }
        else if(month.equals("August")){
            return 8;
        }
        else if(month.equals("September")){
            return 9;
        }
        else if(month.equals("October")){
            return 10;
        }
        else if(month.equals("November")){
            return 11;
        }
        else if(month.equals("December")){
            return 12;
        }
        return 0;

    }

}
