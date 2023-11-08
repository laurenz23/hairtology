package com.project.hairtologyowner.views.fragments.profile;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.components.repository.Session;
import com.project.hairtologyowner.models.OwnerModel;

public class ProfileViewModel extends ViewModel {

    public interface onUpdateProfileListener {
        void onSuccess(OwnerModel user);
        void onFailed(Throwable throwable);
    }


    private FirebaseClient mFirebaseClient;
    private Session mSession;

    public void setModel(Context context) {
        mFirebaseClient = new FirebaseClient(context);
        mSession = new Session(context);
    }

    public OwnerModel getCurrentUser() {
        return mSession.getCurrentUser();
    }

    public void updateProfile(String firstName, String lastName, String email, onUpdateProfileListener listener) {
        OwnerModel owner = mSession.getCurrentUser();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);

        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiInfo(owner.getUuid()))
                .setValue(owner)
                .addOnSuccessListener(unused -> {
                    mSession.setCurrentUser(owner);
                    listener.onSuccess(owner);
                }).addOnFailureListener(listener::onFailed);
    }

    public void logout() {
        mSession.endSession();
    }

}
