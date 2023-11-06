package com.project.hairtologyuser.views.fragments.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.UserModel;

public class LoginViewModel extends ViewModel {

    public interface onLoginListener {
        void onLoginSuccess(UserModel user);
        void onLoginFailed(String errorMessage);
    }

    private Context mContext;
    private FirebaseClient mFirebaseClient;
    private Session mSession;

    public void setViewModel(@NonNull Application application) {
        mContext = application.getApplicationContext();
        mFirebaseClient = new FirebaseClient(mContext);
        mSession = new Session(mContext);
    }

    public void login(String email, String password, onLoginListener listener) {
        mFirebaseClient.getAuth().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                    if (user.isEmailVerified()) {
                        mFirebaseClient.getDatabaseReference()
                                .child(mFirebaseClient.apiInfo(user.getUid()))
                                .get()
                                .addOnSuccessListener(dataSnapshot -> {
                                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                    mSession.setCurrentUser(userModel);
                                    listener.onLoginSuccess(userModel);
                                })
                                .addOnFailureListener(error -> {
                                    listener.onLoginFailed(error.getMessage());
                                });
                    } else {
                        user.sendEmailVerification().addOnSuccessListener(unused1 -> {
                            listener.onLoginFailed("Please verify your email");
                        });
                    }
                })
                .addOnFailureListener(error -> {
                    listener.onLoginFailed(error.getMessage());
                });
    }

}