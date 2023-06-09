package com.project.hairtologyuser.components.client;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.hairtologyuser.components.app.AppConfig;

public class FirebaseClient {

    private Context mContext;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(AppConfig.Api.BASE_URL);

    public FirebaseClient(@NonNull Context context) {
        mContext = context;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public String apiInfo(String uuid) {
        return "users/" + uuid + "/info";
    }

    public String apiReservation(String uuid) {
        return "users/" + uuid + "/reservation";
    }

}
