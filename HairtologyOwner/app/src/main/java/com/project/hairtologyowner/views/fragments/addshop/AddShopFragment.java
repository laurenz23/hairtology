package com.project.hairtologyowner.views.fragments.addshop;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.storage.StorageReference;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ShopInfo;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;

import java.util.ArrayList;

public class AddShopFragment extends Fragment {

    private AddShopViewModel mViewModel;
    private View mView;
    private LinearProgressIndicator mProgressIndicator;
    private Uri mSelectedImage;
    private LinearLayout[] mPageLinearLayoutArray = new LinearLayout[4];
    private EditText mShopNameEditText;
    private EditText mShopDescriptionEditText;
    private EditText mShopAddressEditText;
    private EditText mShopOpenHoursEditText;
    private EditText mShopPriceRangeEditText;
    private EditText mServiceNameEditText;
    private EditText mServiceDescriptionEditText;
    private EditText mServicePriceEditText;
    private Button mNextPage1Button;
    private Button mNextPage2Button;
    private Button mPreviousPage2Button;

    private Button mNextPage3Button;
    private Button mPreviousPage3Button;
    private Button mAddServiceButton;
    private Button mSubmitButton;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ShopModel mShopModel;
    private ShopInfo mShopInfo;
    private ArrayList<ShopService> mServiceArrayList = new ArrayList<>();
    private AddServiceListAdapter mAddServiceListAdapter;
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        mSelectedImage = result.getData().getData();
                        Glide.with(getContext()).load(mSelectedImage).into(mImage1);
                    }
                } else {
                    Toast.makeText(getContext(), "Encountered an error when getting an Image", Toast.LENGTH_LONG).show();
                }
            });

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_shop, container, false);

        mPageLinearLayoutArray[0] = mView.findViewById(R.id.page1AddShop);
        mPageLinearLayoutArray[1] = mView.findViewById(R.id.page2AddShop);
        mPageLinearLayoutArray[2] = mView.findViewById(R.id.page3AddShop);
        mPageLinearLayoutArray[3] = mView.findViewById(R.id.page4AddShop);

        mNextPage1Button = mView.findViewById(R.id.nextPage1AddShop);
        mNextPage2Button = mView.findViewById(R.id.nextPage2AddShop);
        mPreviousPage2Button = mView.findViewById(R.id.previousPage2AddShop);
        mNextPage3Button = mView.findViewById(R.id.nextPage3AddShop);
        mPreviousPage3Button = mView.findViewById(R.id.previousPage3AddShop);
        mAddServiceButton = mView.findViewById(R.id.addServicePage3AddShop);
        mSubmitButton = mView.findViewById(R.id.submitPage4AddShop);

        mShopNameEditText = mView.findViewById(R.id.nameAddShop);
        mShopDescriptionEditText = mView.findViewById(R.id.descriptionAddShop);
        mShopAddressEditText = mView.findViewById(R.id.addressAddShop);
        mShopOpenHoursEditText = mView.findViewById(R.id.openHoursAddShop);
        mShopPriceRangeEditText = mView.findViewById(R.id.priceRangeAddShop);

        mServiceNameEditText = mView.findViewById(R.id.serviceNameAddShop);
        mServiceDescriptionEditText = mView.findViewById(R.id.serviceDescriptionAddShop);
        mServicePriceEditText = mView.findViewById(R.id.servicePriceAddShop);

        mImage1 = mView.findViewById(R.id.image1AddShop);
        mImage2 = mView.findViewById(R.id.image2AddShop);
        mImage3 = mView.findViewById(R.id.image3AddShop);

        mAddServiceListAdapter = new AddServiceListAdapter(getContext(), mServiceArrayList);

        RecyclerView recyclerView = mView.findViewById(R.id.serviceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAddServiceListAdapter);

        mImage1.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mActivityResultLauncher.launch(intent);
        });

        mImage2.setOnClickListener(view -> {
            mViewModel.uploadImage(mSelectedImage);
        });

        mImage3.setOnClickListener(view -> {
            Log.e(AddShopFragment.class.getSimpleName(), "Image 3");
        });

        mNextPage1Button.setOnClickListener(view -> {
            String shopName = String.valueOf(mShopNameEditText.getText());
            String shopDescription = String.valueOf(mShopDescriptionEditText.getText());

            if (shopName.equals("")) {
                Toast.makeText(getContext(), "Please provide the shop name", Toast.LENGTH_LONG).show();
                return;
            }

            if (shopDescription.equals("")) {
                Toast.makeText(getContext(), "Please provide a short description", Toast.LENGTH_LONG).show();
                return;
            }

            mShopInfo.setName(shopName);
            mShopInfo.setDescription(shopDescription);

            setPage(2);
        });

        mNextPage2Button.setOnClickListener(view -> {
            String shopAddress = String.valueOf(mShopAddressEditText.getText());
            String shopOpenHours = String.valueOf(mShopOpenHoursEditText.getText());
            String shopPriceRange = String.valueOf(mShopPriceRangeEditText.getText());

            if (shopAddress.equals("")) {
                Toast.makeText(getContext(), "Please provide shop address for user to locate the shop", Toast.LENGTH_LONG).show();
                return;
            }

            if (shopOpenHours.equals("")) {
                Toast.makeText(getContext(), "Please provide the expected the shops start to open and close", Toast.LENGTH_LONG).show();
                return;
            }

            if (shopPriceRange.equals("")) {
                Toast.makeText(getContext(), "Please provide the price range of your services from min to max", Toast.LENGTH_LONG).show();
                return;
            }

            mShopInfo.setAddress(shopAddress);
            mShopInfo.setHour(shopOpenHours);
            mShopInfo.setPrice(shopPriceRange);

            setPage(3);
        });

        mPreviousPage2Button.setOnClickListener(view -> {
            setPage(1);
        });

        mNextPage3Button.setOnClickListener(view -> {
            if (mAddServiceListAdapter.getItemCount() <= 0) {
                Toast.makeText(getContext(), "Please add at least 1 service to your shop", Toast.LENGTH_LONG).show();
                return;
            }

            setPage(4);
        });

        mPreviousPage3Button.setOnClickListener(view -> {
            setPage(2);
        });

        mAddServiceButton.setOnClickListener(view -> {
            String serviceName = String.valueOf(mServiceNameEditText.getText());
            String serviceDescription = String.valueOf(mServiceDescriptionEditText.getText());
            String servicePrice = String.valueOf(mServicePriceEditText.getText());

            if (serviceName.equals("")) {
                Toast.makeText(getContext(), "Please provide the service name", Toast.LENGTH_LONG).show();
                return;
            }

            if (serviceDescription.equals("")) {
                Toast.makeText(getContext(), "Please provide a short service description", Toast.LENGTH_LONG).show();
                return;
            }

            if (servicePrice.equals("")) {
                Toast.makeText(getContext(), "Please provide the price of this service", Toast.LENGTH_LONG).show();
                return;
            }

            ShopService serviceData = new ShopService();
            serviceData.setName(serviceName);
            serviceData.setDescription(serviceDescription);
            serviceData.setPrice(servicePrice);

            mServiceNameEditText.setText("");
            mServiceDescriptionEditText.setText("");
            mServicePriceEditText.setText("");

            mServiceArrayList.add(serviceData);
            mAddServiceListAdapter.notifyDataSetChanged();
        });

        mSubmitButton.setOnClickListener(view -> {
            mShopModel.setShopInfo(mShopInfo);
            mShopModel.setShopServiceArrayList(mServiceArrayList);
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AddShopViewModel.class);
        mViewModel.setViewModel(getContext());

        mShopInfo = new ShopInfo();
        mShopModel = new ShopModel();

        setPage(1);
    }

    private void setPage(int pageNum) {
        pageNum -= 1;

        for (LinearLayout page : mPageLinearLayoutArray) {
            page.setVisibility(View.GONE);
        }

        mPageLinearLayoutArray[pageNum].setVisibility(View.VISIBLE);
    }

}