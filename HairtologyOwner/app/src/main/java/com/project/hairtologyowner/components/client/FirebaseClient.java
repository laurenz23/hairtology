package com.project.hairtologyowner.components.client;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.hairtologyowner.components.app.AppConfig;

public class FirebaseClient {

    private Context mContext;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(AppConfig.Api.BASE_URL);
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public FirebaseClient(@NonNull Context context) {
        mContext = context;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public StorageReference getStorageReference() {
        return storageReference;
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
        return "shops/";
    }

    public String apiShopDetail() {
        return "shopDetail/";
    }
    public String apiShopService() {
        return "shopService/";
    }

    public String apiService(String shopId) {
        return "shops/" + shopId + "/shopService";
    }

    public String apiUsers() {
        return "users/";
    }

    public String apiOwnerInfo(String uuid) {
        return "owners/" + uuid + "/info";
    }

    public String apiMessage() {
        return "messages/";
    }

    public String storageShops() {
        return "shops/";
    }

    public String storageServices() {
        return "shops/services/";
    }

}
