package com.santiago6695gmail.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfirmActivity extends Activity implements OnClickListener {

    private Button yesbutton; //Button for if user clicks yes
    private Button nobutton; //Button for if user clicks no
    private String value; //String to hold initial String passed via intent
    private String grabbedname; //Grabbing only the name from the string
    private String PRIMARYKEY; //Grabbing only the PK from the string
    private TextView tview; //Text view widget
    private Thread t = null; //Background thread for running JDBC
    private Toast tust; //Toast to let user know event was successfully added
    private Intent movebackyes; //Moves user back to EventList if yes is clicked
    private Intent movebackno; //Moves user back to EventList if no is clicked
    private String useremail = "'XIE_XIAO@bentley.edu'"; //Email/login of the current user


    Handler infohandler = new Handler() { //Method which handles the messages sent
        public void handleMessage(Message msg) {
            if (msg.obj.equals("IsReallyDone")){ //If message is successfully received...

                tust.show(); //Show user the success toast
                startActivity(movebackyes); //Move back to the main event list

            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.finalcheck);

        Bundle extras = getIntent().getExtras(); //Grabbing from the EventList intent
        value = extras.getString("Switcher");

        //Format
        int locationofend = value.indexOf(":::"); //Using my ::: as a reference to determine the end of the name
        grabbedname = value.substring(0, locationofend); //Use the start of the String and the locationofend int to pull out the name

        PRIMARYKEY = value.substring(locationofend + 3, value.length()); //Use locationofend int and length of String to pull the PK

        tust = Toast.makeText(this, grabbedname + " added!", Toast.LENGTH_LONG); //Setting up the toast for later use

        //Initializing and setting text to the text view
        tview = (TextView) findViewById(R.id.status);
        tview.setText("Add " + grabbedname + " to your events?");

        //Initializing yes button
        yesbutton = (Button) findViewById(R.id.yesbutton);
        yesbutton.setOnClickListener(this);

        //Initializing no button
        nobutton = (Button) findViewById(R.id.nobutton);
        nobutton.setOnClickListener(this);

        //Setting t to the background runnable, to be used when we are ready
        t = new Thread(background);

        }


    public void onClick(View v) { //Onclick listener
        switch (v.getId()) {

            case R.id.yesbutton: //for yes

                movebackyes = new Intent(this, EventList.class); //Intent set up
                t.start(); //Start the background thread

                break;

            case R.id.nobutton:
                movebackno = new Intent(this, EventList.class); //intent set up AND fired off
                startActivity(movebackno);
                break;
        }
    }

    Runnable background = new Runnable() {
        @Override
        public void run() { //Background thread for JDBC


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

                Log.w("IMTOM", "insert into user_event values(null, " + useremail + ", " + PRIMARYKEY + ");");
                stmt.executeUpdate("insert into user_event values(null, " + useremail + ", " + PRIMARYKEY + ");");



                con.close();

            } catch (SQLException e) {
                Log.e("JDBC", "problems with SQL sent to " + URL +
                        ": " + e.getMessage());
            }

            Message msg = infohandler.obtainMessage(1, "IsReallyDone"); //Shooting the information over to the handler for inspection
            infohandler.sendMessage(msg);

        }




    };




}



