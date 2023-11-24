package com.project.hairtologyowner.models;

import java.util.ArrayList;

public class ShopModel {

    private ShopDetail detail;
    private ArrayList<ShopService> service;
    private ArrayList<ShopReview> review;

    public ShopModel() {}

    public ShopDetail getShopDetail() {
        return detail;
    }

    public void setShopDetail(ShopDetail shopDetail) {
        this.detail = shopDetail;
    }

    public ArrayList<ShopService> getShopService() {
        return service;
    }

    public void setShopService(ArrayList<ShopService> shopServiceArrayList) {
        this.service = shopServiceArrayList;
    }

    public ArrayList<ShopReview> getReview() {
        return review;
    }

    public void setReviews(ArrayList<ShopReview> reviewArrayList) {
        this.review = reviewArrayList;
    }

}
