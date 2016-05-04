package com.santiago6695gmail.events;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by MARAT_DULA on 5/4/2016.
 */
public class SignUp extends Activity implements OnClickListener {

    private TextView firstName; // text view of user email
    private TextView lastName; // text view of user password
    private TextView email; //fdkjafklasdjf;kls
    private TextView password;

    private EditText enterFirstName;
    private EditText enterLastName;
    private EditText enterEmail; // where user enters email
    private EditText enterPassword;

    private String firstNameString;
    private String lastNameString;
    private String emailString;
    private String passwordString;
    private String useremail;

    private Button signUp;
    private Thread t = null; //variable for thread
    private Runnable background = new Runnable() {
        public void run() {
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
            Connection con = null;
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
                // Hash a password for the first time
                String hashed = BCrypt.hashpw(passwordString, BCrypt.gensalt());

                stmt.executeUpdate("insert into cs460teamc.user values " +
                        "(NULL, '" + emailString + "','" + hashed + "','" + firstNameString + "','" + lastNameString + "');");

            } catch (SQLException e) { // catch
                Log.e("JDBC", "problems with SQL sent to " + URL +
                        ": " + e.getMessage());
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    Log.e("JDBC", "problems with SQL sent to " + URL +
                            ": " + e.getMessage());
                }

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        enterFirstName = (EditText) findViewById(R.id.enterFirstName);
        enterLastName = (EditText) findViewById(R.id.enterLastName);
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        enterPassword = (EditText) findViewById(R.id.enterPassword);

        enterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);

        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(this);


    }

    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.signup: // sign in button click
            {
                firstNameString = enterFirstName.getText().toString(); // set email field to email field string
                lastNameString = enterLastName.getText().toString(); // set email field to email field string
                emailString = enterEmail.getText().toString(); // set email field to email field string
                passwordString = enterPassword.getText().toString(); // set email field to email field string

                useremail = "'"+emailString+"'";
                Log.e("Checking", emailString); // writing to the log checking if it got the username's email
                t = new Thread(background); // making a thread
                t.start(); // starting a thread
                break;
            }

        }
    }

}