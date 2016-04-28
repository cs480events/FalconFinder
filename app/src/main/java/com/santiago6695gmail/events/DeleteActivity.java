package com.santiago6695gmail.events;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;


public class DeleteActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private Button yesbutton; //Button for if user clicks yes
    private Button nobutton; //Button for if user clicks no
    private String value; //String to hold initial String passed via intent
    private String grabbedname ="blank"; //Grabbing only the name from the string
    private String PRIMARYKEY; //Grabbing only the PK from the string
    private String useremail = "'XIE_XIAO@bentley.edu'"; //Email/login of the current user
    private static final String tag = "Speaking";
    private TextView tview; //Text view widget
    private Thread t = null; //Background thread for running JDBC
    private Toast tust; //Toast to let user know event was successfully added
    private Intent movebackyes; //Moves user back to EventList if yes is clicked
    private Intent movebackno; //Moves user back to EventList if no is clicked
    private Intent notifclicked; //Moves user to eventlist for new event
    private TextToSpeech speaker; // speaker for speaking event added
    private NotificationManager notificationManager; // for notifications
    private NotificationCompat.Builder notif;


    Handler infohandler = new Handler() { //Method which handles the messages sent
        public void handleMessage(Message msg) {
            if (msg.obj.equals("IsReallyDone")){ //If message is successfully received...

                tust.show(); //Show user the success toast
                startActivity(movebackyes); //Move back to the user event list
               // speak(grabbedname + "deleted!");
                notificationManager.notify(0, notif.build());

            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.deletecheck);

        //set up menu
        ActionBar actionBar = getActionBar();
        actionBar.show();

        Bundle extras = getIntent().getExtras(); //Grabbing from the UserEvent intent
        value = extras.getString("The Deleta");

        //Format
        int locationofend = value.indexOf(":::"); //Using my ::: as a reference to determine the end of the name
        grabbedname = value.substring(0, locationofend); //Use the start of the String and the locationofend int to pull out the name
        PRIMARYKEY = value.substring(locationofend + 3, value.length()); //Use locationofend int and length of String to pull the PK
        tust = Toast.makeText(this, grabbedname + " deleted!", Toast.LENGTH_LONG); //Setting up the toast for later use


        //notification
        //to do - take you to personal instead of all the events
        notifclicked = new Intent(this, UserEvents.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), notifclicked, 0);
        notif  = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Event deleted")
                .setContentText(grabbedname)
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        //set up notification aspect
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Initializing and setting text to the text view
        tview = (TextView) findViewById(R.id.status);
        tview.setText("Delete " + grabbedname + " from your events?");

        //Initializing yes button
        yesbutton = (Button) findViewById(R.id.yesbutton);
        yesbutton.setOnClickListener(this);

        //Initializing no button
        nobutton = (Button) findViewById(R.id.nobutton);
        nobutton.setOnClickListener(this);

        //Setting t to the background runnable, to be used when we are ready
        t = new Thread(background);

        //Initialize Text to Speech engine (context, listener object)
        speaker = new TextToSpeech(this, this);

    }


    // set up menu
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

    // exit the app
    public void exit(MenuItem item) {
        System.exit(0);
    }

  /*  @TargetApi(21)
    public void speak(String output){
        if(output.equals(null))
        {

        }
        else
            speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
    } */

    //For text to speech
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // If a language is not be available, the result will indicate it.
            int result = speaker.setLanguage(Locale.US);

            //  int result = speaker.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                Log.e(tag, "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized
                Log.i(tag, "TTS Initialization successful.");
            }
        } else {
            // Initialization failed.
            Log.e(tag, "Could not initialize TextToSpeech.");
        }
    }



    public void onClick(View v) { //Onclick listener
        switch (v.getId()) {

            case R.id.yesbutton: //for yes

                movebackyes = new Intent(this, UserEvents.class); //Intent set up
                t.start(); //Start the background thread
                yesbutton.setVisibility(View.GONE); //Hide the buttons so user can't crash the program
                nobutton.setVisibility(View.GONE);

                break;

            case R.id.nobutton:
                movebackno = new Intent(this, UserEvents.class); //intent set up AND fired off
                startActivity(movebackno);
                break;
        }
    }

    Runnable background = new Runnable() {
        @Override
        public void run() { //Background thread for JDBC


            String URL = "jdbc:mysql://frodo.bentley.edu:3306/CS460Teamc";

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
                // execute SQL commands to delete from the user's event list

                stmt.executeUpdate("delete from user_event where email = " + useremail + " AND EVENT_ID = " + PRIMARYKEY + ";");

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
