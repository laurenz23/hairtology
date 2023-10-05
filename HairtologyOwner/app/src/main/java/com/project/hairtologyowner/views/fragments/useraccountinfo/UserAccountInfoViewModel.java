package com.project.hairtologyowner.views.fragments.useraccountinfo;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.UserModel;

public class UserAccountInfoViewModel extends ViewModel {

    public interface onDisableUserListener {
        void onSuccess(UserModel user);
        void onFailed(Throwable throwable);
    }

    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(Context context) {
        mContext = context;
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void disableUserAccount(UserModel user, Boolean isDisable, onDisableUserListener listener) {
        user.setAccountDisabled(isDisable);
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiInfo(user.getUuid()))
                .setValue(user)
                .addOnSuccessListener(unused -> {
                    listener.onSuccess(user);
                }).addOnFailureListener(listener::onFailed);
    }

}