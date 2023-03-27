package com.project.hairtologyuser.views.fragments.home;

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
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.project.hairtologyuser.BuildConfig;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.databinding.FragmentHomeBinding;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;
import com.project.hairtologyuser.views.fragments.registration.RegistrationFragment;

import java.util.List;

public class HomeFragment extends Fragment {

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".HOME_FRAGMENT";

    private HomeViewModel mViewModel;

    private View mView;

    private TextView mReservationTextView;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mReserveButton;
    private Button mLogoutButton;
    private EditText mNoteEditText;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mReservationTextView = mView.findViewById(R.id.reservationTextView);

        mReservationTextView = mView.findViewById(R.id.reservationTextView);
        mDateButton = mView.findViewById(R.id.dateButton);
        mTimeButton = mView.findViewById(R.id.timeButton);
        mNoteEditText = mView.findViewById(R.id.reservationNoteTextView);

        mDateButton.setOnClickListener(v -> {

        });

        mTimeButton.setOnClickListener(v -> {

        });

        mReserveButton.setOnClickListener(v -> {
            onReserveTap();
        });

        mLogoutButton.setOnClickListener(v -> {
            onLogoutTap();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (getActivity() == null)
            return;

        mViewModel.setViewModel(getActivity().getApplication());
        mViewModel.getReservationData(new HomeViewModel.onReservationListener() {
            @Override
            public void onReservationSuccess(List<ReservationModel> reservationList) {
                if (reservationList.isEmpty()) {
                    mReservationTextView.setText("You don't have reservation yet");
                } else {
                    StringBuilder strReservation = new StringBuilder("Reservations");
                    for (ReservationModel reservation : reservationList) {
                        strReservation.append("\n")
                                .append(reservation.getDate()).append("     ")
                                .append(reservation.getTime()).append("     ")
                                .append(reservation.getNote());
                    }
                }
            }

            @Override
            public void onReservationFailed(DatabaseError error) {

            }
        });
    }

    public void onDateTap() {

    }

    public void onTimeTap() {

    }

    public void onReserveTap() {
        String date = "";
        String time = "";
        String note = String.valueOf(mNoteEditText.getText());
        mViewModel.reserve(date, time, note, new HomeViewModel.onReserveListener() {
            @Override
            public void onReserveSuccess() {
                mViewModel.getReservationData(new HomeViewModel.onReservationListener() {
                    @Override
                    public void onReservationSuccess(List<ReservationModel> reservationList) {
                        if (reservationList.isEmpty()) {
                            mReservationTextView.setText("You don't have reservation yet");
                        } else {
                            StringBuilder strReservation = new StringBuilder("Reservations");
                            for (ReservationModel reservation : reservationList) {
                                strReservation.append("\n")
                                        .append(reservation.getDate()).append("     ")
                                        .append(reservation.getTime()).append("     ")
                                        .append(reservation.getNote());
                            }
                        }
                    }

                    @Override
                    public void onReservationFailed(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onReserveFailed() {

            }
        });
    }

    public void onLogoutTap() {
        if (getActivity() == null) {
            Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    HomeFragment.class
            ));
            return;
        }

        ((MainActivity) getActivity()).switchActivity(getContext(), OnBoardingActivity.class);
    }

}