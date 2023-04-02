package com.project.hairtologyuser.views.fragments.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    public interface onReservationListener {
        void onReservationSuccess(List<ReservationModel> reservationList);
        void onReservationFailed(DatabaseError error);
    }

    public interface onReserveListener {
        void onReserveSuccess();
        void onReserveFailed();
    }

    private FirebaseClient mFirebaseClient;

    private Session mSession;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mSession = new Session(application.getApplicationContext());
    }

    public void getReservationData(onReservationListener listener) {
        UserModel currentUser = mSession.getCurrentUser();
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ReservationModel> reservationList = new ArrayList<>();
                        ReservationModel reservation = snapshot.getValue(ReservationModel.class);
                        Log.e(getClass().getSimpleName(), reservation.getDate());
                        Log.e(getClass().getSimpleName(), reservation.getTime());
                        Log.e(getClass().getSimpleName(), reservation.getNote());
                        listener.onReservationSuccess(reservationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onReservationFailed(error);
                    }
                });
    }

    public void reserve(String date, String time, String notes, onReserveListener listener) {
        UserModel currentUser = mSession.getCurrentUser();
        ReservationModel reservation = new ReservationModel();
        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setNote(notes);
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .setValue(reservation)
                .addOnSuccessListener(unused -> {
                    listener.onReserveSuccess();
                }).addOnFailureListener(throwable -> {
                    listener.onReserveFailed();
                });
    }

}