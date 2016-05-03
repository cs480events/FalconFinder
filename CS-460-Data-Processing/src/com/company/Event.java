package com.company;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by XIE_XIAO on 3/16/2016.
 */
abstract public class Event {
    private String summary;
    private String location;
    private String category;
    private String description;
    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;

    Event(String summary, String category, String description) {
        setSummary(summary);
        setCategory(category);
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getEnd_time() {return end_time;}

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    // Set the start time
    public void setStart_time(LocalTime start_time) {this.start_time = start_time;}

    public LocalDate getDate() {
        return date;
    }

    // Set the date
    public void setDate(LocalDate date) {this.date = date;}

    //parse time with ics format to LocalTime
    public LocalTime parseTime(String time) {
        int posT = time.indexOf("T");

        if (posT > -1) {
            int hour = Integer.parseInt(time.substring(posT + 1, posT + 3));
            int minute = Integer.parseInt(time.substring(posT + 3, posT + 5));
            return LocalTime.of(hour, minute);
        } else {
            return LocalTime.of(0, 0);
        }
    }
    //parse date with ics format to LocalDate
    public LocalDate parseDate(String time) {
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(4, 6));
        int day = Integer.parseInt(time.substring(6, 8));
        return LocalDate.of(year, month, day);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Summary: ")
                .append(this.getSummary())
                .append("\nCategory: ")
                .append(this.getCategory())
                .append("\nDescription: ")
                .append(this.getDescription())
                .append("\nDate: ")
                .append(this.getDate())
                .append("\nStart Time: ")
                .append(this.getStart_time())
                .append("\nEnd Time: ")
                .append(this.getEnd_time())
                .append("\nLocation:")
                .append(this.getLocation());
        return builder.toString();
    }

    public String createSQL() {
        String[] sql = new String[9];
        sql[0] = "eventlist";
        sql[1] = null;
        sql[2] = this.getSummary();
        sql[3] = this.getCategory();
        sql[4] = this.getDescription();
        sql[5] = this.getDate().toString();
        sql[6] = this.getStart_time().toString();
        sql[7] = this.getEnd_time() != null ? this.getEnd_time().toString() : null;
        sql[8] = this.getLocation() != null ? this.getLocation().toString() : null;
        return SQLHelper.createSQL(sql);
    }
}
