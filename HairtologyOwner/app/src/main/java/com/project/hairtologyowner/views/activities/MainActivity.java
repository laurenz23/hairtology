package com.project.hairtologyowner.views.activities;

import androidx.annotation.IdRes;

import android.os.Bundle;
import android.widget.ImageView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.views.fragments.userreservation.UserReservationFragment;

public class MainActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(containerViewId, new UserReservationFragment(), UserReservationFragment.FRAGMENT_TAG);

        ImageView userReservationImageView = findViewById(R.id.mainUserReservationImageView);
        ImageView shopsImageView = findViewById(R.id.mainShopsImageView);
        ImageView usersImageView = findViewById(R.id.mainUsersImageView);
        ImageView profileImageView = findViewById(R.id.mainProfileImageView);

        userReservationImageView.setOnClickListener(v -> {

        });

        shopsImageView.setOnClickListener(v -> {

        });

        usersImageView.setOnClickListener(v -> {

        });

        profileImageView.setOnClickListener(v -> {

        });

    }
}