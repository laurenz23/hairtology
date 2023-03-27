package com.project.hairtologyuser.views.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.Date;
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

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getReservationData(onReservationListener listener) {
        String uuid = "CNa5GGSMPYRea9sQt0mvkLjuvdN2";
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(uuid))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ReservationModel> reservationList = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ReservationModel reservation = data.getValue(ReservationModel.class);
                            reservationList.add(reservation);
                        }
                        listener.onReservationSuccess(reservationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onReservationFailed(error);
                    }
                });
    }

    public void reserve(String date, String time, String notes, onReserveListener listener) {
        String uuid = "CNa5GGSMPYRea9sQt0mvkLjuvdN2";
        ReservationModel reservation = new ReservationModel();
        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setNote(notes);
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiInfo(uuid))
                .setValue(reservation)
                .addOnSuccessListener(unused -> {
                    listener.onReserveSuccess();
                }).addOnFailureListener(throwable -> {
                    listener.onReserveFailed();
                });
    }

}