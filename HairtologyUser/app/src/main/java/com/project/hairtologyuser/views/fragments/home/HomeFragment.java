package com.project.hairtologyuser.views.fragments.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseError;
import com.google.gson.GsonBuilder;
import com.project.hairtologyuser.BuildConfig;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.databinding.FragmentHomeBinding;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;
import com.project.hairtologyuser.views.fragments.registration.RegistrationFragment;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".HOME_FRAGMENT";

    private HomeViewModel mViewModel;

    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        onButtonTap(R.id.stylingButton);
        onButtonTap(R.id.treatmentButton);
        onButtonTap(R.id.coloringButton);
        onButtonTap(R.id.relaxingButton);
        onButtonTap(R.id.haircutButton);
        onButtonTap(R.id.blowDryButton);
        onButtonTap(R.id.beardCutButton);
        onButtonTap(R.id.specialButton);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (getActivity() == null)
            return;

        mViewModel.setViewModel(getActivity().getApplication());
//        mViewModel.getReservationData(new HomeViewModel.onReservationListener() {
//            @Override
//            public void onReservationSuccess(List<ReservationModel> reservationList) {
//                if (reservationList.isEmpty()) {
//                    mReservationTextView.setText("You don't have reservation yet");
//                } else {
//                    StringBuilder strReservation = new StringBuilder("Reservations");
//                    for (ReservationModel reservation : reservationList) {
//                        strReservation.append("\n")
//                                .append(reservation.getDate()).append("     ")
//                                .append(reservation.getTime()).append("     ")
//                                .append(reservation.getNote());
//                    }
//                }
//            }
//
//            @Override
//            public void onReservationFailed(DatabaseError error) {
//
//            }
//        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onButtonTap(int id) {
        mView.findViewById(id).setOnClickListener(view -> {
            switch (id) {
                case R.id.stylingButton:
                    Log.e(getClass().getSimpleName(), "Styling");
                    break;
                case R.id.treatmentButton:
                    Log.e(getClass().getSimpleName(), "Treatment");
                    break;
                case R.id.coloringButton:
                    Log.e(getClass().getSimpleName(), "Coloring");
                    break;
                case R.id.relaxingButton:
                    Log.e(getClass().getSimpleName(), "Relaxing");
                    break;
                case R.id.haircutButton:
                    Log.e(getClass().getSimpleName(), "Haircut");
                    break;
                case R.id.blowDryButton:
                    Log.e(getClass().getSimpleName(), "Blow dry");
                    break;
                case R.id.beardCutButton:
                    Log.e(getClass().getSimpleName(), "beard cut");
                    break;
                case R.id.specialButton:
                    Log.e(getClass().getSimpleName(), "special");
                    break;
            }
        });
    }

    public void onReserveTap(String date, String time, String note) {
        mViewModel.reserve(date, time, note, new HomeViewModel.onReserveListener() {
            @Override
            public void onReserveSuccess(ReservationModel reservation) {
                Log.e(getClass().getSimpleName(), new GsonBuilder().create().toJson(reservation));
            }

            @Override
            public void onReserveFailed(Throwable throwable) {
                Log.e(getClass().getSimpleName(), String.valueOf(throwable));
            }

            @Override
            public void onReserveFailed(DatabaseError error) {
                Log.e(getClass().getSimpleName(), String.valueOf(error));
            }
        });
    }

}