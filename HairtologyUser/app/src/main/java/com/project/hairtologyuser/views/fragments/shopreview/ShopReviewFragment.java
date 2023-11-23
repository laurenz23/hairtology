package com.project.hairtologyuser.views.fragments.shopreview;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyuser.R;

public class ShopReviewFragment extends Fragment {

    private static String mUserUuid;
    private static String mFirstName;
    private static String mLastName;
    private ShopReviewViewModel mViewModel;
    private View mView;

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

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopReviewViewModel.class);
    }

}