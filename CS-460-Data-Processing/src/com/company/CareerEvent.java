package com.company;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mark on 3/15/2016.
 */
public class CareerEvent extends Event {



    public CareerEvent(String summary, String location, String date, String start_time, String end_time) {
        super(summary, "Careers", "");
        setLocation(location);
        setDate(date);
        setStart_time(start_time);
        setEnd_time(end_time);
    }


    public void setDate(String date) {
        Date tempDate = new Date();
        if (date.contains("-")) {
            date = date.substring(date.indexOf(",") + 1, date.indexOf("-")).trim();

        } else {
            date = date.substring(date.indexOf(",") + 1).trim();
        }
        try {

            tempDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Instant instant = tempDate.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        setDate(zdt.toLocalDate());

    }



    public void setStart_time(String start_time) {

        start_time = start_time.indexOf("-") > -1 ? start_time.substring(0, start_time.indexOf("-")).trim() : start_time;
        int hour;
        int minute = 0;
        int base = (start_time.contains("p") && (!start_time.contains("12"))) ? 12 : 0;
        if (start_time.contains(":")) {
            hour = Integer.parseInt(start_time.substring(0, start_time.indexOf(":")));
            minute = Integer.parseInt(start_time.substring(start_time.indexOf(":") + 1, start_time.indexOf("m") - 1));
        } else {
            hour = Integer.parseInt(start_time.substring(0, start_time.indexOf("m") - 1));
        }
        super.setStart_time(LocalTime.of(hour + base, minute));


    }


    public void setEnd_time(String end_time) {
        end_time = end_time.substring(end_time.indexOf("-") + 1).trim();
        int hour;
        int minute = 0;
        int base = (end_time.contains("p") && (!end_time.contains("12"))) ? 12 : 0;
        if (end_time.contains(":")) {
            hour = Integer.parseInt(end_time.substring(0, end_time.indexOf(":")));
            minute = Integer.parseInt(end_time.substring(end_time.indexOf(":") + 1, end_time.indexOf("m") - 1));
        } else {
            hour = Integer.parseInt(end_time.substring(0, end_time.indexOf("m") - 1));
        }
        super.setEnd_time(LocalTime.of(hour + base, minute));

    }


}
