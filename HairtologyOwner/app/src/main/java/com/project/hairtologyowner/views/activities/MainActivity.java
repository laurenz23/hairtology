package com.project.hairtologyowner.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.IdRes;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.views.fragments.profile.ProfileFragment;
import com.project.hairtologyowner.views.fragments.shoplist.ShopListFragment;
import com.project.hairtologyowner.views.fragments.useraccountlist.UserAccountListFragment;
import com.project.hairtologyowner.views.fragments.userreservationlist.UserReservationListFragment;

public class MainActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(containerViewId, new ShopListFragment(), ShopListFragment.class.getSimpleName());

        ImageView shopsImageView = findViewById(R.id.mainShopsImageView);
        ImageView userReservationImageView = findViewById(R.id.mainUserReservationImageView);
        ImageView usersImageView = findViewById(R.id.mainUsersImageView);
        ImageView profileImageView = findViewById(R.id.mainProfileImageView);

        shopsImageView.setOnClickListener(v -> {
            replaceFragment(new ShopListFragment(), containerViewId);
        });

        userReservationImageView.setOnClickListener(v -> {
            replaceFragment(new UserReservationListFragment(), containerViewId);
        });

        usersImageView.setOnClickListener(v -> {
            replaceFragment(new UserAccountListFragment(), containerViewId);
        });

        profileImageView.setOnClickListener(v -> {
            replaceFragment(new ProfileFragment(), containerViewId);
        });

    }
}