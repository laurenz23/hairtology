package com.project.hairtologyuser.views.fragments.usershopreview;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.ShopReview;

public class UserShopReviewViewModel extends ViewModel {

    private Session mSession;
    private FirebaseClient mFirebaseClient;
    private ShopModel mShop;

    public void setModel(Context context) {
        mSession = new Session(context);
        mFirebaseClient = new FirebaseClient(context);
    }

    public void setShop(ShopModel shop) {
        mShop = shop;
    }

    public void submitReview(int stars, String feedback) {
        String email = mSession.getCurrentUser().getEmail();
        String firstName = mSession.getCurrentUser().getFirstName();
        String lastName = mSession.getCurrentUser().getLastName();
        ShopReview shopReview = new ShopReview(stars, feedback, email, firstName, lastName);
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiShopReview(mShop.getShopDetail().getUuid()))
                .setValue(shopReview)
                .addOnSuccessListener(unused -> {
                    Log.e(UserShopReviewViewModel.class.getSimpleName(), "Successfully submitted your review");
                }).addOnFailureListener(e -> {
                    Log.e(UserShopReviewViewModel.class.getSimpleName(), "Encountered an error: " + e.getMessage());
                });
    }

}