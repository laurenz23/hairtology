package com.project.hairtologyuser.views.fragments.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.hairtologyuser.BuildConfig;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.databinding.FragmentLoginBinding;
import com.project.hairtologyuser.models.UserModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;
import com.project.hairtologyuser.views.fragments.registration.RegistrationFragment;

public class LoginFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".LOGIN_FRAGMENT";

    private FragmentLoginBinding mBinding;
    private View mView;
    private LoginViewModel mViewModel;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mErrorTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mView = mBinding.getRoot();

        mEmailEditText = mBinding.emailEditText;
        mPasswordEditText = mBinding.passwordEditText;
        mErrorTextView = mBinding.errorTextView;

        mBinding.loginButton.setOnClickListener(v -> {
            onLoginTap();
        });

        mBinding.registerButton.setOnClickListener(v -> {
            onRegisterTap();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    LoginFragment.class
            ));
            return;
        }

        mViewModel.setViewModel(getActivity().getApplication());
    }

    public void onLoginTap() {
        String email = String.valueOf(mEmailEditText.getText());
        String password = String.valueOf(mPasswordEditText.getText());
        if (validateFields(email, password)) {
            mViewModel.login(email, password, new LoginViewModel.onLoginListener() {
                @Override
                public void onLoginSuccess(UserModel user) {
                    if (getActivity() == null) {
                        Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                MainActivity.class
                        ));
                        return;
                    }

                    ((OnBoardingActivity) getActivity()).switchActivity(getContext(), MainActivity.class);
                }

                @Override
                public void onLoginFailed(Throwable throwable) {
                    Log.e(getClass().getSimpleName(), throwable.getMessage());
                }
            });
        }
    }

    public void onRegisterTap() {
        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    LoginFragment.class
            ));
            return;
        }

        ((OnBoardingActivity) getActivity()).replaceFragment(
                new RegistrationFragment(),
                OnBoardingActivity.containerViewId);
    }

    private boolean validateFields(String email, String password) {
        boolean isValidatePass = true;
        String errorMessage = "";

        if (email.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_email);
            isValidatePass = false;
        }

        if (password.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_password);
            isValidatePass = false;
        }

        if (isValidatePass) {
            mErrorTextView.setVisibility(View.GONE);
            return true;
        } else {
            mErrorTextView.setText(errorMessage);
            mErrorTextView.setVisibility(View.VISIBLE);
            return false;
        }
    }

}