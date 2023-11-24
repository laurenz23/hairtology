package com.project.hairtologyowner.views.fragments.shoplist;

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

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopReview;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    public interface OnShopItemListener {
        void onTap(int position, ShopModel shop);
    }

    public interface OnShopItemReadReviewListener {
        void onTap(ArrayList<ShopReview> shopReviewArrayList);
    }

    private Context mContext;
    private ArrayList<ShopModel> mShopArrayList;
    private ShopListAdapter.OnShopItemListener mListener;
    private ShopListAdapter.OnShopItemReadReviewListener mReadReviewListener;

    public ShopListAdapter(Context context, ArrayList<ShopModel> shopArrayList) {
        mContext = context;
        mShopArrayList = shopArrayList;
    }

    public void setOnShopItemListener(OnShopItemListener listener) {
        mListener = listener;
    }

    public void setOnShopReadReviewListener(OnShopItemReadReviewListener listener) {
        mReadReviewListener = listener;
    }

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_shop_layout, parent, false);
        return new ViewHolder(view, mListener, mReadReviewListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        ShopModel shop = mShopArrayList.get(position);
        holder.shop = shop;
        holder.name.setText(shop.getShopDetail().getName());
        holder.address.setText(shop.getShopDetail().getAddress());
        holder.schedules.setText(shop.getShopDetail().getHour());
        holder.price.setText(shop.getShopDetail().getPrice());

        double ratingScore = getRating(shop.getReview());

        if (Double.isNaN(ratingScore)) {
            holder.review.setText("0.0 Read Review");
        } else {
            holder.review.setText(ratingScore + " Read Review...");
        }

        holder.reviewStar5.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar4.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar3.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar2.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar1.setImageResource(R.drawable.ic_star_outline_24);

        if (ratingScore >= 5) {
            holder.reviewStar5.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 4) {
            holder.reviewStar4.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 3) {
            holder.reviewStar3.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 2) {
            holder.reviewStar2.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 1) {
            holder.reviewStar1.setImageResource(R.drawable.ic_star_24);
        }
    }

    @Override
    public int getItemCount() {
        if (mShopArrayList == null) {
            return 0;
        }

        return mShopArrayList.size();
    }

    public double getRating(ArrayList<ShopReview> shopReviewArrayList) {
        int star1Respondent = 0;
        int star2Respondent = 0;
        int star3Respondent = 0;
        int star4Respondent = 0;
        int star5Respondent = 0;
        int totalRespondent;
        int totalScore;
        double rating = 0.0;

        if (shopReviewArrayList != null) {
            for (ShopReview review : shopReviewArrayList) {
                if (review.getStars() >= 5) { star5Respondent++; }
                else if (review.getStars() == 4) { star4Respondent++; }
                else if (review.getStars() == 3) { star3Respondent++; }
                else if (review.getStars() == 2) { star2Respondent++; }
                else if (review.getStars() == 1) { star1Respondent++; }
            }

            totalRespondent = star5Respondent + star4Respondent + star3Respondent + star2Respondent + star1Respondent;
            totalScore = (star5Respondent * 5) + (star4Respondent * 4) + (star3Respondent * 3) + (star2Respondent * 2) + star1Respondent;
            rating = (double) totalScore / totalRespondent;
        }

        return rating;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopModel shop;
        private LinearLayout readReviewLinearLayout;
        private ImageView shopNextImageView;
        private TextView name;
        private TextView address;
        private TextView schedules;
        private TextView price;
        private TextView review;
        private ImageView reviewStar1;
        private ImageView reviewStar2;
        private ImageView reviewStar3;
        private ImageView reviewStar4;
        private ImageView reviewStar5;

        public ViewHolder(@NonNull View itemView, OnShopItemListener listener, OnShopItemReadReviewListener readReviewListener) {
            super(itemView);

            readReviewLinearLayout = itemView.findViewById(R.id.itemShopReadReviewLinearLayout);
            shopNextImageView = itemView.findViewById(R.id.itemShopImageView);
            name = itemView.findViewById(R.id.itemShopName);
            address = itemView.findViewById(R.id.itemShopAddress);
            schedules = itemView.findViewById(R.id.itemShopSchedules);
            price = itemView.findViewById(R.id.itemShopPrice);
            review = itemView.findViewById(R.id.reviewTextView);
            reviewStar1 = itemView.findViewById(R.id.shopReviewItemStar1);
            reviewStar2 = itemView.findViewById(R.id.shopReviewItemStar2);
            reviewStar3 = itemView.findViewById(R.id.shopReviewItemStar3);
            reviewStar4 = itemView.findViewById(R.id.shopReviewItemStar4);
            reviewStar5 = itemView.findViewById(R.id.shopReviewItemStar5);

            shopNextImageView.setOnClickListener(v -> {
                listener.onTap(getAdapterPosition(), shop);
            });

            readReviewLinearLayout.setOnClickListener(v -> {
                readReviewListener.onTap(shop.getReview());
            });
        }
    }

}
