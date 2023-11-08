package com.project.hairtologyowner.views.fragments.login;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.hairtologyowner.BuildConfig;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.models.OwnerModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.activities.OnBoardingActivity;
import com.project.hairtologyowner.views.fragments.register.RegisterFragment;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private enum ActionType {
        RESET,
        LOGIN
    }

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".LOGIN_FRAGMENT";

    private LoginViewModel mViewModel;
    private EditText mEmailTextView;
    private EditText mPasswordTextView;
    private Button mLoginButton;
    private Button mRegisterButton;
    private ProgressBar mLoginProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailTextView = view.findViewById(R.id.loginEmailEditText);
        mPasswordTextView = view.findViewById(R.id.loginPasswordEditText);
        mLoginButton = view.findViewById(R.id.loginButton);
        mRegisterButton = view.findViewById(R.id.loginRegisterButton);
        mLoginProgressBar = view.findViewById(R.id.loginProgressBar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.viewModel(requireActivity().getApplication());

        mLoginButton.setOnClickListener(v -> {
            onLoginTap();
        });

        mRegisterButton.setOnClickListener(v -> {
            onRegisterTap();
        });

    }

    private void onLoginTap() {
        String email = String.valueOf(mEmailTextView.getText());
        String password = String.valueOf(mPasswordTextView.getText());

        if (email.isEmpty()) {
            return;
        }

        if (password.isEmpty()) {
            return;
        }

        pageAction(ActionType.LOGIN);

        mViewModel.login(email, password, new LoginViewModel.OnLoginListener() {
            @Override
            public void onSuccess(OwnerModel ownerModel) {
                pageAction(ActionType.RESET);
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
            public void onFailed(String error) {
                pageAction(ActionType.RESET);
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onRegisterTap() {
        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    LoginFragment.class
            ));

            pageAction(ActionType.RESET);
            return;
        }

        ((OnBoardingActivity) getActivity()).replaceFragment(
                new RegisterFragment(),
                OnBoardingActivity.containerViewId);
    }

    private void pageAction(ActionType actionType) {
        if (actionType == ActionType.LOGIN) {
            mLoginProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.GONE);
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