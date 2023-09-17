package com.project.hairtologyowner.views.fragments.register;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.databinding.FragmentRegisterBinding;
import com.project.hairtologyowner.models.OwnerModel;
import com.project.hairtologyowner.models.UserModel;
import com.project.hairtologyowner.views.activities.OnBoardingActivity;
import com.project.hairtologyowner.views.fragments.login.LoginFragment;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {

    private enum Action {
        RESET,
        REGISTER,
        BACK
    }

    private RegisterViewModel mViewModel;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mErrorTextView;
    private Button mSaveButton;
    private Button mBackButton;
    private ProgressBar mRegistrationProgressBar;
    private LinearLayout mErrorLinearLayout;

    private final List<String> mErrorMessageList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentRegisterBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        View mView = mBinding.getRoot();

        mFirstNameEditText = mBinding.firstNameEditText;
        mLastNameEditText = mBinding.lastNameEditText;
        mEmailEditText = mBinding.emailEditText;
        mPasswordEditText = mBinding.passwordEditText;
        mConfirmPasswordEditText = mBinding.confirmPasswordEditText;
        mSaveButton = mBinding.saveButton;
        mBackButton = mBinding.backButton;
        mRegistrationProgressBar = mBinding.saveProgressBar;
        mErrorTextView = mBinding.errorTextView;
        mErrorLinearLayout = mBinding.errorLinearLayout;

        mBinding.saveButton.setOnClickListener(v -> {
            onSaveTap();
        });

        mBinding.backButton.setOnClickListener(v -> {
            if (getActivity() == null) {
                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        RegisterFragment.class
                ));
                return;
            }

            ((OnBoardingActivity) getActivity()).replaceFragment(
                    new LoginFragment(),
                    OnBoardingActivity.containerViewId);
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    RegisterFragment.class
            ));
            return;
        }

        mViewModel.setViewModel(getActivity().getApplication());
    }

    private void onSaveTap() {
        setAction(Action.REGISTER);

        String firstName = String.valueOf(mFirstNameEditText.getText());
        String lastName = String.valueOf(mLastNameEditText.getText());
        String email = String.valueOf(mEmailEditText.getText());
        String password = String.valueOf(mPasswordEditText.getText());
        String confirmPassword = String.valueOf(mConfirmPasswordEditText.getText());

        if (validateFields(firstName, lastName, email, password, confirmPassword)) {

            mViewModel.register(firstName, lastName, email, password,
                    new RegisterViewModel.onRegisterListener() {
                        @Override
                        public void onRegisterSuccess(OwnerModel owner) {
                            setAction(Action.RESET);

                            if (getActivity() == null) {
                                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                        RegisterFragment.class
                                ));
                                return;
                            }

                            ((OnBoardingActivity) getActivity()).replaceFragment(
                                    new LoginFragment(),
                                    OnBoardingActivity.containerViewId);
                        }

                        @Override
                        public void onRegisterFailed(Throwable throwable) {
                            setAction(Action.RESET);
                            setErrorMessage(throwable.getMessage());
                            displayErrorMessage();
                        }
                    });
        } else {
            setAction(Action.RESET);
        }
    }

    private boolean validateFields(String firstName, String lastName, String email, String password, String confirmPassword) {
        boolean isValidationPass = true;

        if (firstName.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_first_name));
            isValidationPass = false;
        }

        if (lastName.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_last_name));
            isValidationPass = false;
        }

        if (email.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_email));
            isValidationPass = false;
        }

        if (password.isEmpty()) {
            setErrorMessage(getString(R.string.str_please_enter_password));
            isValidationPass = false;
        } else {
            if (confirmPassword.isEmpty()) {
                setErrorMessage(getString(R.string.str_please_confirm_password));
                isValidationPass = false;
            } else if (!password.equals(confirmPassword)) {
                setErrorMessage(getString(R.string.str_password_did_not_match));
                isValidationPass = false;
            }
        }

        displayErrorMessage();
        return isValidationPass;
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
        if (pageAction == Action.REGISTER) {
            mRegistrationProgressBar.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.GONE);
            mSaveButton.setClickable(false);
            mBackButton.setClickable(false);
        } else if (pageAction == Action.BACK) {
            mRegistrationProgressBar.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.VISIBLE);
            mSaveButton.setClickable(false);
            mBackButton.setClickable(false);
        } else {
            mRegistrationProgressBar.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.VISIBLE);
            mSaveButton.setClickable(true);
            mBackButton.setClickable(true);
        }
    }

}