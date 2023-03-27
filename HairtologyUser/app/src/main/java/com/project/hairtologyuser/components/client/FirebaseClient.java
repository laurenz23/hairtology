package com.project.hairtologyuser.components.client;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseClient {

    private Context mContext;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase
                        .getInstance()
                        .getReferenceFromUrl("https://hairtology-242e8-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public FirebaseClient(@NonNull Context context) {
        mContext = context;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

}
