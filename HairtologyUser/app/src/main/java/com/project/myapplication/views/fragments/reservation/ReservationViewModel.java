package com.project.myapplication.views.fragments.reservation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.project.myapplication.models.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class ReservationViewModel extends AndroidViewModel {

    private MutableLiveData<List<ShopModel>> mShopList;

    private MutableLiveData<String> mDate;

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        mShopList = new MutableLiveData<>();
        mDate = new MutableLiveData<>();
    }

    public MutableLiveData<List<ShopModel>> getShopList() {
        return mShopList;
    }

    public MutableLiveData<String> getDate() {
        return mDate;
    }

    public void setShop() {
        List<ShopModel> shopList = new ArrayList<>();
        shopList.add(new ShopModel("Shop1"));
        shopList.add(new ShopModel("Shop2"));
        shopList.add(new ShopModel("Shop3"));
        shopList.add(new ShopModel("Shop4"));
        shopList.add(new ShopModel("Shop5"));
        mShopList.setValue(shopList);
    }

}