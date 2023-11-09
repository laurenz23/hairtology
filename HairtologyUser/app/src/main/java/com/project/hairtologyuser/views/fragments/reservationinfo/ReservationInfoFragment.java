package com.project.hairtologyuser.views.fragments.reservationinfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyuser.R;

public class ReservationInfoFragment extends Fragment {

    private ReservationInfoViewModel mViewModel;

    public static ReservationInfoFragment newInstance() {
        return new ReservationInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservation_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationInfoViewModel.class);
        mViewModel.setViewModel(getActivity().getApplication());
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