package com.santiago6695gmail.events;


/* This activity holds all of the events from the database, and will be modified depending on the filter from categories */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class    EventList extends Activity implements AdapterView.OnItemClickListener {

    private ListView lview; //For my list view widget
    private ArrayList<String> items; //An array list to hold all the list view items
    private ArrayAdapter<String> todoitems; //Array adapter for my list view
    private Thread thred = null; //Thread used for background task (JDBC)
    private ArrayList<String> itemswithID; //Second array list, to hold the names WITH id numbers as well
    public String selectquery; //To hold the query from categories

    Handler handler = new Handler() {
        public void handleMessage(Message msg) { //Method which handles the messages sent
            if (msg.obj.equals("IsDone")){ //Notify the array adapter that data is changed, if it gets the ok from my messenger
                todoitems.notifyDataSetChanged();
            }
        }
    };


    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.eventlist);

        //set up menu
        ActionBar actionBar = getActionBar();
        actionBar.show();

        //Setting up my list view
        lview = (ListView) findViewById(R.id.list);
        lview.setOnItemClickListener(this);

        //Initializing the array lists
        items = new ArrayList<String>();
        itemswithID = new ArrayList<String>();

        //Setting up the array adapter and attaching a style
        todoitems = new ArrayAdapter<String>(this, R.layout.mylist, items);
        lview.setAdapter(todoitems);

        //Begin background thread, so the events can be pulled from the database to the listview
        thred = new Thread(background);
        thred.start();

        Bundle extras = getIntent().getExtras(); //Grabbing from the EventList intent
        selectquery = extras.getString("Category");
        Log.e("ABCD", selectquery);
    }

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void goCategory (MenuItem item) {

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


    Runnable background = new Runnable() {
       @Override
       public void run() { //Background task for all that fun JDBC!


           String URL = "jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc"; //URL for our database

           try { //load the driver, and catch error if it happens early on
                       Class.forName("com.mysql.jdbc.Driver");
           } catch (ClassNotFoundException e) {
                       Log.e("JDBC", "Did not load driver");

           }


           //create connection and statement objects
           Statement stmt = null;
           Connection con = null;

                   try { //Try to connect

                       con = DriverManager.getConnection("jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc", "cs460teamc", "cs460teamc");
                       stmt = con.createStatement();

                   } catch (SQLException e) {
                       Log.e("JDBC", "problem connecting"); //If issues connecting
                   }

                   try {
                       ResultSet result = stmt.executeQuery(selectquery); //If it works, give us all of it!!!

                       while (result.next()) {

                           String eventnames = result.getString("summary"); //event name
                           String eventloc = result.getString("location"); //event location
                           if (eventloc == null ){ //Add the Dana Center as the default location for null event locations
                               eventloc = "Dana Center";
                           }else if(eventloc.equals("") ){
                               eventloc = "Dana Center";
                           }
                           String eventdate = result.getString("date"); //event date
                           String eventtime = result.getString("start_time"); //event start time
                           String eventID = result.getString("EventID"); //primary key, ABSOLUTELY NECCESARY
                           String finalevents = " " + eventnames + "  " +  "\n" + "\n" + "\n" + "   " +
                                   eventloc + "    ||    " + eventdate + "    ||    " + eventtime + "\n"; //format for the user's pleasure
                           items.add(finalevents); //add the formatted string to the list view

                           String thegoodstuff = eventnames + ":::" + eventID; //the stuff WE actually care about: name and PK
                           itemswithID.add(thegoodstuff); //add this stuff to a separate array
                       }

                       //clean up
                       con.close();

                   } catch (SQLException e) {
                       Log.e("JDBC", "problems with SQL sent to " + URL +
                               ": " + e.getMessage()); //If the SQL statements are wrong
                   }

           Message msg = handler.obtainMessage(1, "IsDone"); //Shooting the information over to the handler for inspection
           handler.sendMessage(msg);

               }


   };

    //Required method. What to do when a list item is clicked
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        String nabber = itemswithID.get(position); //We're actually grabbing from our second array that user DOES NOT see

        Intent i = new Intent(this, ConfirmActivity.class); //Intent to move us to the confirmation
        i.putExtra("Switcher", nabber); //Store the name and primary key of the event selected
        startActivity(i);
    }



}