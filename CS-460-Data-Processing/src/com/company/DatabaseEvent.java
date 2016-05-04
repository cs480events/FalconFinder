package com.company;

/**
 * Created by XIE_XIAO on 3/23/2016.
 */
public class DatabaseEvent {
    public String Summary;
    public String Category;
    public String Description;
    public String Date;
    public String Start_Time;
    public String End_Time;
    public String Location;

    @Override
    public String toString() {
        return "DatabaseEvent{" +
                "Summary='" + Summary + '\'' +
                ", Category='" + Category + '\'' +
                ", Description='" + Description + '\'' +
                ", Date='" + Date + '\'' +
                ", Start_Time='" + Start_Time + '\'' +
                ", End_Time='" + End_Time + '\'' +
                ", Location='" + Location + '\'' +
                '}';
    }

    public DatabaseEvent(String summary, String category, String description, String date, String start_Time, String end_Time, String location) {
        setSummary(summary);
        setCategory(category);
        setDescription(description);
        setDate(date);
        setStart_Time(start_Time);
        setEnd_Time(end_Time);
        setLocation(location);
    }



    public void setSummary(String summary) {
        Summary = summary;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public void setLocation(String location) {
        Location = location;
    }


    public String getSummary() {
        return Summary;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getDate() {
        return Date;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public String getLocation() {
        return Location;
    }
}
