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
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.UserModel;

public class UserAccountInfoFragment extends Fragment {

    private UserAccountInfoViewModel mViewModel;
    private View mView;
    private ImageView mProfileImageView;
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

        return mView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserAccountInfoViewModel.class);

        mNameTextView.setText(mUser.getFirstName() + " " + mUser.getLastName());
        mUuidTextView.setText(mUser.getUuid());
        mEmailTextView.setText(mUser.getEmail());
    }

}