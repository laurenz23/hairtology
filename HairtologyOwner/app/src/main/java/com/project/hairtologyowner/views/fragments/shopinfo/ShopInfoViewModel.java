package com.project.hairtologyowner.views.fragments.shopinfo;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.models.ShopModel;

public class ShopInfoViewModel extends ViewModel {

    private ShopModel mShop;

    public void viewModel(ShopModel shop) {
        mShop = shop;
    }

    public ShopModel getShop() {
        return mShop;
    }

}