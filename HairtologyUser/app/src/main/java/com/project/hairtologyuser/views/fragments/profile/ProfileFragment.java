package com.project.hairtologyuser.views.fragments.profile;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.models.CountryDetails;
import com.project.hairtologyuser.models.UserModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.service.ServiceFragment;

public class ProfileFragment extends Fragment {
    private ProfileViewModel mViewModel;
    private Context mContext;
    private View mView;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mCurrentPasswordEditText;
    private EditText mNewPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private ConstraintLayout mProfileConstraintLayout;
    private ConstraintLayout mPasswordConstraintLayout;
    private TextInputLayout mCountryTextInputLayout;
    private AutoCompleteTextView mCountryAutoComplete;
    private ArrayAdapter<String> mCountryArrayAdapter;
    private String mSelectedCountry;

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getContext();

        mFirstNameEditText = mView.findViewById(R.id.firstNameEditText);
        mLastNameEditText = mView.findViewById(R.id.lastNameEditText);
        mEmailEditText = mView.findViewById(R.id.emailEditText);
        mCurrentPasswordEditText = mView.findViewById(R.id.currentPasswordEditText);
        mNewPasswordEditText = mView.findViewById(R.id.newPasswordEditText);
        mConfirmPasswordEditText = mView.findViewById(R.id.confirmPasswordEditText);
        mCountryTextInputLayout = mView.findViewById(R.id.countryTextInputLayout);
        mCountryAutoComplete = mView.findViewById(R.id.autoCompleteCountry);

        Button firstNameEditButton = mView.findViewById(R.id.firstNameEditButton);
        Button lastNameEditButton = mView.findViewById(R.id.lastNameEditButton);
        Button emailEditButton = mView.findViewById(R.id.emailEditButton);
        Button countryEditButton = mView.findViewById(R.id.countryEditButton);
        Button updateProfileButton = mView.findViewById(R.id.updateProfileButton);
        Button updatePasswordButton = mView.findViewById(R.id.updatePasswordButton);
        Button saveButton = mView.findViewById(R.id.saveButton);
        Button logoutButton = mView.findViewById(R.id.logoutButton);

        mProfileConstraintLayout = mView.findViewById(R.id.profileConstraintLayout);
        mPasswordConstraintLayout = mView.findViewById(R.id.passwordConstraintLayout);

        mCountryArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_country, CountryDetails.country);

        mCountryAutoComplete.setAdapter(mCountryArrayAdapter);
        mCountryAutoComplete.setOnItemClickListener((adapterView, view, i, l) -> {
            mSelectedCountry = adapterView.getItemAtPosition(i).toString();
        });

        mFirstNameEditText.setEnabled(false);
        mLastNameEditText.setEnabled(false);
        mEmailEditText.setEnabled(false);
        mCountryAutoComplete.setEnabled(false);

        firstNameEditButton.setOnClickListener(v -> {
            mFirstNameEditText.setEnabled(true);
        });

        lastNameEditButton.setOnClickListener(v -> {
            mLastNameEditText.setEnabled(true);
        });

        emailEditButton.setOnClickListener(v -> {
            mEmailEditText.setEnabled(true);
        });

        countryEditButton.setOnClickListener(v -> {
            mCountryAutoComplete.setEnabled(true);
            mCountryAutoComplete.setAdapter(mCountryArrayAdapter);
        });

        updateProfileButton.setOnClickListener(v -> {
            mFirstNameEditText.setText(mViewModel.getCurrentUser().getFirstName());
            mLastNameEditText.setText(mViewModel.getCurrentUser().getLastName());
            mEmailEditText.setText(mViewModel.getCurrentUser().getEmail());
            mCountryAutoComplete.setText(mViewModel.getCurrentUser().getCountry());
            mPasswordConstraintLayout.setVisibility(View.INVISIBLE);
            mProfileConstraintLayout.setVisibility(View.VISIBLE);
        });

        updatePasswordButton.setOnClickListener(v -> {
            mFirstNameEditText.setEnabled(false);
            mLastNameEditText.setEnabled(false);
            mEmailEditText.setEnabled(false);
            mCountryAutoComplete.setEnabled(false);
            mPasswordConstraintLayout.setVisibility(View.VISIBLE);
            mProfileConstraintLayout.setVisibility(View.INVISIBLE);
        });

        saveButton.setOnClickListener(v -> {
            onSaveProfile();
        });

        logoutButton.setOnClickListener(v -> {
            onLogoutTap();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.setModel(mContext);

        mFirstNameEditText.setText(mViewModel.getCurrentUser().getFirstName());
        mLastNameEditText.setText(mViewModel.getCurrentUser().getLastName());
        mEmailEditText.setText(mViewModel.getCurrentUser().getEmail());
        mCountryAutoComplete.setText(mViewModel.getCurrentUser().getCountry());
    }

    public void onSaveProfile() {
        String firstName = String.valueOf(mFirstNameEditText.getText());
        String lastName = String.valueOf(mLastNameEditText.getText());
        String email = String.valueOf(mEmailEditText.getText());

        mViewModel.updateProfile(firstName, lastName, email, mSelectedCountry, new ProfileViewModel.onUpdateProfileListener() {
            @Override
            public void onSuccess(UserModel user) {
                if (getActivity() == null) {
                    Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                            ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                            ProfileFragment.class
                    ));
                    return;
                }

                ((MainActivity) getActivity()).replaceFragment(
                        new ServiceFragment(),
                        MainActivity.containerViewId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.e(getClass().getSimpleName(), "" + throwable);
            }
        });
    }

    public void onSavePassword() {
        String currentPassword = String.valueOf(mCurrentPasswordEditText.getText());
        String newPassword = String.valueOf(mNewPasswordEditText.getText());
        String confirmPassword = String.valueOf(mConfirmPasswordEditText.getText());
    }

    public void onLogoutTap() {
        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    ServiceFragment.class
            ));
            return;
        }

        mViewModel.logout();
        ((MainActivity) getActivity()).switchActivity(getContext(), OnBoardingActivity.class);
    }

}