package com.project.hairtologyowner.views.fragments.shopinfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopModel;

public class ShopInfoFragment extends Fragment {

    private static ShopModel mShop;
    private ShopInfoViewModel mViewModel;
    private View mView;

    public static ShopInfoFragment newInstance(ShopModel shop) {
        mShop = shop;
        return new ShopInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shop_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopInfoViewModel.class);
        Log.e(ShopInfoFragment.class.getSimpleName(), "This is working");
    }

}