package com.project.hairtologyowner.views.fragments.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.components.repository.Session;
import com.project.hairtologyowner.models.OwnerModel;

import java.util.Objects;

public class LoginViewModel extends ViewModel {

    public interface OnLoginListener {
        void onSuccess(OwnerModel ownerModel);
        void onFailed(String error);
    }

    private FirebaseClient mFirebaseClient;
    private Session mSession;

    public void viewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mSession = new Session(application.getApplicationContext());
    }

    public void login(String email, String password, OnLoginListener listener) {
        mFirebaseClient.getAuth().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                if (user != null) {
                    mFirebaseClient.getDatabaseReference()
                        .child(mFirebaseClient.apiOwnerInfo(user.getUid()))
                        .get()
                        .addOnSuccessListener(dataSnapshot -> {
                            OwnerModel ownerModel = dataSnapshot.getValue(OwnerModel.class);
                            mSession.setCurrentUser(ownerModel);
                            listener.onSuccess(ownerModel);
                        })
                        .addOnFailureListener(e -> listener.onFailed(Objects.requireNonNull(e.getCause()).toString()));
                }
            })
            .addOnFailureListener(e -> listener.onFailed(Objects.requireNonNull(e.getCause()).toString()));
    }

}