package com.project.hairtologyowner.components.client;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.hairtologyowner.components.app.AppConfig;

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

    public String apiFavorite(String uuid) {
        return "users/" + uuid + "/info/favoriteShopId";
    }

    public String apiShop() {
        return "shops";
    }

    public String apiShopDetail() {
        return "detail";
    }
    public String apiShopService() {
        return "service";
    }

    public String apiService(String shopId) {
        return "shops/" + shopId + "/service";
    }

    public String apiUsers() {
        return "users";
    }

    public String apiOwnerInfo(String uuid) {
        return "owners/" + uuid + "/info";
    }

    public String apiMessage() {
        return "messages";
    }

}
