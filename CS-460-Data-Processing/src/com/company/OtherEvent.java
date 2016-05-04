package com.company;

/**
 * Created by XIE_XIAO on 3/20/2016.
 */
public class OtherEvent extends Event {
    /**
     * Constructor
     * @param summary
     * @param category
     * @param description
     * @param time
     */
    public OtherEvent(String summary,String description,String category,String time,String location){
        super(summary, category, description);
        setStart_time(parseTime(time));
        setDate(parseDate(time));
        setLocation(location);


    }

}

