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
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateClass extends Activity implements View.OnClickListener {


    MySQLiteHelper db = new MySQLiteHelper(this);

    public int tryFail = 1;
    public int tryFailFormat =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        View CreateButton = findViewById(R.id.createaccount_button);
        CreateButton.setOnClickListener(this);

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
        String input1;
        String input2;
        EditText cinput1;
        EditText cinput2;

        cinput1 = (EditText) findViewById(R.id.username_field);
        input1 = cinput1.getText().toString();

        cinput2 = (EditText) findViewById(R.id.password_field);
        input2 = cinput2.getText().toString();


        if (v.getId() == R.id.createaccount_button) {

            if(checkFormat(input1)&&checkFormat(input2)&&userDuplicateCheck(input1)) {

                User user = new User(input2, input1);
                db.addUser(user);


                TimeStamp timeStamp = new TimeStamp();


                Transaction transactionNew = new Transaction(user.getUsername(),3, timeStamp.getDate(), timeStamp.getTime());
                db.addTransaction(transactionNew);


                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Successfully Created Account");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent I = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(I);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            else if(!userDuplicateCheck(input1)&&tryFail<2){
                tryFail++;
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Username already Exists, Try once more.");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            else if(!userDuplicateCheck(input1)&&tryFail==2){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Username already Exists.");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent I = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(I);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            else if(userDuplicateCheck(input1)&&tryFailFormat<2){
                tryFailFormat++;
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Your Username or Password are not correctly formatted! last chance.");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            else if(userDuplicateCheck(input1)&&tryFailFormat==2){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Your Username or Password are not correctly formatted!" + "Username and Password Must Contain UpperCase, Special Characters and Numbers!");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent I = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(I);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        }
    }


    public boolean checkFormat(String username){

        ArrayList<Character> charList = new ArrayList<Character>();
        Collections.addAll(charList, '!', '$', '#', '@');


        boolean containsSpecial = false;
        for(Character character: charList) {
            if (username.contains(Character.toString(character))) {
                containsSpecial = true;
            }
        }

        int numberCharacters = 0;
        if(containsSpecial){
            for(int i=0; i<username.length();i++){
                String symbol = String.valueOf(username.charAt(i));


                Pattern pattern = Pattern.compile("[A-z]");
                Matcher matcher = pattern.matcher(symbol);

                if(matcher.matches()){
                    numberCharacters++;
                    System.out.println("Match: " + numberCharacters);
                }
            }
        }

        if(numberCharacters>2){
            return true;
        }
        else {
            return false;
        }
    }


    public boolean userDuplicateCheck(String username){
        ArrayList<User> users = new ArrayList<>(db.getAllUsers());

        for(User user : users){
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

}
