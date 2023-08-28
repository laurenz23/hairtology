package com.project.hairtologyowner.views.activities;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.repository.Session;
import com.project.hairtologyowner.views.fragments.login.LoginFragment;

public class OnBoardingActivity extends BaseActivity {

    @IdRes
    public static final int containerViewId = R.id.onBoardingFragment;

    public Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        mSession = new Session(this);

        if (mSession.getCurrentUser() == null) {
            addFragment(containerViewId, new LoginFragment(), LoginFragment.FRAGMENT_TAG);
        } else {
            switchActivity(this, MainActivity.class);
        }

    }
}