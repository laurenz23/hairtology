package com.project.myapplication.views.fragments.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.project.myapplication.R;

import java.util.Calendar;

public class ReservationFragment extends Fragment {

    private ReservationViewModel mViewModel;

    private View mView;

    private AutoCompleteTextView mShopAutoCompleteTextView;

    private ArrayAdapter mShopArrayAdapter;

    private EditText mDateTextInputLayout;

    private EditText mTimeTextInputLayout;

    public static ReservationFragment newInstance() {
        return new ReservationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation, container, false);
//        mBinding.setLifecycleOwner(getViewLifecycleOwner());
//        mView = mBinding.getRoot();
//
//        mShopAutoCompleteTextView = mBinding.shopAutoCompleteTextView;
//        mDateTextInputLayout = mBinding.dateEditText;
//        mTimeTextInputLayout = mBinding.timeEditText;
//
//        return mView;

        return inflater.inflate(R.layout.fragment_reserve, container, false);
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
//        mBinding.setViewModel(mViewModel);
//        mBinding.setFragment(this);
//        mViewModel.setShop();
//    }
//
//    public void onDateTap() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int date = calendar.get(Calendar.DATE);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                String datePicked = date + "/" + (month + 1) + "/" + year;
//                mDateTextInputLayout.setText(datePicked);
//                Log.e(getClass().getSimpleName(), "Date Picked: " + datePicked);
//            }
//        }, year, month, date);
//        datePickerDialog.show();
//    }
//
//    public void onTimeTap() {
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR);
//        int min = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
//            String timePicked = hour + "hr " + min + "min";
//            mTimeTextInputLayout.setText(timePicked);
//            Log.e(getClass().getSimpleName(), "Time Picked: " + timePicked);
//        }, hour, min, true);
//        timePickerDialog.show();
//    }

}