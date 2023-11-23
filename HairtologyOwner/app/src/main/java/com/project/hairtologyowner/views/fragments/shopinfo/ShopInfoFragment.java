package com.project.hairtologyowner.views.fragments.shopinfo;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.models.ShopModel;
import com.project.hairtologyowner.models.ShopService;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.shoplist.ShopListFragment;

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
    private ShopInfoServiceListAdapter mAddServiceListAdapter;


    private final ActivityResultLauncher<Intent> mSelectImageLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage1));

    private final ActivityResultLauncher<Intent> mSelectImageLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage2));

    private final ActivityResultLauncher<Intent> mSelectImageLauncher3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> selectImageFromGallery(result, mImage3));

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

        mAddServiceListAdapter = new ShopInfoServiceListAdapter(getContext(), mShop.getShopDetail(), mServiceArrayList);

        RecyclerView recyclerView = mView.findViewById(R.id.serviceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAddServiceListAdapter);

        mImage1.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher1.launch(intent);
        });

        mImage2.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher2.launch(intent);
        });

        mImage3.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            mSelectImageLauncher3.launch(intent);
        });

        mBackButton.setOnClickListener(view -> backToHomePage());

        mSaveButton.setOnClickListener(view -> mViewModel.editShop(new ShopInfoViewModel.OnEditShopListener() {
            @Override
            public void onSuccess(ShopModel shopModel) {
                backToHomePage();
                Toast.makeText(getContext(), "Shop details successfully updated!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext(), "Encountered an error while updating shop details", Toast.LENGTH_LONG).show();
            }
        }));

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

    private void selectImageFromGallery(ActivityResult result, ImageView imageView) {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri selectedImage = result.getData().getData();
                Glide.with(getContext()).load(selectedImage).into(imageView);

                if (imageView == mImageService) {
                    mSelectedImageService = selectedImage;
                } else if (imageView == mImage3) {
                    mSelectedImage3 = selectedImage;
                    mViewModel.getShop().getShopDetail().setImageId3(System.currentTimeMillis() + "." + mViewModel.getFileExtension(mSelectedImage3));
                } else if (imageView == mImage2) {
                    mSelectedImage2 = selectedImage;
                    mViewModel.getShop().getShopDetail().setImageId2(System.currentTimeMillis() + "." + mViewModel.getFileExtension(mSelectedImage2));
                } else {
                    mSelectedImage1 = selectedImage;
                    mViewModel.getShop().getShopDetail().setImageId1(System.currentTimeMillis() + "." + mViewModel.getFileExtension(mSelectedImage1));
                }
            }
        } else {
            Toast.makeText(getContext(), "Encountered an error when getting an Image", Toast.LENGTH_LONG).show();
        }
    }

    private void backToHomePage() {
        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    ShopInfoFragment.class
            ));
            return;
        }

        ((MainActivity) getActivity()).replaceFragment(
                new ShopListFragment(),
                MainActivity.containerViewId);
    }

}