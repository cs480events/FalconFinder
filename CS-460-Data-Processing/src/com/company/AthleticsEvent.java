package com.company;

/**
 * Created by Mark on 3/14/2016.
 * CS-460-Web
 */
public class AthleticsEvent extends Event {

    /**
     * Constructor
     *
     * @param summary     - summary of the event
     * @param description - description of the event
     * @param time        - time of the event
     */
    public AthleticsEvent(String summary, String description, String time) {
        super(summary, "Athletics", description);
        setStart_time(parseTime(time));
        setDate(parseDate(time));
    }



}
