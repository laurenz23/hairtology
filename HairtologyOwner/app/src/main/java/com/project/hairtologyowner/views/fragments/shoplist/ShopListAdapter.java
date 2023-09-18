package com.project.hairtologyowner.views.fragments.shoplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopModel;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    public interface OnShopItemListener {
        void onTap(int position, ShopModel shop);
    }

    private Context mContext;
    private ArrayList<ShopModel> mShopArrayList;
    private ShopListAdapter.OnShopItemListener mListener;

    public ShopListAdapter(Context context, ArrayList<ShopModel> shopModelArrayList) {
        mContext = context;
        mShopArrayList = shopModelArrayList;
    }

    public void setOnServiceListener(OnShopItemListener listener) {
        mListener = listener;
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
        holder.schedules.setText(shop.getHour());
        holder.price.setText(shop.getPrice());
    }

    @Override
    public int getItemCount() {
        if (mShopArrayList == null) {
            return 0;
        }

        return mShopArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopModel shop;
        private LinearLayout shopLinearLayout;
        private TextView name;
        private TextView address;
        private TextView schedules;
        private TextView price;

        public ViewHolder(@NonNull View itemView, OnShopItemListener listener) {
            super(itemView);

            shopLinearLayout = itemView.findViewById(R.id.itemShopLinearLayout);
            name = itemView.findViewById(R.id.itemShopName);
            address = itemView.findViewById(R.id.itemShopAddress);
            schedules = itemView.findViewById(R.id.itemShopSchedules);
            price = itemView.findViewById(R.id.itemShopPrice);

            shopLinearLayout.setOnClickListener(v -> {
                listener.onTap(getAdapterPosition(), shop);
            });
        }

    }

}
