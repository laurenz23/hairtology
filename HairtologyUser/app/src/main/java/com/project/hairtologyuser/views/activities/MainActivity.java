package com.project.hairtologyuser.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.views.fragments.home.HomeFragment;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;
import com.project.hairtologyuser.views.fragments.profile.ProfileFragment;
import com.project.hairtologyuser.views.fragments.registration.RegistrationFragment;

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
            replaceFragment(
                new HomeFragment(),
                containerViewId);
        });

        reservationImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Reservation");
        });

        messageImageView.setOnClickListener(view -> {
            Log.e(getClass().getSimpleName(), "Messages");
        });

        accountImageView.setOnClickListener(view -> {
            replaceFragment(
                new ProfileFragment(),
                containerViewId);
        });

    }
}