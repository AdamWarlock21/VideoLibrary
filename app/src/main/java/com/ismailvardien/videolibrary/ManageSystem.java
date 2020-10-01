package com.ismailvardien.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ManageSystem extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managesystem);

        View logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);

        View addBookButton = findViewById(R.id.addbook_button);
        addBookButton.setOnClickListener(this);

        View inventoryButton = findViewById(R.id.inventory_button);
        inventoryButton.setOnClickListener(this);

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

        if (v.getId() == R.id.log_button) {
            Intent I = new Intent(getApplicationContext(), Log.class);
            startActivity(I);
        }
        else if (v.getId() == R.id.addbook_button) {
            Intent I = new Intent(getApplicationContext(), AddMovie.class);
            startActivity(I);
        }
        else  if(v.getId() == R.id.inventory_button) {
            Intent I = new Intent(getApplicationContext(), Inventory.class);
            startActivity(I);
        }

    }

}