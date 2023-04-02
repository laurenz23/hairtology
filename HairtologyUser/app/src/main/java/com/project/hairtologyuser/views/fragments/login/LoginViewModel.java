package com.project.hairtologyuser.views.fragments.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.client.RestServiceClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public interface onLoginListener {
        void onLoginSuccess(UserModel user);
        void onLoginFailed(Throwable throwable);
    }

    private FirebaseClient mFirebaseClient;
    private Session mSession;

    public void setViewModel(@NonNull Application application) {
        Context mContext = application.getApplicationContext();
        mFirebaseClient = new FirebaseClient(mContext);
        mSession = new Session(mContext);
    }

    public void login(String email, String password, onLoginListener listener) {
        mFirebaseClient.getAuth().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                    if (user != null) {
                        mFirebaseClient.getDatabaseReference()
                            .child(mFirebaseClient.apiInfo(user.getUid()))
                            .get()
                            .addOnSuccessListener(dataSnapshot -> {
                                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                mSession.setCurrentUser(userModel.getUuid());
                                listener.onLoginSuccess(userModel);
                            })
                            .addOnFailureListener(listener::onLoginFailed);
                    }
                })
                .addOnFailureListener(listener::onLoginFailed);
    }

}