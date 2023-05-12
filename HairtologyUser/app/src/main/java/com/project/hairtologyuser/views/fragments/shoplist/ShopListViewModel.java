package com.project.hairtologyuser.views.fragments.shoplist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ShopModel;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListViewModel extends ViewModel {

    public interface OnShopDataListener {
        void onSuccess(ArrayList<ShopModel> shopList);
        void onFailed(DatabaseError error);
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
                        ArrayList<ShopModel> shopList = new ArrayList<>();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            for (DataSnapshot d : data.getChildren()) {
                                if (Objects.equals(d.getKey(), mFirebaseClient.apiShopDetail())) {
                                    shopList.add(d.getValue(ShopModel.class));
                                }
                            }
                        }

                        listener.onSuccess(shopList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailed(error);
                    }
                });
    }

}