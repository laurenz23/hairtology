package com.project.hairtologyowner.models;

public class ShopDetail {

    private String uuid;
    private String name;
    private String description;
    private String address;
    private String country;
    private String hour;
    private String price;
    private String imageId1;
    private String imageId2;
    private String imageId3;

    public ShopDetail() {}

    public ShopDetail(String uuid, String name, String description, String address, String hour, String price, String imageId1, String imageId2, String imageId3) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hour = hour;
        this.price = price;
        this.imageId1 = imageId1;
        this.imageId2 = imageId2;
        this.imageId3 = imageId3;
    }

    public void setUuid(String id) {
        this.uuid = id;
    }

    public String getUuid() {
        return uuid;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getImageId1() {
        return imageId1;
    }

    public void setImageId1(String imageId1) {
        this.imageId1 = imageId1;
    }

    public String getImageId2() {
        return imageId2;
    }

    public void setImageId2(String imageId2) {
        this.imageId2 = imageId2;
    }

    public String getImageId3() {
        return imageId3;
    }

    public void setImageId3(String imageId3) {
        this.imageId3 = imageId3;
    }

}
