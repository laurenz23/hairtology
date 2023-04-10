package com.project.hairtologyuser.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.views.fragments.home.HomeFragment;

public class MainActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(containerViewId, new HomeFragment(), HomeFragment.FRAGMENT_TAG);

        ImageView homeImageView = findViewById(R.id.homeImageView);
        ImageView reservationImageView = findViewById(R.id.reservationImageView);
        ImageView messageImageView = findViewById(R.id.messageImageView);
        ImageView accountImageView = findViewById(R.id.accountImageView);

        homeImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Home");
        });

        reservationImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Reservation");
        });

        messageImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Messages");
        });

        accountImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Account");
        });

    }
}