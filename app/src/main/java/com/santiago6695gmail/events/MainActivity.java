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
import android.widget.AdapterView;
import android.widget.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private Thread t = null;
    private ArrayList<String> list = new ArrayList<String>();
    private TextView slogan = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        //hide title and icon in action bar
        ActionBar actionBar = getActionBar();
        try {
            actionBar.show();
        }
        catch(NullPointerException e) {
            Log.e("Error","Action Bar failed");
        }
        //animated slogan
        slogan = (TextView) findViewById(R.id.slogan);
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(5000);
        slogan.startAnimation(in);
        slogan.setText("Find all events on campus!");

        Button signUpButton = (Button) findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.sign_up);
                //start thread
                t = new Thread(background);
                t.start();
            }
        });
        Button switcheventbutton = (Button) findViewById(R.id.eventlistswitch);
        switcheventbutton.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent i = new Intent (this, Category.class);
        startActivity(i);
    }



    private Runnable background = new Runnable() {
    public void run(){
        String URL = "jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc";
        String username = "cs460teamc";
        String password = "cs460teamc";

        try { //load driver into VM memory
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.e("JDBC", "Did not load driver");

        }

        Statement stmt = null;
        Connection con=null;
        try { //create connection and statement objects
            con = DriverManager.getConnection(
                    URL,
                    username,
                    password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            Log.e("JDBC", "problem connecting");
        }

        try {
            ResultSet result = stmt.executeQuery("select * from cs460teamc.eventlist;");

            //read result set, write data to ArrayList and Log
            while (result.next()) {
                String city = result.getString("summary");
                Log.e("JDBC",city );
            }
            //clean up
            t = null;


        } catch (SQLException e) {
            Log.e("JDBC","problems with SQL sent to "+URL+
                    ": "+e.getMessage());
        }
        catch (NullPointerException e) {
            Log.e("NULL", "some other null pointer error");
        }

    }
};

}

