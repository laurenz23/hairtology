package com.project.hairtologyuser.views.fragments.shopreview;

import android.content.Context;
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
import com.project.hairtologyuser.views.fragments.shoplist.ShopListAdapter;
import com.project.hairtologyuser.views.fragments.shoplistimage.ShopListImageAdapter;

import java.util.ArrayList;

public class ShopReviewAdapter extends RecyclerView.Adapter<ShopReviewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ShopReview> mReviewArrayList;

    public ShopReviewAdapter(Context context, ArrayList<ShopReview> reviewArrayList) {
        mContext = context;
        mReviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public ShopReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_shop_review, parent, false);
        return new ShopReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopReviewAdapter.ViewHolder holder, int position) {
        ShopReview review = mReviewArrayList.get(position);
        holder.name.setText(review.getFirstName());
        holder.feedback.setText(review.getFeedback());

        int rating = review.getStars();

        holder.reviewStar1.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar2.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar3.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar4.setImageResource(R.drawable.ic_star_outline_24);
        holder.reviewStar5.setImageResource(R.drawable.ic_star_outline_24);

        if (rating >= 5) {
            holder.reviewStar5.setImageResource(R.drawable.ic_star_24);
        }

        if (rating >= 4) {
            holder.reviewStar4.setImageResource(R.drawable.ic_star_24);
        }

        if (rating >= 3) {
            holder.reviewStar3.setImageResource(R.drawable.ic_star_24);
        }

        if (rating >= 2) {
            holder.reviewStar2.setImageResource(R.drawable.ic_star_24);
        }

        if (rating >= 1) {
            holder.reviewStar1.setImageResource(R.drawable.ic_star_24);
        }
    }

    @Override
    public int getItemCount() {
        if (mReviewArrayList == null)
            return 0;

        return mReviewArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView feedback;
        private ImageView reviewStar1;
        private ImageView reviewStar2;
        private ImageView reviewStar3;
        private ImageView reviewStar4;
        private ImageView reviewStar5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shopReviewItemName);
            feedback = itemView.findViewById(R.id.shopReviewItemFeedback);
            reviewStar1 = itemView.findViewById(R.id.shopReviewItemStar1);
            reviewStar2 = itemView.findViewById(R.id.shopReviewItemStar2);
            reviewStar3 = itemView.findViewById(R.id.shopReviewItemStar3);
            reviewStar4 = itemView.findViewById(R.id.shopReviewItemStar4);
            reviewStar5 = itemView.findViewById(R.id.shopReviewItemStar5);
        }
    }

}

