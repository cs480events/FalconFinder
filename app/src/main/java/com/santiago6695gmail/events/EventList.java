package com.santiago6695gmail.events;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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


public class EventList extends Activity implements AdapterView.OnItemClickListener {

    private ListView lview; //For my list view widget
    private ArrayList<String> items; //An array list to hold all the list view items
    private ArrayAdapter<String> todoitems;
    private int counter;
    private String finaltask;
    private String positiontext;
    private int positionholder;
    private Button testbutton;
    private int x;
    private Thread thred = null;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) { //Method which handles the messages sent
            if (msg.obj.equals("IsDone")){
                todoitems.notifyDataSetChanged();
            }
        }
    };


    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.eventlist);

        //Setting up my list views
        lview = (ListView) findViewById(R.id.list);
        lview.setOnItemClickListener(this);

        //Adding to the array list each time the Add/Update menu option is selected
        items = new ArrayList<String>();

        //Setting up an array adapter and attaching a style
        todoitems = new ArrayAdapter<String>(this, R.layout.mylist, items);
        lview.setAdapter(todoitems);

        thred = new Thread(background);
        thred.start();
    }


    Runnable background = new Runnable() {
       @Override
       public void run() {


                   String URL = "jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc";
                   String username = "cs460teamc";
                   String password = "cs460teamc";

                   try { //load driver into VM memory
                       Class.forName("com.mysql.jdbc.Driver");
                   } catch (ClassNotFoundException e) {
                       Log.e("JDBC", "Did not load driver");

                   }

                   Statement stmt = null;
                   Connection con = null;
                   try { //create connection and statement objects

                       con = DriverManager.getConnection("jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc", "cs460teamc", "cs460teamc");

                       stmt = con.createStatement();

                   } catch (SQLException e) {
                       Log.e("JDBC", "problem connecting");
                   }

                   try {
                       // execute SQL commands to create table, insert data, select contents
                       //stmt.executeUpdate("drop table if exists first;");
                       //stmt.executeUpdate("create table first(id integer primary key, city varchar(25));");
                       //stmt.executeUpdate("insert into first values(1, 'Waltham');");
                       //stmt.executeUpdate("insert into first values(2, 'Cambridge');");
                       //stmt.executeUpdate("insert into first values(3, 'Boston');");

                       ResultSet result = stmt.executeQuery("select * from cs460teamc.eventlist;");

                       //read result set, write data to ArrayList and Log
                       while (result.next()) {
                           String eventnames = result.getString("summary");
                           String eventloc = "A Place";
                           String eventdate = "April 20th, 2016";
                           String eventtime = "12:00 PM";
                          // String eventloc = result.getString("location");
                          // String eventdate = result.getString("date");
                          // String eventtime = result.getString("time");
                           String finalevents = "   " + eventnames + "\n" + "\n" + "\n" + "   " + eventloc + "    ||    " + eventdate + "    ||    " + eventtime + "\n";
                           items.add(finalevents);

                       }
                       //clean up

                       con.close();

                   } catch (SQLException e) {
                       Log.e("JDBC", "problems with SQL sent to " + URL +
                               ": " + e.getMessage());
                   }

           Message msg = handler.obtainMessage(1, "IsDone"); //Shooting the information over to the handler for inspection
           handler.sendMessage(msg);

               }


   };





    //Required method. What to do when a list item is clicked
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        String nabber = items.get(position);

        Intent i = new Intent(this, ConfirmActivity.class);
        i.putExtra("Switcher", nabber);
        startActivity(i);
    }



}