package com.project.hairtologyowner.views.fragments.addshop;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;

import java.util.UUID;

public class AddShopViewModel extends ViewModel {

    private Context mContext;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Context context) {
        mContext = context;
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void uploadImage(Uri imageUri) {
        mFirebaseClient.getStorageReference().child("images/" + UUID.randomUUID().toString())
                .putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(mContext, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(mContext, "Encountered an error while uploading an image", Toast.LENGTH_LONG).show();
                });
    }

}