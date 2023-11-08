package com.project.hairtologyuser.views.fragments.shoplist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.components.utils.ToastMessage;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.reserve.ReserveFragment;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private ShopListViewModel mViewModel;
    private ShopListAdapter mShopAdapter;
    private LinearLayout mShopLoadingLinearLayout;
    private SearchView mShopSearchView;
    private final ArrayList<ShopModel> mShopArrayList = new ArrayList<>();
    private final ArrayList<ShopModel> mExistingShopArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        mShopAdapter = new ShopListAdapter(view.getContext(), mShopArrayList);
        mShopLoadingLinearLayout = view.findViewById(R.id.shopLoadingLinearLayout);
        mShopSearchView = view.findViewById(R.id.shopSearchView);

        RecyclerView recyclerView = view.findViewById(R.id.shopListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mShopAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);

        mViewModel.setViewModel(requireActivity().getApplication());
        mViewModel.getShop(new ShopListViewModel.OnShopDataListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(ArrayList<ShopModel> shopList) {
                mExistingShopArrayList.addAll(shopList);
                mShopArrayList.addAll(mExistingShopArrayList);
                mShopAdapter.notifyDataSetChanged();
                mShopLoadingLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String error) {
                ToastMessage.display(getContext(), "Error: " + error);
                mShopLoadingLinearLayout.setVisibility(View.GONE);
            }
        });

        mShopSearchView.clearFocus();
        mShopSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchedShop) {
                searchShop(searchedShop);
                return true;
            }
        });

        mShopAdapter.setOnShopListListener((position, shop) -> {
            if (getActivity() == null) {
                ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        ShopListFragment.class
                ));
                return;
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(shop);

            Bundle bundle = new Bundle();
            bundle.putString("data", jsonString);

            Fragment fragment = new ReserveFragment();
            fragment.setArguments(bundle);

            ((MainActivity) getActivity()).replaceFragment(
                    fragment,
                    MainActivity.containerViewId);
        });

        mShopLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchShop(String searchedShop) {
        ArrayList<ShopModel> filteredArrayList = new ArrayList<>();

        if (searchedShop.isEmpty()) {
            filteredArrayList = mExistingShopArrayList;
        } else {
            for (ShopModel shop : mExistingShopArrayList) {
                if (shop.getShopDetail().getName().toLowerCase().contains(searchedShop.toLowerCase())) {
                    filteredArrayList.add(shop);
                }
            }
        }

        mShopArrayList.clear();
        mShopArrayList.addAll(filteredArrayList);
        mShopAdapter.notifyDataSetChanged();
    }

}