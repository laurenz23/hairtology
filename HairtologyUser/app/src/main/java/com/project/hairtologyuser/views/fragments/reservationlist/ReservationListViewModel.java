package com.project.hairtologyuser.views.fragments.reservationlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ReservationModel;

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
        reservationModel1.setDate("Wed");
        reservationModel1.setTime("1PM");
        reservationModel1.setNote("Cut");
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setDate("Mon");
        reservationModel1.setTime("2PM");
        reservationModel1.setNote("Trim");
        ReservationModel reservationModel3 = new ReservationModel();
        reservationModel1.setDate("Fri");
        reservationModel1.setTime("3PM");
        reservationModel1.setNote("Cut");

        ArrayList<ReservationModel> reservationList = new ArrayList<>();
        reservationList.add(reservationModel1);
        reservationList.add(reservationModel2);
        reservationList.add(reservationModel3);

        mReservationList.setValue(reservationList);
    }

}