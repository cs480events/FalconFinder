package com.santiago6695gmail.events;

/* This class allows the user to select the categories to narrow down the database's query by */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by TORRES_SANT on 4/27/2016.
 */
public class Category extends Activity implements View.OnClickListener {

    private Button allbutton; //Button for allowing all events to appear
    private Button athleticbutton; //Button to show just athletic events
    private Button careerbutton; //Button for just career events
    private Button otherbutton; //Button for everything else

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

    public void onClick(View v) { //Set up for all different cases
        switch(v.getId()) {
            case R.id.all: {
                Intent i = new Intent(this,EventList.class); //For each case, a new intent is created to hold the query
                i.putExtra("Category", "SELECT * FROM cs460teamc.eventlist WHERE MONTH(DATE) = MONTH(now()) AND YEAR(DATE)=YEAR(now()) AND DAY(DATE)>=DAY(now()) AND EventID NOT IN (SELECT EVENT_ID FROM cs460teamc.User_event WHERE email =" + MainActivity.useremail + ");");
                startActivity(i); //Start EventList when selected
                break; //Break, to prevent moving to next option!
            }

            case R.id.Sports: {
                Intent a = new Intent(this,EventList.class); //For each case, a new intent is created to hold the query
                a.putExtra("Category", "SELECT * FROM cs460teamc.eventlist WHERE CATEGORY = 'ATHLETICS' AND EventID NOT IN (SELECT EVENT_ID FROM cs460teamc.User_event WHERE email =" + MainActivity.useremail + ");");
                startActivity(a); //Start EventList when selected
                break; //Break, to prevent moving to next option!
            }

            case R.id.Career_Service:{
                Intent b = new Intent(this,EventList.class); //For each case, a new intent is created to hold the query
                b.putExtra("Category", "SELECT * FROM cs460teamc.eventlist WHERE CATEGORY = 'CAREERS' AND EventID NOT IN (SELECT EVENT_ID FROM cs460teamc.User_event WHERE email =" + MainActivity.useremail + ");");
                startActivity(b); //Start EventList when selected
                break; //Break, to prevent moving to next option!
            }

            case R.id.Other:{
                Intent c = new Intent(this,EventList.class); //For each case, a new intent is created to hold the query
                c.putExtra("Category", "SELECT * FROM cs460teamc.eventlist WHERE CATEGORY != 'ATHLETICS' AND CATEGORY != 'CAREERS' AND EventID NOT IN (SELECT EVENT_ID FROM cs460teamc.User_event WHERE email =" + MainActivity.useremail + ");" );
                        startActivity(c); //Start EventList when selected
                break; //Break, to prevent moving to next option!
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
        Intent i = new Intent (this, UserEvents.class);
        startActivity(i);
    }
    // exit the app
    public void exit(MenuItem item) {
        System.exit(0);
    }

}
