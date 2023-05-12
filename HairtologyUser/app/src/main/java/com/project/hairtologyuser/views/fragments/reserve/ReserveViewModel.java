package com.project.hairtologyuser.views.fragments.reserve;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.components.utils.ToastMessage;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ServiceModel;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReserveViewModel extends ViewModel {

    public interface OnServiceDataListener {
        void onSuccess(ArrayList<ServiceModel> serviceList);
        void onFailed(DatabaseError error);
    }

    public interface OnReserveTapListener {
        void onSuccess(ReservationModel reservation);
        void onFailed(Throwable throwable);
        void onFailed(DatabaseError error);
    }

    public interface OnFavoriteTapListener {
        void onSuccess();
        void onFailed(Exception error);
    }

    private FirebaseClient mFirebaseClient;
    private Session mSession;
    private MutableLiveData<UserModel> mCurrentUser;
    private MutableLiveData<ShopModel> mShopLiveData;
    private MutableLiveData<ServiceModel> mServiceLiveData;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mSession = new Session(application.getApplicationContext());
        mCurrentUser = new MutableLiveData<>(mSession.getCurrentUser());
        mShopLiveData = new MutableLiveData<>(null);
        mServiceLiveData = new MutableLiveData<>(null);
    }

    public MutableLiveData<UserModel> getCurrentUser() {
        return mCurrentUser;
    }

    public void setShop(ShopModel shop) {
        mShopLiveData.setValue(shop);
    }

    public ShopModel getShop() {
        return mShopLiveData.getValue();
    }

    public void setService(ServiceModel serviceModel) {
        mServiceLiveData.setValue(serviceModel);
    }

    public ServiceModel getService() {
        return mServiceLiveData.getValue();
    }

    public void service(int shopId, OnServiceDataListener listener) {
        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiService(String.valueOf(shopId)))
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ServiceModel> serviceList = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        serviceList.add(data.getValue(ServiceModel.class));
                    }

                    listener.onSuccess(serviceList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onFailed(error);
                }
            });
    }

    public void reserve(String time, String minute, String meridian, String day, String month, String year, OnReserveTapListener listener) {
        if (getCurrentUser().getValue() == null) {
            return;
        }

        ReservationModel reservation = new ReservationModel(
            getShop().getId(),
            getShop().getName(),
            getService().getDescription(),
            time,
            minute,
            meridian,
            day,
            month,
            year,
            String.valueOf(getService().getPrice())
        );

        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiReservation(getCurrentUser().getValue().getUuid()))
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long childCount = 0;

                    if (snapshot.exists()) {
                        childCount = snapshot.getChildrenCount() + 1;
                    }

                    mFirebaseClient.getDatabaseReference()
                        .child(mFirebaseClient.apiReservation(getCurrentUser().getValue().getUuid()))
                        .child(String.valueOf(childCount))
                        .setValue(reservation)
                        .addOnSuccessListener(data -> {
                            listener.onSuccess(reservation);
                        }).addOnFailureListener(listener::onFailed);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onFailed(error);
                }
            });
    }

    public void favorite(int shopId, OnFavoriteTapListener listener) {
        if (getCurrentUser().getValue() == null) {
            return;
        }

        List<Integer> favoriteList = getCurrentUser().getValue().getFavoriteShopId();

        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }

        favoriteList.add(shopId);
        addFavoriteShop(getCurrentUser().getValue(), favoriteList, listener);
    }

    private void addFavoriteShop(UserModel user, List<Integer> favoriteList, OnFavoriteTapListener listener) {
        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiFavorite(user.getUuid()))
            .setValue(favoriteList)
            .addOnSuccessListener(data -> {
                user.setFavoriteShopId(favoriteList);
                mSession.setCurrentUser(user);
                listener.onSuccess();
            })
            .addOnFailureListener(listener::onFailed);
    }

}