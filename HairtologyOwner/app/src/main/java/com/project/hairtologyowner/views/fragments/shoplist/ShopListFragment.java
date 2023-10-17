package com.project.hairtologyowner.views.fragments.shoplist;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.components.utils.ToastMessage;
import com.project.hairtologyowner.models.ShopInfo;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.addshop.AddShopFragment;
import com.project.hairtologyowner.views.fragments.shopinfo.ShopInfoFragment;
import com.project.hairtologyowner.views.fragments.useraccountinfo.UserAccountInfoFragment;
import com.project.hairtologyowner.views.fragments.useraccountlist.UserAccountListFragment;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private ShopListViewModel mViewModel;
    private ShopListAdapter mShopListAdapter;
    private ImageView mAddShopImageView;
    private ArrayList<ShopModel> mShopArrayList = new ArrayList<>();

    public static ShopListFragment newInstance() {
        return new ShopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        mAddShopImageView = view.findViewById(R.id.addShopListFragment);

        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);
        mViewModel.viewModel(requireActivity().getApplication());

        mShopListAdapter = new ShopListAdapter(getContext(), mShopArrayList);
        mShopListAdapter.setOnShopItemListener((position, shop) -> {
            if (getActivity() == null) {
                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        ShopListFragment.class
                ));
                return;
            }

            ((MainActivity) getActivity()).replaceFragment(
                    ShopInfoFragment.newInstance(shop),
                    MainActivity.containerViewId);
        });

        RecyclerView recyclerView = view.findViewById(R.id.shopListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mShopListAdapter);

        mAddShopImageView.setOnClickListener(addShopView -> {
            if (getActivity() == null) {
                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        ShopListFragment.class
                ));
                return;
            }

            ((MainActivity) getActivity()).replaceFragment(
                    new AddShopFragment(),
                    MainActivity.containerViewId);
        });

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