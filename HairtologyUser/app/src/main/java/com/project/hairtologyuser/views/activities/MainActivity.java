package com.project.hairtologyuser.views.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.IdRes;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.views.fragments.profile.ProfileFragment;
import com.project.hairtologyuser.views.fragments.reservation.ReservationFragment;
import com.project.hairtologyuser.views.fragments.shop.ShopFragment;

public class MainActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(containerViewId, new ShopFragment(), ShopFragment.FRAGMENT_TAG);

        ImageView homeImageView = findViewById(R.id.homeImageView);
        ImageView reservationImageView = findViewById(R.id.reservationImageView);
//        ImageView favoriteImageView = findViewById(R.id.favoriteImageView);
        ImageView accountImageView = findViewById(R.id.accountImageView);

        homeImageView.setOnClickListener(view -> {
            replaceFragment(
                new ShopFragment(),
                containerViewId);
        });

        reservationImageView.setOnClickListener(view -> {
            replaceFragment(
                new ReservationFragment(),
                containerViewId);
        });

//        favoriteImageView.setOnClickListener(view -> {
//            Log.e(getClass().getSimpleName(), "Messages");
//        });

        accountImageView.setOnClickListener(view -> {
            replaceFragment(
                new ProfileFragment(),
                containerViewId);
        });

    }
}