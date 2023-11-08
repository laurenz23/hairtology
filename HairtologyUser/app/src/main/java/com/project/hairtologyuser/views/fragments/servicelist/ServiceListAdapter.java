package com.project.hairtologyuser.views.fragments.servicelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ShopService;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    public interface OnServiceListListener {
        void onServiceTap(int position, ShopService service);
    }

    private Context mContext;
    private ArrayList<ShopService> mServiceArrayList;
    private OnServiceListListener mListener;

    public ServiceListAdapter(Context context, ArrayList<ShopService> serviceArrayList) {
        this.mContext = context;
        this.mServiceArrayList = serviceArrayList;
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
    }

    @Override
    public int getItemCount() {
        if (mServiceArrayList == null)
            return 0;

        return mServiceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopService service;
        private final TextView serviceName;
        private final TextView description;
        private final TextView price;

        public ViewHolder(@NonNull View itemView, OnServiceListListener listener) {
            super(itemView);

            LinearLayout linearLayout = itemView.findViewById(R.id.serviceItemLinearLayout);
            serviceName = itemView.findViewById(R.id.itemServiceName);
            description = itemView.findViewById(R.id.itemServiceDescription);
            price = itemView.findViewById(R.id.itemServicePrice);

            linearLayout.setOnClickListener(v -> {
                listener.onServiceTap(getAdapterPosition(), service);
            });
        }
    }

}
