package com.santiago6695gmail.events;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by TORRES_SANT on 4/27/2016.
 */
public class Category extends Activity implements View.OnClickListener {

    private Button allbutton;
    private Button athleticbutton;
    private Button careerbutton;
    private Button otherbutton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        //hide title and icon in action bar
        ActionBar actionBar = getActionBar();
        actionBar.show();
        //add onclick to buttons
        allbutton = (Button) findViewById(R.id.all);
        allbutton.setOnClickListener(this);

        athleticbutton = (Button) findViewById(R.id.Sports);
        athleticbutton.setOnClickListener(this);

        careerbutton = (Button) findViewById(R.id.Career_Service);
        careerbutton.setOnClickListener(this);

        otherbutton = (Button) findViewById(R.id.Other);
        otherbutton.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.all: {
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category","All");
                startActivity(i);
            }
            case R.id.Sports: {
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category","Sports");
                startActivity(i);
            }
            case R.id.Career_Service:{
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category","Career");
                startActivity(i);
            }
            case R.id.Other:{
                Intent i = new Intent(this,EventList.class);
                i.putExtra("Category","Other");
                startActivity(i);
            }
        }
    }

}
