package com.project.hairtologyuser.models;

public class ReservationModel {

    private ServiceType serviceType;
    private String time;
    private String day;
    private String month;
    private String note;

    public ReservationModel() {}

    public ReservationModel(ServiceType type, String time, String day, String month, String note) {
        this.serviceType = type;
        this.time = time;
        this.day = day;
        this.month = month;
        this.note = note;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
