package com.project.hairtologyuser.views.fragments.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyuser.components.client.FirebaseClient;

public class LoginViewModel extends ViewModel {

    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        mContext = application.getApplicationContext();
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void login(String email, String password) {
        mFirebaseClient.getAuth().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                    if (user != null) {
                        Log.e(getClass().getSimpleName(), "Email: " + user.getEmail());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(getClass().getSimpleName(), "Encountered a problem signing your account");
                });
    }

}