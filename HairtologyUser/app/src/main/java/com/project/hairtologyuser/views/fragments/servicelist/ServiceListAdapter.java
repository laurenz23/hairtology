package com.project.hairtologyuser.views.fragments.servicelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    public interface OnServiceListListener {
        void onServiceTap(int position, ShopService service);
    }

    private Context mContext;
    private ShopDetail mShopDetail;
    private ArrayList<ShopService> mServiceArrayList;
    private OnServiceListListener mListener;

    public ServiceListAdapter(Context context, ShopDetail shopDetail, ArrayList<ShopService> serviceArrayList) {
        mContext = context;
        mShopDetail = shopDetail;
        mServiceArrayList = serviceArrayList;
    }

    public void setOnServiceListListener(OnServiceListListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_shop_service_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ViewHolder holder, int position) {
        ShopService service = mServiceArrayList.get(position);
        holder.service = service;
        holder.serviceName.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.price.setText(String.valueOf(service.getPrice()));
        holder.retrieveImage(holder.imageView, mShopDetail.getUuid(), service.getImageId());
    }

    @Override
    public int getItemCount() {
        if (mServiceArrayList == null)
            return 0;

        return mServiceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopService service;
        private final FirebaseClient firebaseClient;
        private final ImageView imageView;
        private final TextView serviceName;
        private final TextView description;
        private final TextView price;

        public ViewHolder(@NonNull View itemView, OnServiceListListener listener) {
            super(itemView);

            firebaseClient = new FirebaseClient(itemView.getContext());
            LinearLayout linearLayout = itemView.findViewById(R.id.serviceItemLinearLayout);
            imageView = itemView.findViewById(R.id.itemShopServiceImage);
            serviceName = itemView.findViewById(R.id.itemServiceName);
            description = itemView.findViewById(R.id.itemServiceDescription);
            price = itemView.findViewById(R.id.itemServicePrice);

            linearLayout.setOnClickListener(v -> {
                listener.onServiceTap(getAdapterPosition(), service);
            });
        }

        public void retrieveImage(ImageView imageView, String shopId, String imageId) {
            firebaseClient.getStorageReference().child(firebaseClient.storageShops() + shopId + "/" + imageId)
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(imageView))
                    .addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_image));
        }
    }

}
