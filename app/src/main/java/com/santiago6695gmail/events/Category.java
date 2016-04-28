package com.santiago6695gmail.events;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by TORRES_SANT on 4/27/2016.
 */
public class Category extends Activity implements View.OnClickListener {

    private Button allbutton;
    private Button athleticbutton;
    private Button careerbutton;
    private Button otherbutton;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.category_list);
        //hide title and icon in action bar
        ActionBar actionBar = getActionBar();
        try {
            actionBar.show();
        }
        catch(Exception e) {
            Log.e("Error","Error with actionbar");
        }
        //add onclick to buttons
        allbutton = (Button) findViewById(R.id.all);
        allbutton.setOnClickListener(this);

        athleticbutton = (Button) findViewById(R.id.Sports);
        athleticbutton.setOnClickListener(this);

        careerbutton = (Button) findViewById(R.id.Career_Service);
        careerbutton.setOnClickListener(this);

        otherbutton = (Button) findViewById(R.id.Other);
        otherbutton.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.all: {
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category", "*");
                startActivity(i);
            }
            case R.id.Sports: {
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category", "Athletics");
                startActivity(i);
            }
            case R.id.Career_Service:{
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category", "Careers");
                startActivity(i);
            }
            case R.id.Other:{
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category", "Other");
                startActivity(i);
            }
        }
    }
    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void goCategory (MenuItem item) {
        setContentView(R.layout.category_list);
        Intent i = new Intent(this, Category.class);
        startActivity(i);
    }

    // go to userevents
    public void goUserEvents(MenuItem item) {
        setContentView(R.layout.userlist);
        Intent i = new Intent (this, UserEvents.class);
        startActivity(i);
    }
    //go all events
    public void goAllEvents(MenuItem item) {
        setContentView(R.layout.eventlist);
        Intent i = new Intent (this, EventList.class);
        startActivity(i);
    }
    // exit the app
    public void exit(MenuItem item) {
        System.exit(0);
    }

}
