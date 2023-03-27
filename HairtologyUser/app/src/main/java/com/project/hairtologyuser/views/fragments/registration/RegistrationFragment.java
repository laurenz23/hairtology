package com.project.hairtologyuser.views.fragments.registration;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.databinding.FragmentRegistrationBinding;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;

public class RegistrationFragment extends BaseFragment {

    private FragmentRegistrationBinding mBinding;
    private RegistrationViewModel mViewModel;
    private View mView;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mErrorTextView;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mView = mBinding.getRoot();

        mFirstNameEditText = mBinding.firstNameEditText;
        mLastNameEditText = mBinding.lastNameEditText;
        mEmailEditText = mBinding.emailEditText;
        mPasswordEditText = mBinding.passwordEditText;
        mConfirmPasswordEditText = mBinding.confirmPasswordEditText;
        mErrorTextView = mBinding.errorTextView;

        mBinding.saveButton.setOnClickListener(v -> {
            onSaveTap();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    RegistrationFragment.class
            ));
            return;
        }

        mViewModel.setViewModel(getActivity().getApplication());
    }

    private void onSaveTap() {
        String firstName = String.valueOf(mFirstNameEditText.getText());
        String lastName = String.valueOf(mLastNameEditText.getText());
        String email = String.valueOf(mEmailEditText.getText());
        String password = String.valueOf(mPasswordEditText.getText());
        String confirmPassword = String.valueOf(mConfirmPasswordEditText.getText());

        if (validateFields(firstName, lastName, email, password, confirmPassword)) {

            mViewModel.register(firstName, lastName, email, password,
                    new RegistrationViewModel.onRegisterListener() {
                @Override
                public void onRegisterSuccess() {
                    if (getActivity() == null) {
                        Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                RegistrationFragment.class
                        ));
                        return;
                    }

                    ((OnBoardingActivity) getActivity()).replaceFragment(
                            new LoginFragment(),
                            OnBoardingActivity.containerViewId);
                }

                @Override
                public void onRegisterFailed() {

                }
            });
        }
    }

    private boolean validateFields(String firstName, String lastName, String email, String password, String confirmPassword) {
        boolean isValidationPass = true;
        String errorMessage = "";

        if (firstName.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_first_name);
            isValidationPass = false;
        }

        if (lastName.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_last_name);
            isValidationPass = false;
        }

        if (email.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_email);
            isValidationPass = false;
        }

        if (password.isEmpty()) {
            errorMessage += "\n • " + getString(R.string.str_please_enter_password);
            isValidationPass = false;
        } else {
            if (confirmPassword.isEmpty()) {
                errorMessage += "\n • " + getString(R.string.str_please_confirm_password);
                isValidationPass = false;
            } else if (!password.equals(confirmPassword)) {
                errorMessage += "\n • " + getString(R.string.str_password_did_not_match);
                isValidationPass = false;
            }
        }

        if (!isValidationPass) {
            mErrorTextView.setText(errorMessage);
            mErrorTextView.setVisibility(View.VISIBLE);
            return false;
        } else {
            mErrorTextView.setVisibility(View.GONE);
            return true;
        }
    }

}