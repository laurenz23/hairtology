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
import com.project.hairtologyowner.components.utils.ToastMessage;
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

        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);
        mViewModel.viewModel(requireActivity().getApplication());

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
        mViewModel.getShopList(new ShopListViewModel.OnShopListDataListener() {
            @Override
            public void onSuccess(ArrayList<ShopModel> shop) {
                mShopArrayList.addAll(shop);
                mShopListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMessage) {
                ToastMessage.display(getContext(), errorMessage);
            }
        });
    }

}