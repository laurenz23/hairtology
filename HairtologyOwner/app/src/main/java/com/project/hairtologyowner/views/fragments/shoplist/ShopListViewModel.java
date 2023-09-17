package com.project.hairtologyowner.views.fragments.shoplist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.views.fragments.useraccountlist.UserAccountListViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListViewModel extends ViewModel {

    public interface OnShopDataListener {
        void onSuccess(ArrayList<ShopModel> shop);
        void onFailed(String errorMessage);
    }

    private FirebaseClient mFirebaseClient;

    public void viewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getShopList(ShopListViewModel.OnShopDataListener listener) {
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
                        listener.onFailed(error.getMessage());
                    }
                });
    }

}