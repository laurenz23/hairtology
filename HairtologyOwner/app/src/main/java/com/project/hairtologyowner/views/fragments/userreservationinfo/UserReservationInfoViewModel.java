package com.project.hairtologyowner.views.fragments.userreservationinfo;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.UserModel;

public class UserReservationInfoViewModel extends ViewModel {

    public interface OnViewUserInfoListener {
        void onSuccess(UserModel user);
        void onFailed(String errorMessage);
    }

    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(Context context) {
        mContext = context;
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void viewUserInfo(String userUuid, OnViewUserInfoListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiInfo(userUuid))
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    listener.onSuccess(userModel);
                })
                .addOnFailureListener(error -> {
                    listener.onFailed(error.getMessage());
                });
    }

}