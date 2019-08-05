package com.example.studicated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Reminder implements Comparable {
    private String title;
    private String hour;
    private String date;
    private String text;

    public Reminder(String title, String hour, String date, String text) {
        this.title = title;
        this.hour = hour;
        this.date = date;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "title='" + title + '\'' +
                ", hour='" + hour + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String mToString() {
        return title + ";" + hour + ";" + date + ";" + text + "\n";
    }

    @Override
    public int compareTo(Object o) {
        DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        Reminder o2 = (Reminder) o;
        try {
            return f.parse(this.getDate()).compareTo(f.parse(o2.getDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
