package com.project.hairtologyuser.views.fragments.reservationlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class ReservationListViewModel extends ViewModel {

    private FirebaseClient mFirebaseClient;

    private MutableLiveData<ArrayList<ReservationModel>> mReservationList;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mReservationList = new MutableLiveData<>(null);
    }

    public MutableLiveData<ArrayList<ReservationModel>> getReservationList() {
        return mReservationList;
    }

    public void getReservation() {
        ReservationModel reservationModel1 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.HAIRCUT);
        reservationModel1.setTime("6AM - 7AM");
        reservationModel1.setDay("13");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 1");
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.BEARD_CUT);
        reservationModel1.setTime("8AM - 9AM");
        reservationModel1.setDay("12");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 2");
        ReservationModel reservationModel3 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.RELAXING);
        reservationModel1.setTime("10AM - 11AM");
        reservationModel1.setDay("11");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 3");

        ArrayList<ReservationModel> reservationList = new ArrayList<>();
        reservationList.add(reservationModel1);
        reservationList.add(reservationModel2);
        reservationList.add(reservationModel3);

        mReservationList.setValue(reservationList);
    }

}