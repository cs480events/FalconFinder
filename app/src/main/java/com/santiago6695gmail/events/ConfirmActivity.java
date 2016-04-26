package com.santiago6695gmail.events;

/**
 * Created by LEONARD_THOM on 4/25/2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfirmActivity extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.finalcheck);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Switcher");
            Log.w("omnomnomnivore", value);

            Toast.makeText(this, value + " has been added successfully to this activity. Huzzah!", Toast.LENGTH_LONG)
                    .show();
        }


    }



}
