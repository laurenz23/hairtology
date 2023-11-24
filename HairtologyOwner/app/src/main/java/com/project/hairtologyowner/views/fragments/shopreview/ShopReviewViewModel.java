package com.project.hairtologyowner.views.fragments.shopreview;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;

public class ShopReviewViewModel extends ViewModel {

    private FirebaseClient mFirebaseClient;

    public void setModel(Context context) {
        mFirebaseClient = new FirebaseClient(context);
    }

}