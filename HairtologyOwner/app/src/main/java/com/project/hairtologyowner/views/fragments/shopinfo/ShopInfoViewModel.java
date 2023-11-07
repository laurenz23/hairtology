package com.project.hairtologyowner.views.fragments.shopinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopModel;

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

    public Bitmap retrieveImage(String shopId, String imageId) {
        final Bitmap[] bitmap = {null};
        try {
            File localFile = File.createTempFile("tempFile", ".jpg");
            mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopId + "/" + imageId)
                    .getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap[0];
    }

}