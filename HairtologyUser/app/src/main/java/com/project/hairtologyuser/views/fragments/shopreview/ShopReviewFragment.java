package com.project.hairtologyuser.views.fragments.shopreview;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hairtologyuser.R;

public class ShopReviewFragment extends Fragment {

    private static String mUserUuid;
    private static String mFirstName;
    private static String mLastName;
    private ShopReviewViewModel mViewModel;
    private View mView;
    private TextView mShopNameTextView;
    private ImageView mStar1ImageView;
    private ImageView mStar2ImageView;
    private ImageView mStar3ImageView;
    private ImageView mStar4ImageView;
    private ImageView mStar5ImageView;
    private EditText mReviewEditText;
    private Button mCancelButton;
    private Button mSubmitButton;
    private int mStarRating = 0;

    public static ShopReviewFragment newInstance(String userUuid, String firstName, String lastName) {
        mUserUuid = userUuid;
        mFirstName = firstName;
        mLastName = lastName;
        return new ShopReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shop_review, container, false);

        mShopNameTextView = mView.findViewById(R.id.shopReviewNameTextView);
        mStar1ImageView = mView.findViewById(R.id.shopReviewStar1ImageView);
        mStar2ImageView = mView.findViewById(R.id.shopReviewStar2ImageView);
        mStar3ImageView = mView.findViewById(R.id.shopReviewStar3ImageView);
        mStar4ImageView = mView.findViewById(R.id.shopReviewStar4ImageView);
        mStar5ImageView = mView.findViewById(R.id.shopReviewStar5ImageView);
        mReviewEditText = mView.findViewById(R.id.shopReviewMessageTextView);
        mCancelButton = mView.findViewById(R.id.shopReviewCancelButton);
        mSubmitButton = mView.findViewById(R.id.shopReviewSubmitButton);

        mStar1ImageView.setOnClickListener(view -> setStar(1));
        mStar2ImageView.setOnClickListener(view -> setStar(2));
        mStar3ImageView.setOnClickListener(view -> setStar(3));
        mStar4ImageView.setOnClickListener(view -> setStar(4));
        mStar5ImageView.setOnClickListener(view -> setStar(5));

        mCancelButton.setOnClickListener(view -> {
            
        });

        mSubmitButton.setOnClickListener(view -> {

        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopReviewViewModel.class);
    }

    public void setStar(int star) {
        mStarRating = star;

        mStar1ImageView.setImageResource(R.drawable.ic_star_outline_50);
        mStar2ImageView.setImageResource(R.drawable.ic_star_outline_50);
        mStar3ImageView.setImageResource(R.drawable.ic_star_outline_50);
        mStar4ImageView.setImageResource(R.drawable.ic_star_outline_50);
        mStar5ImageView.setImageResource(R.drawable.ic_star_outline_50);

        if (star >= 5) {
            mStar5ImageView.setImageResource(R.drawable.ic_star_50);
        }

        if (star >= 4) {
            mStar4ImageView.setImageResource(R.drawable.ic_star_50);
        }

        if (star >= 3) {
            mStar3ImageView.setImageResource(R.drawable.ic_star_50);
        }

        if (star >= 2) {
            mStar2ImageView.setImageResource(R.drawable.ic_star_50);
        }

        if (star >= 1) {
            mStar1ImageView.setImageResource(R.drawable.ic_star_50);
        }
    }

}