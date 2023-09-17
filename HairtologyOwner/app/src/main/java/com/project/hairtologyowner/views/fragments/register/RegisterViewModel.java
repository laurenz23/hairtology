package com.project.hairtologyowner.views.fragments.register;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.OwnerModel;

public class RegisterViewModel extends ViewModel {

    public interface onRegisterListener {
        void onRegisterSuccess(OwnerModel owner);
        void onRegisterFailed(Throwable throwable);
    }

    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        Context mContext = application.getApplicationContext();
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void register(String firstName, String lastName, String email, String password, onRegisterListener listener) {
        mFirebaseClient.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser owner = mFirebaseClient.getAuth().getCurrentUser();
                    if (owner != null) {
                        OwnerModel ownerModel = new OwnerModel();
                        ownerModel.setUuid(owner.getUid());
                        ownerModel.setFirstName(firstName);
                        ownerModel.setLastName(lastName);
                        ownerModel.setEmail(email);

                        mFirebaseClient.getDatabaseReference()
                                .child(mFirebaseClient.apiOwnerInfo(owner.getUid()))
                                .setValue(ownerModel)
                                .addOnSuccessListener(unused -> {
                                    listener.onRegisterSuccess(ownerModel);
                                }).addOnFailureListener(listener::onRegisterFailed);
                    }
                })
                .addOnFailureListener(listener::onRegisterFailed);
    }
}