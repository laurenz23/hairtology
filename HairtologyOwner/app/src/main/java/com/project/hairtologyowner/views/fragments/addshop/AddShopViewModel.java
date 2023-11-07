package com.project.hairtologyowner.views.fragments.addshop;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopDetail;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
        shopModel.setShopService(service);

        mFirebaseClient.getDatabaseReference().child(mFirebaseClient.apiShop() + shopModel.getShopDetail().getUuid())
                .setValue(shopModel).addOnSuccessListener(unused -> {
                    listener.onSuccess(shopModel);
                }).addOnFailureListener(e -> {
                    listener.onError(e.getMessage());
                });
    }

    public void uploadImage(Uri imageUri, ShopDetail shopDetail, String imageUuid) {
        mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopDetail.getUuid() + "/" + imageUuid)
                .putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(mContext, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(mContext, "Encountered an error while uploading an image", Toast.LENGTH_LONG).show();
                });
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

    public String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = mContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

//    public Uri retrieveImage(String shopId, String imageId) {
//        final Uri[] uri = {null};
//        try {
//            File localFile = File.createTempFile("tempFile", ".jpg");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            byte[] bitmapData = bytes.toByteArray();
//            mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopId + "/" + imageId)
//                    .getFile(localFile)
//                    .addOnSuccessListener(taskSnapshot -> {
//                        uri[0] = localFile.getAbsolutePath();
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return uri[0];
//    }

}