package com.project.hairtologyuser.views.activities;

import android.os.Bundle;

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
    }
}