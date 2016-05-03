package com.company;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

/**
 * Created by Mark on 3/14/2016.
 */
public class CalenderUpdate {
    public static final String Athletics = "http://bentleyfalcons.com/composite?print=ical";
    public static final String All = "http://events.bentley.edu/calendar.ics";

    public static void update(String calendarType,String fileName){
        try {
            URL url = new URL(calendarType);
            File destination = new File(fileName);

            FileUtils.copyURLToFile(url, destination);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
