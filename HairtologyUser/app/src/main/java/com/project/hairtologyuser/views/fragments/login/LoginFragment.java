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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends BaseFragment {

    private enum Action {
        RESET,
        LOGIN,
        REGISTER
    }

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".LOGIN_FRAGMENT";

    private LoginViewModel mViewModel;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegisterButton;
    private TextView mErrorTextView;
    private LinearLayout mErrorLinearLayout;
    private ProgressBar mLoginProgressBar;
    private final List<String> mErrorMessageList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        View mView = mBinding.getRoot();

        mEmailEditText = mBinding.emailEditText;
        mPasswordEditText = mBinding.passwordEditText;
        mLoginButton = mBinding.loginButton;
        mRegisterButton = mBinding.registerButton;
        mErrorTextView = mBinding.errorTextView;
        mErrorLinearLayout = mBinding.errorLinearLayout;
        mLoginProgressBar = mBinding.loginProgressBar;

        mLoginButton.setOnClickListener(v -> {
            onLoginTap();
        });

        mRegisterButton.setOnClickListener(v -> {
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

        setAction(Action.RESET);
        mViewModel.setViewModel(getActivity().getApplication());
    }

    public void onLoginTap() {
        setAction(Action.LOGIN);

        String email = String.valueOf(mEmailEditText.getText());
        String password = String.valueOf(mPasswordEditText.getText());
        if (validateFields(email, password)) {
            mViewModel.login(email, password, new LoginViewModel.onLoginListener() {
                @Override
                public void onLoginSuccess(UserModel user) {
                    setAction(Action.RESET);

                    if (user.getAccountDisabled()) {
                        setErrorMessage(getString(R.string.str_your_account_is_disabled));
                        displayErrorMessage();
                    } else {
                        if (getActivity() == null) {
                            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                    MainActivity.class
                            ));
                            return;
                        }

                        ((OnBoardingActivity) getActivity()).switchActivity(getContext(), MainActivity.class);
                    }
                }

                @Override
                public void onLoginFailed(String error) {
                    setAction(Action.RESET);
                    setErrorMessage(error);
                    displayErrorMessage();
                }
            });
        } else {
            setAction(Action.RESET);
        }
    }

    public void onRegisterTap() {
        setAction(Action.REGISTER);

        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    LoginFragment.class
            ));

            setAction(Action.RESET);
            return;
        }

        ((OnBoardingActivity) getActivity()).replaceFragment(
                new RegistrationFragment(),
                OnBoardingActivity.containerViewId);
    }

    private boolean validateFields(String email, String password) {
        boolean isValidatePass = true;

        if (email.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_email));
            isValidatePass = false;
        }

        if (password.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_password));
            isValidatePass = false;
        }

        displayErrorMessage();
        return isValidatePass;
    }

    private void setErrorMessage(String errorMessage) {
        mErrorMessageList.add(errorMessage);
    }

    private void displayErrorMessage() {
        if (mErrorMessageList.size() == 0) {
            mErrorTextView.setText("");
            mErrorLinearLayout.setVisibility(View.GONE);
            return;
        } else {
            mErrorLinearLayout.setVisibility(View.VISIBLE);
        }

        int len = mErrorMessageList.size();
        String prefix = (len > 1) ? "• " : "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(prefix);
            stringBuilder.append(mErrorMessageList.get(i));

            if (len > 1 && i < len - 1) {
                stringBuilder.append("\n");
            }
        }

        mErrorTextView.setText(stringBuilder.toString());
        mErrorMessageList.clear();
    }

    private void setAction(Action pageAction) {
        if (pageAction == Action.LOGIN) {
            mLoginProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.GONE);
            mLoginButton.setClickable(false);
            mRegisterButton.setClickable(false);
        } else if (pageAction == Action.REGISTER) {
            mLoginProgressBar.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginButton.setClickable(false);
            mRegisterButton.setClickable(false);
        } else {
            mLoginProgressBar.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginButton.setClickable(true);
            mRegisterButton.setClickable(true);
        }
    }

}