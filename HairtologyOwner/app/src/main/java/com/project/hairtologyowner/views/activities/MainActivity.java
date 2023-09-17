package com.project.hairtologyowner.views.activities;

import androidx.annotation.IdRes;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.views.fragments.useraccount.UserAccountFragment;
import com.project.hairtologyowner.views.fragments.userreservation.UserReservationFragment;

public class MainActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(containerViewId, new UserReservationFragment(), UserReservationFragment.FRAGMENT_TAG);

        ImageView shopsImageView = findViewById(R.id.mainShopsImageView);
        ImageView userReservationImageView = findViewById(R.id.mainUserReservationImageView);
        ImageView usersImageView = findViewById(R.id.mainUsersImageView);
        ImageView profileImageView = findViewById(R.id.mainProfileImageView);

        shopsImageView.setOnClickListener(v -> {
            Log.e(MainActivity.class.getSimpleName(), "Shops");
        });

        userReservationImageView.setOnClickListener(v -> {
            replaceFragment(new UserReservationFragment(), containerViewId);
        });

        usersImageView.setOnClickListener(v -> {
            replaceFragment(new UserAccountFragment(), containerViewId);
        });

        profileImageView.setOnClickListener(v -> {
            Log.e(MainActivity.class.getSimpleName(), "Profile");
        });

    }
}