package com.project.hairtologyowner.views.fragments.userchat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyowner.R;

public class UserChatFragment extends Fragment {

    private static String mUuid;
    private static String mFirstName;
    private static String mLastName;
    private UserChatViewModel mViewModel;
    private View mView;

    public static UserChatFragment newInstance(String uuid, String firstName, String lastName) {
        mUuid = uuid;
        mFirstName = firstName;
        mLastName = lastName;
        return new UserChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_chat, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserChatViewModel.class);
    }

}