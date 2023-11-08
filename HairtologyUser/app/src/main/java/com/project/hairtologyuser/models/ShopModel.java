package com.project.hairtologyuser.models;

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

    public ArrayList<ShopService> getShopService() {
        return service;
    }

    public void setShopService(ArrayList<ShopService> shopServiceArrayList) {
        this.service = shopServiceArrayList;
    }

}
