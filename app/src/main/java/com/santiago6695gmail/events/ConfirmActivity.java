package com.santiago6695gmail.events;

/**
 * Created by LEONARD_THOM on 4/25/2016.
 */
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

    private Button yesbutton;
    private Button nobutton;
    private String value;
    private TextView tview;
    private Thread t = null;
    private Toast tust = Toast.makeText(this, value + " added!", Toast.LENGTH_LONG);
    private Intent movebackyes = new Intent(this, EventList.class);

    Handler infohandler = new Handler() {
        public void handleMessage(Message msg) { //Method which handles the messages sent
            if (msg.obj.equals("IsDone")){

                tust.show();
                startActivity(movebackyes);

            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.finalcheck);

        Bundle extras = getIntent().getExtras();
        value = extras.getString("Switcher");

        //Format
        int locationofend = value.indexOf("  ");
        String grabbedname = value.substring(1, locationofend);

        Log.w("ABCD", value);

        tview = (TextView) findViewById(R.id.status);
        tview.setText("Add " + grabbedname + " to your events?");


        yesbutton = (Button) findViewById(R.id.yesbutton);
        yesbutton.setOnClickListener(this);

        nobutton = (Button) findViewById(R.id.nobutton);
        nobutton.setOnClickListener(this);

        t = new Thread(background);

        }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.yesbutton:

                t.start();

                break;

            case R.id.nobutton:
                Intent movebackno = new Intent(this, EventList.class);
                startActivity(movebackno);
                break;
        }
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

                //stmt.executeUpdate("insert into first values(1, 'Waltham');");


                con.close();

            } catch (SQLException e) {
                Log.e("JDBC", "problems with SQL sent to " + URL +
                        ": " + e.getMessage());
            }

            Message msg = infohandler.obtainMessage(1, "IsDone"); //Shooting the information over to the handler for inspection
            infohandler.sendMessage(msg);

        }




    };




}



