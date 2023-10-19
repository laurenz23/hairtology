package com.project.hairtologyowner.views.fragments.addshop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.project.hairtologyowner.R;

public class AddShopFragment extends Fragment {

    private AddShopViewModel mViewModel;
    private View mView;
    private LinearLayout[] mPageLinearLayoutArray = new LinearLayout[3];
    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private EditText mAddressEditText;
    private EditText mOpenHoursEditText;
    private EditText mPriceRangeEditText;
    private Button mNextPage1Button;
    private Button mNextPage2Button;
    private Button mPreviousPage2Button;
    private Button mSubmitButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_shop, container, false);

        mPageLinearLayoutArray[0] = mView.findViewById(R.id.page1AddShop);
        mPageLinearLayoutArray[1] = mView.findViewById(R.id.page2AddShop);
        mPageLinearLayoutArray[2] = mView.findViewById(R.id.page3AddShop);

        mNextPage1Button = mView.findViewById(R.id.nextPage1AddShop);
        mNextPage2Button = mView.findViewById(R.id.nextPage2AddShop);
        mPreviousPage2Button = mView.findViewById(R.id.previousPage2AddShop);
        mSubmitButton = mView.findViewById(R.id.submitPage3AddShop);

        mNameEditText = mView.findViewById(R.id.nameAddShop);
        mDescriptionEditText = mView.findViewById(R.id.descriptionAddShop);
        mAddressEditText = mView.findViewById(R.id.addressAddShop);
        mOpenHoursEditText = mView.findViewById(R.id.openHoursAddShop);
        mPriceRangeEditText = mView.findViewById(R.id.priceRangeAddShop);

        mNextPage1Button.setOnClickListener(view -> {
            setPage(2);
        });

        mNextPage2Button.setOnClickListener(view -> {
            setPage(3);
        });

        mPreviousPage2Button.setOnClickListener(view -> {
            setPage(1);
        });

        mPreviousPage2Button.setOnClickListener(view -> {
            String name = String.valueOf(mNameEditText.getText());
            String description = String.valueOf(mDescriptionEditText.getText());;
            String address = String.valueOf(mAddressEditText.getText());;
            String openHours = String.valueOf(mOpenHoursEditText.getText());;
            String priceRange = String.valueOf(mPriceRangeEditText.getText());;

            if (isPage1Validated(name, description) && isPage2Validated(address, openHours, priceRange)) {
                Log.e(AddShopFragment.class.getSimpleName(), name);
                Log.e(AddShopFragment.class.getSimpleName(), description);
                Log.e(AddShopFragment.class.getSimpleName(), address);
                Log.e(AddShopFragment.class.getSimpleName(), openHours);
                Log.e(AddShopFragment.class.getSimpleName(), priceRange);
                Log.e(AddShopFragment.class.getSimpleName(), "SUCCESS");
            } else {
                Log.e(AddShopFragment.class.getSimpleName(), "ERROR");
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddShopViewModel.class);

        setPage(1);
    }

    private void setPage(int pageNum) {
        pageNum -= 1;

        for (LinearLayout page : mPageLinearLayoutArray) {
            page.setVisibility(View.GONE);
        }

        mPageLinearLayoutArray[pageNum].setVisibility(View.VISIBLE);
    }

    private boolean isPage1Validated(String name, String description) {
        if (name.isEmpty())
            return false;

        if (description.isEmpty())
            return false;

        return true;
    }

    private boolean isPage2Validated(String address, String openHours, String priceRange) {
        if (address.isEmpty())
            return false;

        if (openHours.isEmpty())
            return false;

        if (priceRange.isEmpty())
            return false;

        return true;
    }

}