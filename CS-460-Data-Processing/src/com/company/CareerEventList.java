package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Mark on 3/15/2016.
 */
public class CareerEventList extends EventList {

    private static final CareerEventList instance = new CareerEventList();

    private CareerEventList() {
        super.list = new ArrayList<>();
    }

    public static CareerEventList getInstance() {
        return instance;
    }


    public ArrayList<Event> getList() {
        String summary = "";
        String location = "";
        String date = "";
        String time = "";

        Document doc = null;
        int index = 1;
        do {

            try {


                doc = Jsoup.connect("http://careeredge.bentley.edu/events/page/" + index + "/").get();
                index++;
                Elements elements = doc.select("ul.events-list li.event_item").select("div.title ,p span.event-date ,p span.event-time ,p span.location");

                int count = 0;
                for (Element e : elements) {

                    switch (e.className()) {
                        case "title":
                            summary = e.ownText();
                            count++;
                            break;
                        case "event-date":
                            date = e.ownText();
                            count++;
                            break;
                        case "event-time":
                            time = e.ownText();
                            count++;
                            break;

                        case "location":
                            location = e.ownText();
                            count++;
                            break;
                    }
                    if (count == 3) {
                        list.add(new CareerEvent(summary, location, date, time, time));
                        count = 0;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        } while (!(doc.toString().contains(("There are no events."))));
        return list;
    }
}
