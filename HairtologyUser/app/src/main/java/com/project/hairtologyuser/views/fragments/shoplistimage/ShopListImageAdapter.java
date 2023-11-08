package com.project.hairtologyuser.views.fragments.shoplistimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ShopModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopListImageAdapter extends PagerAdapter {

    private Context mContext;
    private String mShopUuid;
    private ArrayList<String> mImageArrayList;
    private FirebaseClient mFirebaseClient;

    public ShopListImageAdapter(Context context, String shopUuid, ArrayList<String> imageArrayList) {
        mContext = context;
        mShopUuid = shopUuid;
        mImageArrayList = imageArrayList;
        mFirebaseClient = new FirebaseClient(mContext);
    }

    @Override
    public int getCount() {
        return mImageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint({"CheckResult", "InflateParams"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_shop_image_list, null);
        ImageView imageView = view.findViewById(R.id.shopItemImageView);
        retrieveImage(imageView, mShopUuid, mImageArrayList.get(position));
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void retrieveImage(ImageView imageView, String shopId, String imageId) {
        mFirebaseClient.getStorageReference().child(mFirebaseClient.storageShops() + shopId + "/" + imageId)
                .getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(imageView))
                .addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_image));
    }

}
