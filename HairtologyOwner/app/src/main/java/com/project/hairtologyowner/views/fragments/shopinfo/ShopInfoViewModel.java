package com.project.hairtologyowner.views.fragments.shopinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageMetadata;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopModel;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ShopInfoViewModel extends ViewModel {

    private Context mContext;
    private FirebaseClient mFirebaseClient;
    private ShopModel mShop;

    public void setViewModel(Context context) {
        mContext = context;
        mFirebaseClient = new FirebaseClient(mContext);
    }

    public void setShop(ShopModel shop) {
        mShop = shop;
    }

    public void retrieveImage() {
        mFirebaseClient.getStorageReference().getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                storageMetadata.getPath();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

    public void retrieveImage(ImageView imageView, String shopId, String imageId) {
        mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopId + "/" + imageId)
            .getDownloadUrl()
            .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(imageView))
            .addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_image));
    }

}