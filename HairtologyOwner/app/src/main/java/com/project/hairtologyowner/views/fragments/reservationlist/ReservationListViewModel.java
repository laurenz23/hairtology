package com.project.hairtologyowner.views.fragments.reservationlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.components.repository.Session;
import com.project.hairtologyowner.models.ReservationModel;

import java.util.ArrayList;

public class ReservationListViewModel extends ViewModel {

    public interface onReservationFetch {
        void onSuccess(ArrayList<ReservationModel> reservationList);
        void onFailed(DatabaseError error);
    }

    public interface onReservationCancellation {
        void onSuccess(int position);
        void onFailed(Exception exception);
    }

    private Session mSession;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        mSession = new Session(application.getApplicationContext());
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getReservation(String userUuid, onReservationFetch listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(userUuid))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ReservationModel> reservationList = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ReservationModel reservation = data.getValue(ReservationModel.class);
                            reservationList.add(reservation);
                        }

                        listener.onSuccess(reservationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailed(error);
                    }
                });
    }

    public void cancelReservation(int position, ReservationModel reservation, onReservationCancellation listener) {
//        UserModel currentUser = mSession.getCurrentUser();
//        if (currentUser == null)
//            return;
//
//        reservation.setCancelled(true);
//
//        mFirebaseClient.getDatabaseReference()
//            .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
//            .child(String.valueOf(position))
//            .removeValue()
//            .addOnSuccessListener(s -> {
//                mFirebaseClient.getDatabaseReference()
//                    .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
//                    .child(String.valueOf(position))
//                    .setValue(reservation)
//                    .addOnSuccessListener(success -> {
//                        listener.onSuccess(position);
//                    }).addOnFailureListener(listener::onFailed);
//            }).addOnFailureListener(listener::onFailed);

    }

}