package com.project.hairtologyowner.views.fragments.useraccountinfo;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.app.AppConfig;
import com.project.hairtologyowner.components.utils.ToastMessage;
import com.project.hairtologyowner.models.UserModel;

public class UserAccountInfoFragment extends Fragment {

    private UserAccountInfoViewModel mViewModel;
    private View mView;
    private ImageView mProfileImageView;
    private Button mEnableButton;
    private Button mDisableButton;
    private TextView mNameTextView;
    private TextView mUuidTextView;
    private TextView mEmailTextView;
    private static UserModel mUser;

    public static UserAccountInfoFragment newInstance(UserModel user) {
        mUser = user;
        return new UserAccountInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_account_info, container, false);

        mProfileImageView = mView.findViewById(R.id.profileImageUserAccountInfo);
        mNameTextView = mView.findViewById(R.id.nameUserAccountInfo);
        mUuidTextView = mView.findViewById(R.id.uuidUserAccountInfo);
        mEmailTextView = mView.findViewById(R.id.emailUserAccountInfo);
        mDisableButton = mView.findViewById(R.id.disableButtonUserAccountInfo);
        mEnableButton = mView.findViewById(R.id.enableButtonUserAccountInfo);

        mDisableButton.setOnClickListener(view -> disableUserAccount(mUser, true));
        mEnableButton.setOnClickListener(view -> disableUserAccount(mUser, false));

        return mView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(UserAccountInfoViewModel.class);
        mViewModel.setViewModel(getContext());

        mNameTextView.setText(mUser.getFirstName() + " " + mUser.getLastName());
        mUuidTextView.setText(mUser.getUuid());
        mEmailTextView.setText(mUser.getEmail());

        updateUIEnableDisableButton(mUser.getAccountDisabled());
    }

    private void disableUserAccount(UserModel user, Boolean isDisabled) {
        mViewModel.disableUserAccount(user, isDisabled, new UserAccountInfoViewModel.onDisableUserListener() {
            @Override
            public void onSuccess(UserModel user) {
                updateUIEnableDisableButton(isDisabled);
                String successMessage;
                if (isDisabled) {
                    successMessage = getString(R.string.str_successfully_disabled_user) + " " + user.getEmail();
                } else {
                    successMessage = getString(R.string.str_successfully_enabled_user) + " " + user.getEmail();
                }

                Toast.makeText(getContext(), successMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                updateUIEnableDisableButton(isDisabled);
                Toast.makeText(getContext(),
                        getString(R.string.str_encountered_an_error_disabling_user) + " " + mUser.getEmail(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUIEnableDisableButton(Boolean isDisabled) {
        if (isDisabled) {
            mEnableButton.setVisibility(View.VISIBLE);
            mDisableButton.setVisibility(View.GONE);
        } else {
            mEnableButton.setVisibility(View.GONE);
            mDisableButton.setVisibility(View.VISIBLE);
        }
    }

}