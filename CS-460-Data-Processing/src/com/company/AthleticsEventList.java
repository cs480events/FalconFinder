package com.company;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Mark on 3/14/2016.
 * CS-460-Web
 */
public class AthleticsEventList extends EventList {


    /**
     * Constructor
     */
    private static final AthleticsEventList instance = new AthleticsEventList();

    /**
     * Constructor
     */
    private AthleticsEventList() {
        super.list = new ArrayList<>();
    }

    public static AthleticsEventList getInstance() {
        return instance;
    }

    /**
     * This method returns a list of events
     *
     * @return
     */
    public ArrayList<Event> getList() {
        String time = "";
        String summary = "";
        String description = "";


        try {
            FileInputStream fin = new FileInputStream("composite.ics");
            CalendarBuilder builder = new CalendarBuilder();

            Calendar calendar = builder.build(fin);
            for (Component c : calendar.getComponents()) {
                for (Property property : c.getProperties()) {

                    switch (property.getName()) {
                        case "DTSTART":
                            time = property.getValue();
                            break;
                        case "SUMMARY":
                            summary = property.getValue();
                            break;
                        case "DESCRIPTION":
                            description = property.getValue();
                            break;
                    }
                }
                list.add(new AthleticsEvent(summary, description, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
