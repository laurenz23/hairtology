package com.project.hairtologyuser.models;

public class ReservationModel {

    private String date;

    private String time;

    private String note;

    public ReservationModel() {}

    public ReservationModel(String date, String time, String note) {
        this.date = date;
        this.time = time;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
