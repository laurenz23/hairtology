package com.project.hairtologyuser.views.fragments.reservationinfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.userchat.UserChatFragment;

public class ReservationInfoFragment extends Fragment {

    private final int mContainerViewId = R.id.onUserReservationInfoFragment;
    private static ReservationModel mReservation;
    private ReservationInfoViewModel mViewModel;
    private View mView;
    private TextView mServiceNameTextView;
    private TextView mPriceTextView;
    private TextView mDayTextView;
    private TextView mTimeTextView;
    private TextView mMonthTextView;
    private TextView mDetailTextView;
    private Button mViewShopButton;
    private Button mCancelReservationButton;

    public static ReservationInfoFragment newInstance(ReservationModel reservation) {
        mReservation = reservation;
        return new ReservationInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reservation_info, container, false);

        mServiceNameTextView = mView.findViewById(R.id.reservationInfoNameTextView);
        mPriceTextView = mView.findViewById(R.id.priceTextView);
        mDayTextView = mView.findViewById(R.id.dayTextView);
        mTimeTextView = mView.findViewById(R.id.timeTextView);
        mMonthTextView = mView.findViewById(R.id.monthTextView);
        mDetailTextView = mView.findViewById(R.id.detailTextView);
        mViewShopButton = mView.findViewById(R.id.reservationInfoShopButton);
        mCancelReservationButton = mView.findViewById(R.id.reservationInfoCancelButton);

        mViewShopButton.setOnClickListener(view -> {

        });

        mCancelReservationButton.setOnClickListener(view -> {
            onCancelReservation();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationInfoViewModel.class);
        mViewModel.setViewModel(getActivity().getApplication());

        mServiceNameTextView.setText(mReservation.getServiceName());
        mPriceTextView.setText(mReservation.getPrice());
        mDayTextView.setText(mReservation.getDay());
        mTimeTextView.setText(mReservation.getTime());
        mMonthTextView.setText(mReservation.getMonth());
        mDetailTextView.setText(mReservation.getServiceDetail());

        ((MainActivity) getActivity()).replaceFragment(
                UserChatFragment.newInstance(mReservation.getUuid()),
                mContainerViewId);
    }


    private void onCancelReservation() {
//        ReservationModel reservation = mReservationArrayList.get(position);
//        mViewModel.cancelReservation(position, reservation, new ReservationListViewModel.onReservationCancellation() {
//            @Override
//            public void onSuccess(int position) {
//                if (getActivity() == null) {
//                    ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
//                            ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
//                            ReservationListFragment.class
//                    ));
//                    return;
//                }
//
//                ((MainActivity) getActivity()).replaceFragment(
//                        new ReservationFragment(),
//                        containerViewId);
//            }
//
//            @Override
//            public void onFailed(Exception exception) {
//                ToastMessage.display(getContext(), "Error: " + exception.getMessage());
//            }
//        });
    }

}