package com.example.studicated;

public class Reminder {
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
}
