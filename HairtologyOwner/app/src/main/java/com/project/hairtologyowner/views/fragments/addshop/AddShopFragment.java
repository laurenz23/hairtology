package com.project.hairtologyowner.views.fragments.addshop;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.models.ShopDetail;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;
import com.project.hairtologyowner.models.UserReservationModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.shoplist.ShopListFragment;
import com.project.hairtologyowner.views.fragments.useraccountinfo.UserAccountInfoFragment;
import com.project.hairtologyowner.views.fragments.userreservationinfo.UserReservationInfoFragment;

import java.util.ArrayList;
import java.util.UUID;

public class AddShopFragment extends Fragment {

    private AddShopViewModel mViewModel;
    private View mView;
    private LinearLayout[] mPageLinearLayoutArray = new LinearLayout[4];
    private LinearLayout[] mPageIndicator = new LinearLayout[4];
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
    private Button mPreviousPage4Button;
    private Button mSubmitButton;
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

    private final ActivityResultLauncher<Intent> mSelectImageLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage1));

    private final ActivityResultLauncher<Intent> mSelectImageLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage2));

    private final ActivityResultLauncher<Intent> mSelectImageLauncher3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage3));

    private final ActivityResultLauncher<Intent> mSelectImageLauncher4 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImageService));

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_shop, container, false);

        mPageLinearLayoutArray[0] = mView.findViewById(R.id.page1AddShop);
        mPageLinearLayoutArray[1] = mView.findViewById(R.id.page2AddShop);
        mPageLinearLayoutArray[2] = mView.findViewById(R.id.page3AddShop);
        mPageLinearLayoutArray[3] = mView.findViewById(R.id.page4AddShop);

        mPageIndicator[0] = mView.findViewById(R.id.page1IndicatorAddShop);
        mPageIndicator[1] = mView.findViewById(R.id.page2IndicatorAddShop);
        mPageIndicator[2] = mView.findViewById(R.id.page3IndicatorAddShop);
        mPageIndicator[3] = mView.findViewById(R.id.page4IndicatorAddShop);

        mNextPage1Button = mView.findViewById(R.id.nextPage1AddShop);
        mNextPage2Button = mView.findViewById(R.id.nextPage2AddShop);
        mPreviousPage2Button = mView.findViewById(R.id.previousPage2AddShop);
        mNextPage3Button = mView.findViewById(R.id.nextPage3AddShop);
        mPreviousPage3Button = mView.findViewById(R.id.previousPage3AddShop);
        mAddServiceButton = mView.findViewById(R.id.addServicePage3AddShop);
        mPreviousPage4Button = mView.findViewById(R.id.previousPage4AddShop);
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
        mImageService = mView.findViewById(R.id.imageServiceAddShop);

        mAddServiceListAdapter = new AddServiceListAdapter(getContext(), mServiceArrayList);

        RecyclerView recyclerView = mView.findViewById(R.id.serviceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAddServiceListAdapter);

        mAddServiceListAdapter.setListener(position -> {
            mServiceArrayList.remove(position);
            mAddServiceListAdapter.notifyDataSetChanged();
        });

        mImage1.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher1.launch(intent);
            mViewModel.detail.setImageId1(UUID.randomUUID().toString());
        });

        mImage2.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher2.launch(intent);
            mViewModel.detail.setImageId2(UUID.randomUUID().toString());
        });

        mImage3.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher3.launch(intent);
            mViewModel.detail.setImageId3(UUID.randomUUID().toString());
        });

        mImageService.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher4.launch(intent);
        });

        mNextPage1Button.setOnClickListener(view -> {
            if (mSelectedImage1 == null || mSelectedImage2 == null || mSelectedImage3 == null) {
                Toast.makeText(getContext(), "Please please upload image", Toast.LENGTH_LONG).show();
                return;
            }

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

            mViewModel.detail.setUuid(UUID.randomUUID().toString());
            mViewModel.detail.setName(shopName);
            mViewModel.detail.setDescription(shopDescription);

            setPage(2);
        });

        mNextPage2Button.setOnClickListener(view -> {String shopAddress = String.valueOf(mShopAddressEditText.getText());
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

            mViewModel.detail.setAddress(shopAddress);
            mViewModel.detail.setHour(shopOpenHours);
            mViewModel.detail.setPrice(shopPriceRange);

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
            showPageReview();
        });

        mPreviousPage3Button.setOnClickListener(view -> {
            setPage(2);
        });

        mAddServiceButton.setOnClickListener(view -> {
            String serviceName = String.valueOf(mServiceNameEditText.getText());
            String serviceDescription = String.valueOf(mServiceDescriptionEditText.getText());
            String servicePrice = String.valueOf(mServicePriceEditText.getText());

            if (mSelectedImageService == null) {
                Toast.makeText(getContext(), "Please add an image for your service", Toast.LENGTH_LONG).show();
                return;
            }

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
            serviceData.setImageId(UUID.randomUUID().toString());

            mAddServiceListAdapter.addUri(mSelectedImageService);
            mServiceArrayList.add(serviceData);
            mAddServiceListAdapter.notifyDataSetChanged();

            mServiceNameEditText.setText("");
            mServiceDescriptionEditText.setText("");
            mServicePriceEditText.setText("");
            mImageService.setImageResource(R.drawable.ic_add_image);
            mSelectedImageService = null;
        });

        mPreviousPage4Button.setOnClickListener(view -> {
            hidePageReview();
            setPage(3);
        });

        mSubmitButton.setOnClickListener(view -> {
            mViewModel.service.addAll(mServiceArrayList);

            mViewModel.addShop(new AddShopViewModel.OnAddShopListener() {
                @Override
                public void onSuccess(ShopModel shopModel) {
                    ShopDetail shopDetail = shopModel.getShopDetail();
                    mViewModel.uploadImage(mSelectedImage1, shopDetail.getUuid(), shopDetail.getImageId1());
                    mViewModel.uploadImage(mSelectedImage2, shopDetail.getUuid(), shopDetail.getImageId2());
                    mViewModel.uploadImage(mSelectedImage3, shopDetail.getUuid(), shopDetail.getImageId3());

                    for (int x = 0; x < mAddServiceListAdapter.getItemCount(); x++) {
                        mViewModel.uploadImage(
                                mAddServiceListAdapter.getImageUri(x),
                                mViewModel.detail.getUuid(),
                                mAddServiceListAdapter.getItem(x).getImageId());
                    }


                    if (getActivity() == null) {
                        Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                AddShopFragment.class
                        ));
                        return;
                    }

                    ((MainActivity) getActivity()).replaceFragment(
                            new ShopListFragment(),
                            MainActivity.containerViewId);
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(getContext(), "Encountered an error while adding a shop", Toast.LENGTH_LONG).show();
                }
            });
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AddShopViewModel.class);
        mViewModel.setViewModel(getContext());
    }

    private void setPage(int pageNum) {
        pageNum -= 1;

        for (LinearLayout page : mPageLinearLayoutArray) {
            page.setVisibility(View.GONE);
        }

        mPageLinearLayoutArray[pageNum].setVisibility(View.VISIBLE);
    }

    private void selectImageFromGallery(ActivityResult result, ImageView imageView) {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri selectedImage = result.getData().getData();
                Glide.with(getContext()).load(selectedImage).into(imageView);

                if (imageView == mImageService) {
                    mSelectedImageService = selectedImage;
                }
                else if (imageView == mImage3) {
                    mSelectedImage3 = selectedImage;
                } else if (imageView == mImage2) {
                    mSelectedImage2 = selectedImage;
                } else {
                    mSelectedImage1 = selectedImage;
                }
            }
        } else {
            Toast.makeText(getContext(), "Encountered an error when getting an Image", Toast.LENGTH_LONG).show();
        }
    }

    private void showPageReview() {
        for (LinearLayout linearLayout : mPageIndicator) {
            linearLayout.setVisibility(View.GONE);
        }

        mImageService.setVisibility(View.GONE);
        mServiceNameEditText.setVisibility(View.GONE);
        mServiceDescriptionEditText.setVisibility(View.GONE);
        mServicePriceEditText.setVisibility(View.GONE);

        mNextPage1Button.setVisibility(View.GONE);
        mNextPage2Button.setVisibility(View.GONE);
        mNextPage3Button.setVisibility(View.GONE);
        mPreviousPage2Button.setVisibility(View.GONE);
        mPreviousPage3Button.setVisibility(View.GONE);
        mAddServiceButton.setVisibility(View.GONE);

        for (LinearLayout linearLayout : mPageLinearLayoutArray) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void hidePageReview() {
        for (LinearLayout linearLayout : mPageIndicator) {
            linearLayout.setVisibility(View.VISIBLE);
        }

        mImageService.setVisibility(View.VISIBLE);
        mServiceNameEditText.setVisibility(View.VISIBLE);
        mServiceDescriptionEditText.setVisibility(View.VISIBLE);
        mServicePriceEditText.setVisibility(View.VISIBLE);

        mNextPage1Button.setVisibility(View.VISIBLE);
        mNextPage2Button.setVisibility(View.VISIBLE);
        mNextPage3Button.setVisibility(View.VISIBLE);
        mPreviousPage2Button.setVisibility(View.VISIBLE);
        mPreviousPage3Button.setVisibility(View.VISIBLE);
        mAddServiceButton.setVisibility(View.VISIBLE);

        for (LinearLayout linearLayout : mPageLinearLayoutArray) {
            linearLayout.setVisibility(View.GONE);
        }

        mPageLinearLayoutArray[2].setVisibility(View.VISIBLE);
    }

}