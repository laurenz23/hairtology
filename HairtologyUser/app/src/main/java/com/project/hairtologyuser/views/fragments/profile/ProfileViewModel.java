package com.project.hairtologyuser.views.fragments.profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.UserModel;

public class ProfileViewModel extends ViewModel {

    public interface onUpdateProfileListener {
        void onSuccess(UserModel user);
        void onFailed(Throwable throwable);
    }


    private FirebaseClient mFirebaseClient;
    private Session mSession;

    public void setModel(Context context) {
        mFirebaseClient = new FirebaseClient(context);
        mSession = new Session(context);
    }

    public UserModel getCurrentUser() {
        return mSession.getCurrentUser();
    }

    public void updateProfile(String firstName, String lastName, String email, onUpdateProfileListener listener) {
        UserModel user = mSession.getCurrentUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiInfo(user.getUuid()))
                .setValue(user)
                .addOnSuccessListener(unused -> {
                    mSession.setCurrentUser(user);
                    listener.onSuccess(user);
                }).addOnFailureListener(listener::onFailed);
    }

    public void logout() {
        mSession.endSession();
    }

}