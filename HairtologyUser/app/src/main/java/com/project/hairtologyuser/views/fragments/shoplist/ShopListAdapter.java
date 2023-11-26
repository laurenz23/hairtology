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
import com.project.hairtologyuser.models.ShopReview;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        ShopModel shop = mShopArrayList.get(position);

        if (shop != null) {
            holder.shop = shop;
            holder.name.setText(shop.getShopDetail().getName());
            holder.address.setText(shop.getShopDetail().getAddress());
            holder.hour.setText(shop.getShopDetail().getHour());
            holder.price.setText(shop.getShopDetail().getPrice());

            holder.imageIdArrayList.add(shop.getShopDetail().getImageId1());
            holder.imageIdArrayList.add(shop.getShopDetail().getImageId2());
            holder.imageIdArrayList.add(shop.getShopDetail().getImageId3());

            holder.displayImage();
            holder.imageAdapter.notifyDataSetChanged();

            double ratingScore = getRating(shop.getReview());

            if (Double.isNaN(ratingScore)) {
                holder.rating.setText("0.0");
            } else {
                holder.rating.setText(String.valueOf(ratingScore));
            }

            holder.star5.setImageResource(R.drawable.ic_star_outline_24);
            holder.star4.setImageResource(R.drawable.ic_star_outline_24);
            holder.star3.setImageResource(R.drawable.ic_star_outline_24);
            holder.star2.setImageResource(R.drawable.ic_star_outline_24);
            holder.star1.setImageResource(R.drawable.ic_star_outline_24);

            if (ratingScore >= 5) {
                holder.star5.setImageResource(R.drawable.ic_star_24);
            }

            if (ratingScore >= 4) {
                holder.star4.setImageResource(R.drawable.ic_star_24);
            }

            if (ratingScore >= 3) {
                holder.star3.setImageResource(R.drawable.ic_star_24);
            }

            if (ratingScore >= 2) {
                holder.star2.setImageResource(R.drawable.ic_star_24);
            }

            if (ratingScore >= 1) {
                holder.star1.setImageResource(R.drawable.ic_star_24);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mShopArrayList == null)
            return 0;

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
        private LinearLayout linearLayout;
        private ShopListImageAdapter imageAdapter;
        private ViewPager viewPager;
        private TextView name;
        private TextView address;
        private TextView hour;
        private TextView price;
        private TextView rating;
        private ImageView favorite;
        private ImageView star1;
        private ImageView star2;
        private ImageView star3;
        private ImageView star4;
        private ImageView star5;
        private ArrayList<String> imageIdArrayList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView, OnShopListListener listener) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.shopItemLinearLayout);
            name = itemView.findViewById(R.id.shopItemName);
            address = itemView.findViewById(R.id.shopItemAddress);
            hour = itemView.findViewById(R.id.shopItemHours);
            price = itemView.findViewById(R.id.shopItemPrice);
            rating = itemView.findViewById(R.id.shopItemRatingTextView);
            favorite = itemView.findViewById(R.id.shopItemFav);
            star1 = itemView.findViewById(R.id.shopItemStar1);
            star2 = itemView.findViewById(R.id.shopItemStar2);
            star3 = itemView.findViewById(R.id.shopItemStar3);
            star4 = itemView.findViewById(R.id.shopItemStar4);
            star5 = itemView.findViewById(R.id.shopItemStar5);

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
