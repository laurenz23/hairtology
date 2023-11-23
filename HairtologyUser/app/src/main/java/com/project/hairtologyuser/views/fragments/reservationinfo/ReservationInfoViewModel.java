package com.project.hairtologyuser.views.fragments.reservationinfo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.ShopService;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class ReservationInfoViewModel extends ViewModel {

    public interface onReservationCancellation {
        void onSuccess(int position);
        void onFailed(Exception exception);
    }

    private Context mContext;
    private Session mSession;
    private FirebaseClient mFirebaseClient;
    private ShopModel mShopModel;

    public void setViewModel(@NonNull Application application) {
        mContext = application.getApplicationContext();
        mSession = new Session(mContext);
        mFirebaseClient = new FirebaseClient(application);
    }

    public UserModel getCurrentUser() {
        return mSession.getCurrentUser();
    }

    public ShopModel getShop() {
        return mShopModel;
    }

    public void retrieveShop(String shopId) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiShop() + shopId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ShopModel shopModel = new ShopModel();
                        ArrayList<ShopService> shopServiceArrayList = new ArrayList<>();
                        for (DataSnapshot shopData : snapshot.getChildren()) {
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

                                shopModel.setShopService(shopServiceArrayList);
                            }
                        }

                        mShopModel = shopModel;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(ReservationInfoViewModel.class.getSimpleName(), "Encountered an error while retrieving shop info");
                    }
                });
    }

    public void cancelReservation(int position, ReservationModel reservation, ReservationInfoViewModel.onReservationCancellation listener) {
        UserModel currentUser = mSession.getCurrentUser();
        if (currentUser == null)
            return;

        reservation.setCancelled(true);

        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .child(String.valueOf(position))
                .removeValue()
                .addOnSuccessListener(s -> {
                    mFirebaseClient.getDatabaseReference()
                            .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                            .child(String.valueOf(position))
                            .setValue(reservation)
                            .addOnSuccessListener(success -> {
                                listener.onSuccess(position);
                            }).addOnFailureListener(listener::onFailed);
                }).addOnFailureListener(listener::onFailed);
    }
}