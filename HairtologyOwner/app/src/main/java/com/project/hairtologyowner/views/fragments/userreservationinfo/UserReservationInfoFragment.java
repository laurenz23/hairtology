package com.project.hairtologyowner.views.fragments.userreservationinfo;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.components.utils.ToastMessage;
import com.project.hairtologyowner.models.ReservationModel;
import com.project.hairtologyowner.models.UserModel;
import com.project.hairtologyowner.models.UserReservationModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.reservationlist.ReservationListFragment;
import com.project.hairtologyowner.views.fragments.useraccountinfo.UserAccountInfoFragment;
import com.project.hairtologyowner.views.fragments.useraccountlist.UserAccountListFragment;
import com.project.hairtologyowner.views.fragments.userchat.UserChatFragment;

import java.util.Objects;

public class UserReservationInfoFragment extends Fragment {

    private final int mContainerViewId = R.id.onUserReservationInfoFragment;
    private static String mUserUuid;
    private static String mFirstName;
    private static String mLastName;
    private static ReservationModel mReservation;
    private UserReservationInfoViewModel mViewModel;
    private View mView;
    private FragmentContainerView mFragmentContainerView;
    private TextView mNameTextView;
    private ImageView mReservationImageView;
    private ImageView mMessageImageView;
    private ImageView mUserInfoImageView;

    public static UserReservationInfoFragment newInstance(String userUuid, String firstName, String lastName, ReservationModel reservation) {
        mUserUuid = userUuid;
        mFirstName = firstName;
        mLastName = lastName;
        mReservation = reservation;
        return new UserReservationInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_reservation_info, container, false);

        mNameTextView = mView.findViewById(R.id.nameUserReservationInfo);
        mReservationImageView = mView.findViewById(R.id.reservationUserReservationInfo);
        mMessageImageView = mView.findViewById(R.id.messageUserReservationInfo);
        mUserInfoImageView = mView.findViewById(R.id.infoUserReservationInfo);
        mFragmentContainerView = mView.findViewById(R.id.onUserReservationInfoFragment);

        mReservationImageView.setOnClickListener(view -> {
            mFragmentContainerView.removeAllViewsInLayout();
            ((MainActivity) getActivity()).replaceFragment(
                    ReservationListFragment.newInstance(mUserUuid),
                    mContainerViewId);
        });

        mMessageImageView.setOnClickListener(view -> {
            mFragmentContainerView.removeAllViewsInLayout();
            ((MainActivity) getActivity()).replaceFragment(
                    UserChatFragment.newInstance(mReservation.getUuid(), mUserUuid),
                    mContainerViewId);
        });

        mUserInfoImageView.setOnClickListener(view -> {
            mViewModel.viewUserInfo(mUserUuid, new UserReservationInfoViewModel.OnViewUserInfoListener() {
                @Override
                public void onSuccess(UserModel user) {
                    if (getActivity() == null) {
                        Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                UserReservationInfoFragment.class
                        ));
                        return;
                    }

                    ((MainActivity) getActivity()).replaceFragment(
                            UserAccountInfoFragment.newInstance(user),
                            MainActivity.containerViewId);
                }

                @Override
                public void onFailed(String errorMessage) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return mView;
    }

    @SuppressLint({"SetTextI18n", "CommitTransaction"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(UserReservationInfoViewModel.class);
        mViewModel.setViewModel(getContext());

        mFragmentContainerView.removeAllViewsInLayout();
        ((MainActivity) getActivity()).replaceFragment(
                ReservationListFragment.newInstance(mUserUuid),
                mContainerViewId);

        mNameTextView.setText(mFirstName + " " + mLastName);
    }

}