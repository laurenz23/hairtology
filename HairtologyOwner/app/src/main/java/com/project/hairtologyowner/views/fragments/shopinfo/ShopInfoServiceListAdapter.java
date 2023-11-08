package com.project.hairtologyowner.views.fragments.shopinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopService;

import java.util.ArrayList;

public class ShopInfoServiceListAdapter extends RecyclerView.Adapter<ShopInfoServiceListAdapter.ViewHolder> {

    public interface OnServiceItemListener {
        void removedItem(int position);
    }

    private Context mContext;
    private ArrayList<ShopService> mServiceArrayList;
    private ArrayList<Uri> mUriArrayList = new ArrayList<>();
    private OnServiceItemListener mOnServiceItemListener;

    public ShopInfoServiceListAdapter(Context context, ArrayList<ShopService> serviceArrayList) {
        mContext = context;
        mServiceArrayList = serviceArrayList;
    }

    public Uri getImageUri(int position) {
        return mUriArrayList.get(position);
    }

    public void addUri(Uri imageUri) {
        mUriArrayList.add(imageUri);
    }

    public void setListener(OnServiceItemListener listener) {
        mOnServiceItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_service_layout, parent, false);
        return new ShopInfoServiceListAdapter.ViewHolder(view, mOnServiceItemListener);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopService service = mServiceArrayList.get(position);
        holder.position = position;
        holder.service = service;
        holder.nameTextView.setText(service.getName());
        holder.descriptionTextView.setText(service.getDescription());
        holder.priceTextView.setText(service.getPrice());
//        holder.imageView.setImageURI(mUriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mServiceArrayList == null)
            return 0;

        return mServiceArrayList.size();
    }

    public ShopService getItem(int position) {
        return mServiceArrayList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private ShopService service;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;
        private ImageView imageView;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView, OnServiceItemListener listener) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.itemServiceName);
            descriptionTextView = itemView.findViewById(R.id.itemServiceDescription);
            priceTextView = itemView.findViewById(R.id.itemServicePrice);
            imageView = itemView.findViewById(R.id.itemServiceImage);
            deleteButton = itemView.findViewById(R.id.itemServiceDelete);

            deleteButton.setOnClickListener(view -> {
                listener.removedItem(position);
            });
        }

    }

}