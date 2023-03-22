package com.project.myapplication.views.activities.onboarding;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.project.myapplication.BuildConfig;
import com.project.myapplication.R;
import com.project.myapplication.views.fragments.login.LoginFragment;

public class OnBoardingActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".LOGIN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        addFragment(R.id.onBoardingFragment,
                new LoginFragment(),
                FRAGMENT_TAG);
    }

    private void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }
}