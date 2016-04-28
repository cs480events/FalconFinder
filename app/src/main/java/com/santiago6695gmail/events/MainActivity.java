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

    public static EditText emailField;
    public static String emailFieldString = "marat_dula@bentley.edu";
    public static String dumy;
    private EditText passwordField;
    private Button signInButton;
    private TextView userEmail;
    private TextView userPassword;
    public Intent i;

    private Thread t = null;
    private ArrayList<String> list = new ArrayList<String>();
    private TextView slogan = null;
    private Button webLogIn;
    private Button admindial;

    private String password;
    private static final String tag = "Usernames: ";

    public boolean flag = false;
    public String email;
    String testingUser;

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

                Uri dialuri = Uri.parse("tel:6039217921");
                Intent dialintent = new Intent(Intent.ACTION_CALL, dialuri);
                startActivity(dialintent); //Has a red line underneath, but runs completely normal
            }
        });



        i = new Intent (this, Category.class);


        webLogIn = (Button) findViewById(R.id.log_in_web);
        webLogIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });


    }
    public void openBrowser() {
        Uri uri = Uri.parse("http://frodo.bentley.edu/CS460Teamc");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.signin:
            {
                emailFieldString = emailField.getText().toString();
                Log.e("Checking",emailFieldString);
                t = new Thread(background);
                t.start();
            }

        }
    }
    private Runnable background = new Runnable()
    {
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
                //String mysql = "select password from cs460teamc.user WHERE email ="+testing +")";
                //Log.e("Hey", mysql);
//                ResultSet result = stmt.executeQuery(mysql);
//                ResultSet result = stmt.executeQuery("select password from cs460teamc.user WHERE email = 'XIE_XIAO@bentley.edu';");

                ResultSet result = stmt.executeQuery("select password from cs460teamc.user WHERE email = '"+emailFieldString.trim()+"';");
                Log.e("LOGGINGIN",emailFieldString);
                email = emailFieldString;
                while (result.next())
                {
                    //Read all usernames into one string

                    password = result.getString("password");
                    Log.e("pass",password);
                }
                char[] ePassword = password.toCharArray();
                ePassword[2] = 'a';
                password = String.valueOf(ePassword);

                if(BCrypt.checkpw(passwordField.getText().toString().trim(), password))
                {
                    Log.e("M", "True");
                    startActivity(i);
                }
                else
                {
                    Log.e("M","False");
                }
                Log.e("JDBC TEST", password);

                //clean up
                //t = null;
            } catch (SQLException e) {
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

