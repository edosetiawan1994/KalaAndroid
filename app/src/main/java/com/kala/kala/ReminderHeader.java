package com.kala.kala;

/**
 * Created by Edo on 2/12/2016.
 */
public class ReminderHeader extends Reminder implements list {

    private final String day;
    private final String date;

    public ReminderHeader(String day, String date) {
        super();
        this.day = day;
        this.date = date;
    }

    public String getDay(){
        return day;
    }

    public String getDate(){
        return date;
    }

    public boolean isHeader() {
        return true;
    }
}
