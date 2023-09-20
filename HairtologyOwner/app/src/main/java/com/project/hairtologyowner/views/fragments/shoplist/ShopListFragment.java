package com.project.hairtologyowner.views.fragments.shoplist;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopInfo;
import com.project.hairtologyowner.models.ShopModel;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private ShopListViewModel mViewModel;
    private ShopListAdapter mShopListAdapter;
    private ArrayList<ShopModel> mShopArrayList = new ArrayList<>();

    public static ShopListFragment newInstance() {
        return new ShopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        mShopListAdapter = new ShopListAdapter(getContext(), mShopArrayList);

        RecyclerView recyclerView = view.findViewById(R.id.shopListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mShopListAdapter);

        return view;
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);

        ShopInfo shopInfo1 = new ShopInfo();
        shopInfo1.setName("Shop 1");
        shopInfo1.setAddress("Address 1");
        shopInfo1.setDescription("Description 1");
        shopInfo1.setHour("Hour 1");
        shopInfo1.setId(1);
        shopInfo1.setPrice("Price 1");

        ShopModel shopModel1 = new ShopModel();
        shopModel1.setShopInfo(shopInfo1);

        ShopInfo shopInfo2 = new ShopInfo();
        shopInfo2.setName("Shop 2");
        shopInfo2.setAddress("Address 2");
        shopInfo2.setDescription("Description 2");
        shopInfo2.setHour("Hour 2");
        shopInfo2.setId(2);
        shopInfo2.setPrice("Price 2");

        ShopModel shopModel2 = new ShopModel();
        shopModel2.setShopInfo(shopInfo2);

        mShopArrayList.add(shopModel1);
        mShopArrayList.add(shopModel2);

        mShopListAdapter.notifyDataSetChanged();
    }

}