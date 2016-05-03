package com.company;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by XIE_XIAO on 3/20/2016.
 */
public class OtherEventList extends EventList {
    /**
     * Constructor
     */
    private static final OtherEventList instance = new OtherEventList();
    /**
     * Constructor
     */
    private OtherEventList() {
        super.list = new ArrayList<>();
    }
    public static OtherEventList getInstance() {
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
        String category = "";
        String description = "";
        String location = "";

        try {
            FileInputStream fin = new FileInputStream("all.ics");
            CalendarBuilder builder = new CalendarBuilder();

            Calendar calendar = builder.build(fin);
            for (Component c : calendar.getComponents()) {
                for (Property property : c.getProperties()) {
                    switch (property.getName()) {
                        case "DTSTART":
                            time = property.getValue().trim();
                            break;
                        case "SUMMARY":
                            summary = property.getValue().trim();
                            break;
                        case "DESCRIPTION":
//                            System.out.println("Description:" +property.getName()+"||"+property.getValue());
                            description = property.getValue().trim();
                            break;
                        case "CATEGORIES":
//                            System.out.println("Categories:" +property.getName()+"||"+property.getValue());
                            category = property.getValue().trim();
                            break;
                        case "LOCATION":
                            location = property.getValue().trim();
                            break;
                    }
                }


                list.add(new OtherEvent(summary,description,category,time,location));
                summary = "";
                description = "";
                category = "";
                time = "";
                location = "";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
