package com.project.hairtologyowner.views.fragments.shopinfo;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;
import com.project.hairtologyowner.views.fragments.addservice.AddServiceListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopInfoFragment extends Fragment {

    private static ShopModel mShop;
    private ShopInfoViewModel mViewModel;
    private View mView;
    private EditText mShopNameEditText;
    private EditText mShopDescriptionEditText;
    private EditText mShopAddressEditText;
    private EditText mShopOpenHoursEditText;
    private EditText mShopPriceRangeEditText;
    private Button mSaveButton;
    private Button mBackButton;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImageService;
    private Uri mSelectedImage1;
    private Uri mSelectedImage2;
    private Uri mSelectedImage3;
    private Uri mSelectedImageService;
    private ArrayList<ShopService> mServiceArrayList = new ArrayList<>();
    private AddServiceListAdapter mAddServiceListAdapter;

    public static ShopInfoFragment newInstance(ShopModel shop) {
        mShop = shop;
        return new ShopInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shop_info, container, false);

        mShopNameEditText = mView.findViewById(R.id.nameShopInfo);
        mShopDescriptionEditText = mView.findViewById(R.id.descriptionShopInfo);
        mShopAddressEditText = mView.findViewById(R.id.addressShopInfo);
        mShopOpenHoursEditText = mView.findViewById(R.id.openHoursShopInfo);
        mShopPriceRangeEditText = mView.findViewById(R.id.priceRangeShopInfo);

        mImage1 = mView.findViewById(R.id.image1ShopInfo);
        mImage2 = mView.findViewById(R.id.image2ShopInfo);
        mImage3 = mView.findViewById(R.id.image3ShopInfo);

        mSaveButton = mView.findViewById(R.id.saveShopInfo);
        mBackButton = mView.findViewById(R.id.backShopInfo);

        mAddServiceListAdapter = new AddServiceListAdapter(getContext(), mServiceArrayList);

        RecyclerView recyclerView = mView.findViewById(R.id.serviceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAddServiceListAdapter);

        return mView;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopInfoViewModel.class);
        mViewModel.setViewModel(mView.getContext());
        mViewModel.setShop(mShop);

        mShopNameEditText.setText(mShop.getShopDetail().getName());
        mShopDescriptionEditText.setText(mShop.getShopDetail().getDescription());
        mShopAddressEditText.setText(mShop.getShopDetail().getAddress());
        mShopOpenHoursEditText.setText(mShop.getShopDetail().getHour());
        mShopPriceRangeEditText.setText(mShop.getShopDetail().getPrice());

        mServiceArrayList.addAll(mShop.getShopService());
        mAddServiceListAdapter.notifyDataSetChanged();

        mViewModel.retrieveImage(mImage1, mShop.getShopDetail().getUuid(), mShop.getShopDetail().getImageId1());
        mViewModel.retrieveImage(mImage2, mShop.getShopDetail().getUuid(), mShop.getShopDetail().getImageId2());
        mViewModel.retrieveImage(mImage3, mShop.getShopDetail().getUuid(), mShop.getShopDetail().getImageId3());
    }

}