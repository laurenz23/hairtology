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
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.userchat.UserChatFragment;

public class ReservationInfoFragment extends Fragment {

    private final int mContainerViewId = R.id.onUserReservationInfoFragment;
    private ReservationInfoViewModel mViewModel;
    private View mView;

    public static ReservationInfoFragment newInstance() {
        return new ReservationInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reservation_info, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationInfoViewModel.class);
        mViewModel.setViewModel(getActivity().getApplication());

        ((MainActivity) getActivity()).replaceFragment(
                UserChatFragment.newInstance("c7cec3cf-0e6b-4c75-856c-04cf2e2d1639"),
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