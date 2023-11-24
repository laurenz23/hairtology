package com.project.hairtologyuser.views.fragments.reserve;

import android.app.Application;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ReservationStatus;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.ShopReview;
import com.project.hairtologyuser.models.ShopService;
import com.project.hairtologyuser.models.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReserveViewModel extends ViewModel {

    public interface OnServiceDataListener {
        void onSuccess(ArrayList<ShopService> serviceList);
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
    private MutableLiveData<ShopDetail> mShopLiveData;
    private MutableLiveData<ShopService> mServiceLiveData;
    private ArrayList<ShopReview> mShopReviewArrayList = new ArrayList<>();

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

    public void setShop(ShopDetail shopDetail) {
        mShopLiveData.setValue(shopDetail);
    }

    public ShopDetail getShop() {
        return mShopLiveData.getValue();
    }

    public void setService(ShopService serviceModel) {
        mServiceLiveData.setValue(serviceModel);
    }

    public ShopService getService() {
        return mServiceLiveData.getValue();
    }

    public void setReviewArrayList(ArrayList<ShopReview> reviewArrayList) {
        mShopReviewArrayList = reviewArrayList;
    }

    public ArrayList<ShopReview> getReviewArrayList() {
        return mShopReviewArrayList;
    }

    public double getRating() {
        int star1Respondent = 0;
        int star2Respondent = 0;
        int star3Respondent = 0;
        int star4Respondent = 0;
        int star5Respondent = 0;
        int totalRespondent;
        int totalScore;
        double rating = 0.0;

        if (getReviewArrayList() != null) {
            for (ShopReview review : mShopReviewArrayList) {
                if (review.getStars() >= 5) { star5Respondent++; }
                else if (review.getStars() == 4) { star4Respondent++; }
                else if (review.getStars() == 3) { star3Respondent++; }
                else if (review.getStars() == 2) { star2Respondent++; }
                else if (review.getStars() == 1) { star1Respondent++; }
            }

            totalRespondent = star5Respondent + star4Respondent + star3Respondent + star2Respondent + star1Respondent;
            totalScore = (star5Respondent * 5) + (star4Respondent * 4) + (star3Respondent * 3) + (star2Respondent * 2) + star1Respondent;
            rating = (double) totalScore / totalRespondent;
            Log.e(ReserveViewModel.class.getSimpleName(), "Total Respondent: " + totalRespondent);
            Log.e(ReserveViewModel.class.getSimpleName(), "Total Score: " + totalScore);
            Log.e(ReserveViewModel.class.getSimpleName(), "Rating: " + rating);
        }

        return rating;
    }

    public void service(String shopUuid, OnServiceDataListener listener) {
        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiService(String.valueOf(shopUuid)))
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShopService> serviceList = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        serviceList.add(data.getValue(ShopService.class));
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
            UUID.randomUUID().toString(),
            getShop().getUuid(),
            getShop().getName(),
            getService().getName(),
            getService().getDescription(),
            time,
            minute,
            meridian,
            day,
            month,
            year,
            String.valueOf(getService().getPrice()),
            ReservationStatus.ON_GOING
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

    public void favorite(String shopUuId, OnFavoriteTapListener listener) {
        if (getCurrentUser().getValue() == null) {
            return;
        }

        List<String> favoriteList = getCurrentUser().getValue().getFavoriteShopId();

        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }

        favoriteList.add(shopUuId);
        addFavoriteShop(getCurrentUser().getValue(), favoriteList, listener);
    }

    private void addFavoriteShop(UserModel user, List<String> favoriteList, OnFavoriteTapListener listener) {
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