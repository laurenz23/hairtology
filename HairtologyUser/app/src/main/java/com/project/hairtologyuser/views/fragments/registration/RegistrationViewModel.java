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
import com.project.hairtologyuser.models.UserModel;

import java.util.concurrent.Executor;

public class RegistrationViewModel extends ViewModel {

    public interface onRegisterListener {
        void onRegisterSuccess();
        void onRegisterFailed();
    }

    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        mContext = application.getApplicationContext();
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void register(String firstName, String lastName, String email, String password, onRegisterListener listener) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);

        mFirebaseClient.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mFirebaseClient.getAuth().getCurrentUser();
                    if (user != null) {
                        mFirebaseClient.getDatabaseReference()
                                .child("users")
                                .child(user.getUid())
                                .child("info")
                                .setValue(userModel)
                                .addOnSuccessListener(unused -> {
                                    listener.onRegisterSuccess();
                                }).addOnFailureListener(e -> {
                                    listener.onRegisterFailed();
                                });
                    } else {
                        listener.onRegisterFailed();
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onRegisterFailed();
                });
    }

}