package com.project.hairtologyowner.models;

public class ReservationModel {

    private String uuid;
    private String shopId;
    private String shopName;
    private String serviceName;
    private String serviceDetail;
    private String time;
    private String minute;
    private String meridian;
    private String day;
    private String month;
    private String year;
    private String price;
    private ReservationStatus status;

    public ReservationModel() {}

    public ReservationModel(String uuid, String shopId, String shopName, String serviceName, String serviceDetail, String time, String minute, String meridian, String day, String month, String year, String price, ReservationStatus status) {
        this.uuid = uuid;
        this.shopId = shopId;
        this.shopName = shopName;
        this.serviceName = serviceName;
        this.serviceDetail = serviceDetail;
        this.time = time;
        this.minute = minute;
        this.meridian = meridian;
        this.day = day;
        this.month = month;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
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

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

}