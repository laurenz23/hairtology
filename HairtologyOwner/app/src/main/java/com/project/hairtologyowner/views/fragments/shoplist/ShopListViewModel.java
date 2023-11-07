package com.project.hairtologyowner.views.fragments.shoplist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopDetail;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListViewModel extends ViewModel {

    public interface OnShopListDataListener {
        void onSuccess(ArrayList<ShopModel> shop);
        void onFailed(String errorMessage);
    }

    private FirebaseClient mFirebaseClient;

    public void viewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getShopList(OnShopListDataListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiShop())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ShopModel> shopArrayList = new ArrayList<>();
                        for (DataSnapshot shopList : snapshot.getChildren()) {
                            ShopModel shopModel = new ShopModel();
                            ArrayList<ShopService> shopServiceArrayList = new ArrayList<>();
                            for (DataSnapshot shopData : shopList.getChildren()) {
                                if (Objects.equals(shopData.getKey(), "shopDetail")) {
                                    Log.e("Service", shopData.toString());
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
                            }

                            shopModel.setShopService(shopServiceArrayList);
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