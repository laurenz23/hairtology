package com.project.hairtologyowner.models;

import java.util.ArrayList;

public class ShopModel {

    private ShopInfo shopInfo;
    private ArrayList<ShopService> shopServiceArrayList;

    public ShopModel() {}

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public ArrayList<ShopService> getShopServiceArrayList() {
        return shopServiceArrayList;
    }

    public void setShopServiceArrayList(ArrayList<ShopService> shopServiceArrayList) {
        this.shopServiceArrayList = shopServiceArrayList;
    }

}
