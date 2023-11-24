package com.project.hairtologyowner.views.fragments.shopreview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.models.ShopReview;
import com.project.hairtologyowner.R;

import java.util.ArrayList;

public class ShopReviewFragment extends Fragment {

    private static ArrayList<ShopReview> mShopReviewArrayList = new ArrayList<>();
    private ShopReviewViewModel mViewModel;
    private ShopReviewAdapter mShopReviewAdapter;
    private View mView;

    public static ShopReviewFragment newInstance(ArrayList<ShopReview> shopReviewArrayList) {
        mShopReviewArrayList = shopReviewArrayList;
        return new ShopReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shop_review, container, false);

        mShopReviewAdapter = new ShopReviewAdapter(getContext(), mShopReviewArrayList);
        RecyclerView recyclerView = mView.findViewById(R.id.reviewListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mShopReviewAdapter);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopReviewViewModel.class);
    }

}