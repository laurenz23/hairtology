package com.project.hairtologyuser.views.activities;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;

public class OnBoardingActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onBoardingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        addFragment(containerViewId, new LoginFragment(), LoginFragment.FRAGMENT_TAG);
    }
}