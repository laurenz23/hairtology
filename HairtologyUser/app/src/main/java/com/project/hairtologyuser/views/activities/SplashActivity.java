package com.project.hairtologyuser.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.hairtologyuser.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int delayTime = 3000;
        new Handler().postDelayed(() -> {
            switchActivity(SplashActivity.this, OnBoardingActivity.class);
        }, delayTime);

    }
}