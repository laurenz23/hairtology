package com.project.hairtologyowner.models;

import java.util.ArrayList;

public class ShopModel {

    private ShopDetail detail;
    private ArrayList<ShopService> service;

    public ShopModel() {}

    public ShopDetail getShopDetail() {
        return detail;
    }

    public void setShopDetail(ShopDetail shopDetail) {
        this.detail = shopDetail;
    }

    public ArrayList<ShopService> getShopServiceArrayList() {
        return service;
    }

    public void setShopServiceArrayList(ArrayList<ShopService> shopServiceArrayList) {
        this.service = shopServiceArrayList;
    }

}
