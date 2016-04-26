package com.santiago6695gmail.events;

/**
 * Created by LEONARD_THOM on 4/25/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.util.ArrayList;

public class ConfirmActivity extends Activity implements OnClickListener {

    private Button yesbutton;
    private Button nobutton;
    private String value;
    private TextView tview;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.finalcheck);

        Bundle extras = getIntent().getExtras();
        value = extras.getString("Switcher");
        Log.w("ABCD", value);

        tview = (TextView) findViewById(R.id.status);
        tview.setText("Add " + value + " to your events?");


        yesbutton = (Button) findViewById(R.id.yesbutton);
        yesbutton.setOnClickListener(this);

        nobutton = (Button) findViewById(R.id.nobutton);
        nobutton.setOnClickListener(this);
        }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.yesbutton:
                Toast tust = Toast.makeText(this, value + " added!", Toast.LENGTH_LONG);
                tust.show();
                break;

            case R.id.nobutton:
                Intent moveback = new Intent(this, EventList.class);
                startActivity(moveback);
                break;
        }
    }




}



