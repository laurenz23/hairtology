package com.project.hairtologyuser.views.fragments.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseError;
import com.google.gson.GsonBuilder;
import com.project.hairtologyuser.BuildConfig;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ServiceType;

public class ServiceFragment extends Fragment {

    public static final String FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".SERVICE_FRAGMENT";
    private ServiceViewModel mViewModel;
    private View mView;
    private ScrollView mHomeScrollView;
    private LinearLayout mSchedLinearLayout;
    private LinearLayout mSlotLinearLayout;
    private LinearLayout mSuccessfulReservedLinearLayout;
    private EditText mNoteEditText;
    private TextView mSched1TextView;
    private TextView mSched2TextView;
    private TextView mSched3TextView;
    private TextView mSched4TextView;
    private TextView mSched5TextView;
    private ImageView mBell1ImageView;
    private ImageView mBell2ImageView;
    private ImageView mBell3ImageView;
    private ImageView mBell4ImageView;
    private ImageView mBell5ImageView;
    private Button mSlotAMButton;
    private Button mSlotPMButton;
    private Button mSlot1Button;
    private Button mSlot2Button;
    private Button mSlot3Button;
    private Button mSlot4Button;
    private Button mSlot5Button;
    private Button mSlot6Button;
    private Button mReserveNowButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_service, container, false);

        mHomeScrollView = mView.findViewById(R.id.homeScrollView);
        mSchedLinearLayout = mView.findViewById(R.id.scheduleLinearLayout);
        mSlotLinearLayout = mView.findViewById(R.id.slotsLinearLayout);
        mSuccessfulReservedLinearLayout = mView.findViewById(R.id.successfulReservedLinearLayout);
        mNoteEditText = mView.findViewById(R.id.noteEditText);

        mSched1TextView = mView.findViewById(R.id.sched1TextView);
        mSched2TextView = mView.findViewById(R.id.sched2TextView);
        mSched3TextView = mView.findViewById(R.id.sched3TextView);
        mSched4TextView = mView.findViewById(R.id.sched4TextView);
        mSched5TextView = mView.findViewById(R.id.sched5TextView);

        mBell1ImageView = mView.findViewById(R.id.sched1Bell);
        mBell2ImageView = mView.findViewById(R.id.sched2Bell);
        mBell3ImageView = mView.findViewById(R.id.sched3Bell);
        mBell4ImageView = mView.findViewById(R.id.sched4Bell);
        mBell5ImageView = mView.findViewById(R.id.sched5Bell);

        mSlotAMButton = mView.findViewById(R.id.slotAMButton);
        mSlotPMButton = mView.findViewById(R.id.slotPMButton);
        mSlot1Button = mView.findViewById(R.id.slot1Button);
        mSlot2Button = mView.findViewById(R.id.slot2Button);
        mSlot3Button = mView.findViewById(R.id.slot3Button);
        mSlot4Button = mView.findViewById(R.id.slot4Button);
        mSlot5Button = mView.findViewById(R.id.slot5Button);
        mSlot6Button = mView.findViewById(R.id.slot6Button);

        mReserveNowButton = mView.findViewById(R.id.reserveNowButton);

        onServiceTap(R.id.stylingButton);
        onServiceTap(R.id.treatmentButton);
        onServiceTap(R.id.coloringButton);
        onServiceTap(R.id.relaxingButton);
        onServiceTap(R.id.haircutButton);
        onServiceTap(R.id.blowDryButton);
        onServiceTap(R.id.beardCutButton);
        onServiceTap(R.id.specialButton);

        onSchedTap(R.id.sched1Button);
        onSchedTap(R.id.sched2Button);
        onSchedTap(R.id.sched3Button);
        onSchedTap(R.id.sched4Button);
        onSchedTap(R.id.sched5Button);

        onSlotTap(mSlotAMButton);
        onSlotTap(mSlotPMButton);
        onSlotTap(mSlot1Button);
        onSlotTap(mSlot2Button);
        onSlotTap(mSlot3Button);
        onSlotTap(mSlot4Button);
        onSlotTap(mSlot5Button);
        onSlotTap(mSlot6Button);

        mSchedLinearLayout.setVisibility(View.GONE);
        mSlotLinearLayout.setVisibility(View.GONE);
        mNoteEditText.setVisibility(View.GONE);
        mReserveNowButton.setVisibility(View.GONE);
        mSuccessfulReservedLinearLayout.setVisibility(View.GONE);

        mSched1TextView.setText("10");
        mSched2TextView.setText("11");
        mSched3TextView.setText("12");
        mSched4TextView.setText("13");
        mSched5TextView.setText("14");

        mReserveNowButton.setOnClickListener(view -> {
            onReserveTap();
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);

        if (getActivity() == null)
            return;

        mViewModel.setViewModel(getActivity().getApplication());
        mViewModel.setSelectedMonth("April 2023");
//        mViewModel.getReservationData(new ServiceViewModel.onReservationListener() {
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
    public void onServiceTap(int id) {
        mView.findViewById(id).setOnClickListener(view -> {
            mSchedLinearLayout.setVisibility(View.VISIBLE);
            switch (id) {
                case R.id.stylingButton:
                    mViewModel.setSelectedServiceType(ServiceType.STYLING);
                    Log.e(getClass().getSimpleName(), "Styling");
                    break;
                case R.id.treatmentButton:
                    mViewModel.setSelectedServiceType(ServiceType.TREATMENT);
                    Log.e(getClass().getSimpleName(), "Treatment");
                    break;
                case R.id.coloringButton:
                    mViewModel.setSelectedServiceType(ServiceType.COLORING);
                    Log.e(getClass().getSimpleName(), "Coloring");
                    break;
                case R.id.relaxingButton:
                    mViewModel.setSelectedServiceType(ServiceType.RELAXING);
                    Log.e(getClass().getSimpleName(), "Relaxing");
                    break;
                case R.id.haircutButton:
                    mViewModel.setSelectedServiceType(ServiceType.HAIRCUT);
                    Log.e(getClass().getSimpleName(), "Haircut");
                    break;
                case R.id.blowDryButton:
                    mViewModel.setSelectedServiceType(ServiceType.BLOW_DRY);
                    Log.e(getClass().getSimpleName(), "Blow dry");
                    break;
                case R.id.beardCutButton:
                    mViewModel.setSelectedServiceType(ServiceType.BEARD_CUT);
                    Log.e(getClass().getSimpleName(), "beard cut");
                    break;
                case R.id.specialButton:
                    mViewModel.setSelectedServiceType(ServiceType.SPECIAL);
                    Log.e(getClass().getSimpleName(), "special");
                    break;
            }

            mHomeScrollView.post(() -> mHomeScrollView.fullScroll(View.FOCUS_DOWN));
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onSchedTap(int id) {
        mView.findViewById(id).setOnClickListener(view -> {
            mSlotLinearLayout.setVisibility(View.VISIBLE);
            mBell1ImageView.setVisibility(View.GONE);
            mBell2ImageView.setVisibility(View.GONE);
            mBell3ImageView.setVisibility(View.GONE);
            mBell4ImageView.setVisibility(View.GONE);
            mBell5ImageView.setVisibility(View.GONE);

            switch (id) {
                case R.id.sched1Button:
                    mViewModel.setSelectedDay(String.valueOf(mSched1TextView.getText()));
                    mBell1ImageView.setVisibility(View.VISIBLE);
                    break;
                case R.id.sched2Button:
                    mViewModel.setSelectedDay(String.valueOf(mSched2TextView.getText()));
                    mBell2ImageView.setVisibility(View.VISIBLE);
                    break;
                case R.id.sched3Button:
                    mViewModel.setSelectedDay(String.valueOf(mSched3TextView.getText()));
                    mBell3ImageView.setVisibility(View.VISIBLE);
                    break;
                case R.id.sched4Button:
                    mViewModel.setSelectedDay(String.valueOf(mSched4TextView.getText()));
                    mBell4ImageView.setVisibility(View.VISIBLE);
                    break;
                case R.id.sched5Button:
                    mViewModel.setSelectedDay(String.valueOf(mSched5TextView.getText()));
                    mBell5ImageView.setVisibility(View.VISIBLE);
                    break;
            }

            mHomeScrollView.post(() -> mHomeScrollView.fullScroll(View.FOCUS_DOWN));
        });
    }

    public void onSlotTap(Button button) {
        button.setOnClickListener(view -> {
            if (button == mSlotAMButton) {
                setAMSlot();
            } else if (button == mSlotPMButton) {
                setPMSlot();
            } else {
                mViewModel.setSelectedTime(String.valueOf(button.getText()));
                mNoteEditText.setVisibility(View.VISIBLE);
                mReserveNowButton.setVisibility(View.VISIBLE);
            }

            mHomeScrollView.post(() -> mHomeScrollView.fullScroll(View.FOCUS_DOWN));
        });
    }

    public void onReserveTap() {
//        mViewModel.reserve(String.valueOf(mNoteEditText.getText()), new ServiceViewModel.onReserveListener() {
//            @Override
//            public void onReserveSuccess(ReservationModel reservation) {
//                mSchedLinearLayout.setVisibility(View.GONE);
//                mSlotLinearLayout.setVisibility(View.GONE);
//                mNoteEditText.setVisibility(View.GONE);
//                mReserveNowButton.setVisibility(View.GONE);
//                mSuccessfulReservedLinearLayout.setVisibility(View.VISIBLE);
//
//                mBell1ImageView.setVisibility(View.GONE);
//                mBell2ImageView.setVisibility(View.GONE);
//                mBell3ImageView.setVisibility(View.GONE);
//                mBell4ImageView.setVisibility(View.GONE);
//                mBell5ImageView.setVisibility(View.GONE);
//
//                Log.e(getClass().getSimpleName(), new GsonBuilder().create().toJson(reservation));
//                new Handler().postDelayed(() -> mSuccessfulReservedLinearLayout.setVisibility(View.GONE), 3000);
//            }
//
//            @Override
//            public void onReserveFailed(Throwable throwable) {
//                Log.e(getClass().getSimpleName(), String.valueOf(throwable));
//            }
//
//            @Override
//            public void onReserveFailed(DatabaseError error) {
//                Log.e(getClass().getSimpleName(), String.valueOf(error));
//            }
//        });
    }

    public void setAMSlot() {
        mSlot1Button.setText(getString(R.string.str_time_6_7));
        mSlot2Button.setText(getString(R.string.str_time_7_8));
        mSlot3Button.setText(getString(R.string.str_time_8_9));
        mSlot4Button.setText(getString(R.string.str_time_9_10));
        mSlot5Button.setText(getString(R.string.str_time_10_11));
        mSlot6Button.setText(getString(R.string.str_time_11_12));
    }

    public void setPMSlot() {
        mSlot1Button.setText(getString(R.string.str_time_12_1));
        mSlot2Button.setText(getString(R.string.str_time_1_2));
        mSlot3Button.setText(getString(R.string.str_time_2_3));
        mSlot4Button.setText(getString(R.string.str_time_3_4));
        mSlot5Button.setText(getString(R.string.str_time_4_5));
        mSlot6Button.setText(getString(R.string.str_time_5_6));
    }

}