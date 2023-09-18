package com.project.hairtologyowner.views.fragments.shopinfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopModel;

public class ShopInfoFragment extends Fragment {

    private static ShopModel mShop;
    private ShopInfoViewModel mViewModel;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private TextView mAddressTextView;
    private TextView mPriceTextView;

    public static ShopInfoFragment newInstance(ShopModel shop) {
        mShop = shop;
        return new ShopInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_info, container, false);

        mViewModel = new ViewModelProvider(this).get(ShopInfoViewModel.class);
        mViewModel.viewModel(mShop);

        mNameTextView = view.findViewById(R.id.shopInfoName);
        mDescriptionTextView = view.findViewById(R.id.shopInfoDescription);
        mAddressTextView = view.findViewById(R.id.shopInfoAddress);
        mPriceTextView = view.findViewById(R.id.shopInfoPrice);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameTextView.setText(mViewModel.getShop().getName());
        mDescriptionTextView.setText(mViewModel.getShop().getDescription());
        mAddressTextView.setText(mViewModel.getShop().getAddress());
        mPriceTextView.setText(mViewModel.getShop().getPrice());
    }

}