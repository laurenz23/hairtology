package com.project.hairtologyuser.views.fragments.shoplist;

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
import com.project.hairtologyuser.components.client.FirebaseClient;
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
        mContext = context;
        mShopArrayList = shopArrayList;
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

        if (shop == null) {
            Log.e("SHOP", "NULL");
        } else {
            Log.e("SHOP", "IS NOT NULL");
        }

        holder.name.setText(shop.getShopDetail().getName());
        holder.address.setText(shop.getShopDetail().getAddress());
        holder.hour.setText(shop.getShopDetail().getHour());
        holder.price.setText(shop.getShopDetail().getPrice());

        holder.imageIdArrayList.add(shop.getShopDetail().getImageId1());
        holder.imageIdArrayList.add(shop.getShopDetail().getImageId2());
        holder.imageIdArrayList.add(shop.getShopDetail().getImageId3());

        holder.displayImage();
        holder.imageAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mShopArrayList == null)
            return 0;

        return mShopArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopModel shop;
        private LinearLayout linearLayout;
        private ShopListImageAdapter imageAdapter;
        private ViewPager viewPager;
        private TextView name;
        private TextView address;
        private TextView hour;
        private TextView price;
        private ImageView favorite;
        private ArrayList<String> imageIdArrayList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView, OnShopListListener listener) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.shopItemLinearLayout);
            name = itemView.findViewById(R.id.shopItemName);
            address = itemView.findViewById(R.id.shopItemAddress);
            hour = itemView.findViewById(R.id.shopItemHours);
            price = itemView.findViewById(R.id.shopItemPrice);
            favorite = itemView.findViewById(R.id.shopItemFav);

            linearLayout.setOnClickListener(view -> {
                listener.onShopTap(getAdapterPosition(), shop);
            });
        }

        public void displayImage() {
            viewPager = itemView.findViewById(R.id.shopItemViewPager);
            imageAdapter = new ShopListImageAdapter(itemView.getContext(), shop.getShopDetail().getUuid(), imageIdArrayList);
            viewPager.setPadding(25, 0, 25, 0);
            viewPager.setAdapter(imageAdapter);
        }
    }

}
