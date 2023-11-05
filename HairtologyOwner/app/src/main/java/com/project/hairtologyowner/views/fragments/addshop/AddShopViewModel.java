package com.project.hairtologyowner.views.fragments.addshop;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopDetail;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;

import java.util.ArrayList;

public class AddShopViewModel extends ViewModel {

    public interface OnAddShopListener {
        void onSuccess(ShopModel shopModel);
        void onError(String errorMessage);
    }


    public ShopDetail detail ;
    public ArrayList<ShopService> service;
    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Context context) {
        mContext = context;
        mFirebaseClient = new FirebaseClient(mContext);
        this.detail = new ShopDetail();
        this.service = new ArrayList<>();
    }

    public void addShop(OnAddShopListener listener) {
        ShopModel shopModel = new ShopModel();
        shopModel.setShopDetail(detail);
        shopModel.setShopServiceArrayList(service);

        mFirebaseClient.getDatabaseReference().child(mFirebaseClient.apiShop() + shopModel.getShopDetail().getUuid())
                .setValue(shopModel).addOnSuccessListener(unused -> {
                    listener.onSuccess(shopModel);
                }).addOnFailureListener(e -> {
                    listener.onError(e.getMessage());
                });
    }

    public void uploadImage(Uri imageUri, String shopUuId, String imageUuid) {
        mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopUuId + "/" + imageUuid)
                .putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(mContext, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(mContext, "Encountered an error while uploading an image", Toast.LENGTH_LONG).show();
                });
    }

}