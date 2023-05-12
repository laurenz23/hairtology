package com.project.hairtologyuser.views.fragments.shoplist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.views.fragments.shoplistimage.ShopListImageAdapter;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    public interface OnShopListListener {
        void onShopTap(int position, ShopModel shop);
    }

    private Context mContext;
    private ArrayList<ShopModel> mShopArrayList;
    private OnShopListListener mListener;

    public ShopListAdapter(Context context, ArrayList<ShopModel> shopArrayList) {
        this.mContext = context;
        this.mShopArrayList = shopArrayList;
    }

    public void setOnShopListListener(OnShopListListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_shop_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        ShopModel shop = mShopArrayList.get(position);
        holder.shop = shop;
        holder.name.setText(shop.getName());
        holder.address.setText(shop.getAddress());
        holder.hour.setText(shop.getHour());
        holder.price.setText(shop.getPrice());

        if (shop.getId() == 1) {
            holder.imageArrayList.add(R.drawable.image1);
            holder.imageArrayList.add(R.drawable.image2);
            holder.imageArrayList.add(R.drawable.image3);
        } else if (shop.getId() == 2) {
            holder.imageArrayList.add(R.drawable.image4);
            holder.imageArrayList.add(R.drawable.image5);
            holder.imageArrayList.add(R.drawable.image6);
        }

        holder.imageAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mShopArrayList == null)
            return 0;

        return mShopArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ShopModel shop;
        LinearLayout linearLayout;
        ShopListImageAdapter imageAdapter;
        ViewPager viewPager;
        TextView name;
        TextView address;
        TextView hour;
        TextView price;
        ImageView favorite;
        ArrayList<Integer> imageArrayList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView, OnShopListListener listener) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.shopItemLinearLayout);
            viewPager = itemView.findViewById(R.id.shopItemViewPager);
            name = itemView.findViewById(R.id.shopItemName);
            address = itemView.findViewById(R.id.shopItemAddress);
            hour = itemView.findViewById(R.id.shopItemHours);
            price = itemView.findViewById(R.id.shopItemPrice);
            favorite = itemView.findViewById(R.id.shopItemFav);

            imageAdapter = new ShopListImageAdapter(itemView.getContext(), imageArrayList);
            viewPager.setPadding(25, 0, 25, 0);
            viewPager.setAdapter(imageAdapter);

            linearLayout.setOnClickListener(view -> {
                listener.onShopTap(getAdapterPosition(), shop);
            });
        }
    }

}
