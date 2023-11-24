package com.project.hairtologyuser.views.fragments.shoplist;

import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.ShopReview;
import com.project.hairtologyuser.models.ShopService;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListViewModel extends ViewModel {

    public interface OnShopDataListener {
        void onSuccess(ArrayList<ShopModel> shopList);
        void onFailed(String error);
    }

    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getShop(OnShopDataListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiShop())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ShopModel> shopArrayList = new ArrayList<>();
                        for (DataSnapshot shopList : snapshot.getChildren()) {
                            ShopModel shopModel = new ShopModel();
                            ArrayList<ShopService> shopServiceArrayList = new ArrayList<>();
                            ArrayList<ShopReview> shopReviewArrayList = new ArrayList<>();
                            for (DataSnapshot shopData : shopList.getChildren()) {
                                if (Objects.equals(shopData.getKey(), "shopDetail")) {
                                    ShopDetail shopDetail = shopData.getValue(ShopDetail.class);
                                    if (shopDetail != null) {
                                        shopModel.setShopDetail(shopDetail);
                                    }
                                }

                                if (Objects.equals(shopData.getKey(), "shopService")) {
                                    for (DataSnapshot serviceList : shopData.getChildren()) {
                                        ShopService shopService = serviceList.getValue(ShopService.class);
                                        if (shopService != null) {
                                            shopServiceArrayList.add(shopService);
                                        }
                                    }
                                }

                                if (Objects.equals(shopData.getKey(), "shopReview")) {
                                    for (DataSnapshot reviewList : shopData.getChildren()) {
                                        ShopReview shopReview = reviewList.getValue(ShopReview.class);
                                        if (shopReview != null) {
                                            shopReviewArrayList.add(shopReview);
                                        }
                                    }
                                }
                            }

                            shopModel.setShopService(shopServiceArrayList);
                            shopModel.setReviews(shopReviewArrayList);
                            shopArrayList.add(shopModel);
                        }

                        listener.onSuccess(shopArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailed(error.getMessage());
                    }
                });
    }

}