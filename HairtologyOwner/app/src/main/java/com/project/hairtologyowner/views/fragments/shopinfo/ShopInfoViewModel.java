package com.project.hairtologyowner.views.fragments.shopinfo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ShopModel;
import com.squareup.picasso.Picasso;

public class ShopInfoViewModel extends ViewModel {

    public interface OnEditShopListener {
        void onSuccess(ShopModel shopModel);
        void onError(String errorMessage);
    }

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

    public ShopModel getShop() {
        return mShop;
    }

    public void deleteShop() {

    }

    public void editShop(ShopInfoViewModel.OnEditShopListener listener) {
        ShopModel shopModel = new ShopModel();
        shopModel.setShopDetail(getShop().getShopDetail());
        shopModel.setShopService(getShop().getShopService());

        mFirebaseClient.getDatabaseReference().child(mFirebaseClient.apiShop() + shopModel.getShopDetail().getUuid())
                .setValue(shopModel).addOnSuccessListener(unused -> {
                    listener.onSuccess(shopModel);
                }).addOnFailureListener(e -> {
                    listener.onError(e.getMessage());
                });
    }

    public void retrieveImage(ImageView imageView, String shopId, String imageId) {
        mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopId + "/" + imageId)
            .getDownloadUrl()
            .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(imageView))
            .addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_image));
    }

    public String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = mContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

}