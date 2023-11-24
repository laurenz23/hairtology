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
    private ShopListAdapter.OnShopListListener mListener;

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
    }

    @Override
    public int getItemCount() {
        if (mReviewArrayList == null)
            return 0;

        return mReviewArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ShopReview review;
        private TextView name;
        private TextView feedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shopReviewItemName);
            feedback = itemView.findViewById(R.id.shopReviewItemFeedback);
        }
    }

}

