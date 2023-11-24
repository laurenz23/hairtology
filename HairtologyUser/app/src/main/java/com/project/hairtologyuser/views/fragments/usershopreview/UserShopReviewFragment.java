package com.project.hairtologyuser.views.fragments.usershopreview;

import static com.project.hairtologyuser.views.activities.MainActivity.containerViewId;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.components.utils.ToastMessage;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.shop.ShopFragment;

public class UserShopReviewFragment extends Fragment {

    private static ShopModel mShop;
    private UserShopReviewViewModel mViewModel;
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

    public static UserShopReviewFragment newInstance(ShopModel shop) {
        mShop = shop;
        return new UserShopReviewFragment();
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

        mCancelButton.setOnClickListener(view -> returnToMain());

        mSubmitButton.setOnClickListener(view -> {
            if (mStarRating > 0) {
                mViewModel.submitReview(mStarRating, String.valueOf(mReviewEditText.getText()));
                returnToMain();
            } else {
                new AlertDialog.Builder(getContext())
                    .setMessage("Are you sure you want to submit a poor rating?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mViewModel.submitReview(mStarRating, String.valueOf(mReviewEditText.getText()));
                            returnToMain();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserShopReviewViewModel.class);
        mViewModel.setModel(getContext());
        mViewModel.setShop(mShop);
        mShopNameTextView.setText(mShop.getShopDetail().getName());
    }

    private void setStar(int star) {
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

    private void returnToMain() {
        if (getActivity() == null) {
            ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    UserShopReviewFragment.class
            ));
            return;
        }

        ((MainActivity) getActivity()).replaceFragment(
                new ShopFragment(),
                containerViewId);
    }

}