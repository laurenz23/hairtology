package com.project.hairtologyuser.views.fragments.registration;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.client.RestServiceClient;
import com.project.hairtologyuser.models.UserModel;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationViewModel extends ViewModel {

    public interface onRegisterListener {
        void onRegisterSuccess(UserModel user);
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
                    FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                    if (user != null) {
                        UserModel userModel = new UserModel();
                        userModel.setUuid(user.getUid());
                        userModel.setFirstName(firstName);
                        userModel.setLastName(lastName);
                        userModel.setEmail(email);
                        userModel.setAccountDisabled(false);

                        mFirebaseClient.getDatabaseReference()
                                .child(mFirebaseClient.apiInfo(user.getUid()))
                                .setValue(userModel)
                                .addOnSuccessListener(unused -> {
                                    listener.onRegisterSuccess(userModel);
                                }).addOnFailureListener(listener::onRegisterFailed);
                    }
                })
                .addOnFailureListener(listener::onRegisterFailed);
    }

}