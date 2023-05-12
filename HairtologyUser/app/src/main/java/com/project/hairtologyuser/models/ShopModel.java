package com.project.hairtologyuser.models;

public class ShopModel {

    private int id;
    private String name;
    private String description;
    private String address;
    private String hour;
    private String price;

    public ShopModel() {}

    public ShopModel(int id, String name, String description, String address, String hour, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hour = hour;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
