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

import java.util.ArrayList;

public class ShopListImageAdapter extends PagerAdapter {

    Context context;
    ArrayList<Integer> imageArrayList;

    public ShopListImageAdapter(Context context, ArrayList<Integer> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint({"CheckResult", "InflateParams"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_shop_image_list, null);
        ImageView imageView = view.findViewById(R.id.shopItemImageView);
        Glide.with(context)
            .asBitmap()
            .load(imageArrayList.get(position))
            .into(imageView);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
