package com.santiago6695gmail.events;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

    public static String emailFieldString = "marat_dula@bentley.edu";// hardcoded string until global variable works
    public static EditText emailField; // where user enters email
//    public static String emailFieldString = emailField.getText().toString();// testing global variable
    public static String dumy; // dumy string to think
    private EditText passwordField; // where user enters password
    private Button signInButton; // sign in button
    private TextView userEmail; // text view of user email
    private TextView userPassword; // text view of user password
    public Intent i;

    private Thread t = null; //variable for thread
    private TextView slogan = null; // Textview for slogan animal
    private Button webLogIn; // web login button
    private Button admindial; // dialer button to call

    private String password; // string to hold the password
    private static final String tag = "Usernames: "; // unviversal tag to check usernames
    public String email; // using to store the email of the user as a string

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

        // setting up the text view and edit text views for user to enter
        emailField = (EditText) findViewById(R.id.enterEmail);
        passwordField = (EditText) findViewById(R.id.enterPassword);

        userEmail = (TextView) findViewById(R.id.email);
        userPassword = (TextView) findViewById(R.id.password);

        signInButton = (Button) findViewById(R.id.signin);
        signInButton.setOnClickListener(this);

        admindial = (Button) findViewById(R.id.dialerbutt);
        admindial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // dialer to call a cell phone for our teammate
                Uri dialuri = Uri.parse("tel:6039217921");
                Intent dialintent = new Intent(Intent.ACTION_CALL, dialuri);
                startActivity(dialintent); //Has a red line underneath, but runs completely normal
            }
        });


        // intent to declare to it wil move on to another class
        i = new Intent (this, Category.class);

        //button for web browser
        webLogIn = (Button) findViewById(R.id.log_in_web);
        webLogIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });


    }
    //opeing browser intent
    public void openBrowser() {
        Uri uri = Uri.parse("http://frodo.bentley.edu/CS460Teamc");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    // clikck on an object
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.signin: // sign in button click
            {

                emailFieldString = emailField.getText().toString(); // set email field to email field string
                Log.e("Checking",emailFieldString); // writing to the log checking if it got the username's email
                t = new Thread(background); // making a thread
                t.start(); // starting a thread
            }

        }
    }
    private Runnable background = new Runnable()
    {
        public void run(){
            //Database credentials and url for easuer use
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
               //executing the statement in the sql database
                ResultSet result = stmt.executeQuery("select password from cs460teamc.user WHERE email = '"+emailFieldString.trim()+"';");
                Log.e("LOGGINGIN",emailFieldString); // writing to the log loggining with username
                email = emailFieldString; //storing variable for use of global variable
                while (result.next())
                {
                    password = result.getString("password"); // set password
                    Log.e("pass",password); //logg the password what is it encrypted version
                }
                char[] ePassword = password.toCharArray(); // get the encrypted password and set character to array
                ePassword[2] = 'a'; // get the third character in the array a replace with "a" insted of "y" for decryption
                password = String.valueOf(ePassword); // set the password a string

                if(BCrypt.checkpw(passwordField.getText().toString().trim(), password)) // if the encrypted password from database equals the encyrpted password of user
                {
                    Log.e("M", "True"); // if the password match log it and start activity
                    startActivity(i);
                }
                else
                {
                    Log.e("M","False"); // if ecnryption passwords don't match
                }
                Log.e("JDBC TEST", password); // see which password was used

                //clean up
                //t = null;
            } catch (SQLException e) { // catch
                Log.e("JDBC","problems with SQL sent to "+URL+
                        ": "+e.getMessage());
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    Log.e("JDBC","problems with SQL sent to "+URL+
                            ": "+e.getMessage());
                }

            }

        }
    };
}

