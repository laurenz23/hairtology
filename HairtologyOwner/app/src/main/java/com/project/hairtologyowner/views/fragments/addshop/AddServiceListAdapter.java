package com.project.hairtologyowner.views.fragments.addshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopService;

import java.util.ArrayList;

public class AddServiceListAdapter extends RecyclerView.Adapter<AddServiceListAdapter.ViewHolder> {

    private interface OnServiceItemListener {
        void addImage();
        void removedItem();
    }

    private Context mContext;
    private ArrayList<ShopService> mServiceArrayList;

    public AddServiceListAdapter(Context context, ArrayList<ShopService> serviceArrayList) {
        mContext = context;
        mServiceArrayList = serviceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_service_layout, parent, false);
        return new AddServiceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopService service = mServiceArrayList.get(position);
        holder.service = service;
        holder.nameTextView.setText(service.getName());
        holder.descriptionTextView.setText(service.getDescription());
        holder.priceTextView.setText(service.getPrice());
    }

    @Override
    public int getItemCount() {
        if (mServiceArrayList == null)
            return 0;

        return mServiceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopService service;
        private LinearLayout serviceLinearLayout;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceLinearLayout = itemView.findViewById(R.id.itemServiceLinearLayout);
            nameTextView = itemView.findViewById(R.id.itemServiceName);
            descriptionTextView = itemView.findViewById(R.id.itemServiceDescription);
            priceTextView = itemView.findViewById(R.id.itemServicePrice);
        }

    }

}
