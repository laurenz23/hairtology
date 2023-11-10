package com.project.hairtologyuser.views.fragments.reservationinfo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;

public class ReservationInfoViewModel extends ViewModel {

    public interface onReservationCancellation {
        void onSuccess(int position);
        void onFailed(Exception exception);
    }

    private Context mContext;
    private Session mSession;
    private FirebaseClient mFirebaseClient;

    public void setViewModel(@NonNull Application application) {
        mContext = application.getApplicationContext();
        mSession = new Session(mContext);
        mFirebaseClient = new FirebaseClient(application);
    }

    public UserModel getCurrentUser() {
        return mSession.getCurrentUser();
    }

    public void cancelReservation(int position, ReservationModel reservation, ReservationInfoViewModel.onReservationCancellation listener) {
        UserModel currentUser = mSession.getCurrentUser();
        if (currentUser == null)
            return;

        reservation.setCancelled(true);

        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .child(String.valueOf(position))
                .removeValue()
                .addOnSuccessListener(s -> {
                    mFirebaseClient.getDatabaseReference()
                            .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                            .child(String.valueOf(position))
                            .setValue(reservation)
                            .addOnSuccessListener(success -> {
                                listener.onSuccess(position);
                            }).addOnFailureListener(listener::onFailed);
                }).addOnFailureListener(listener::onFailed);
    }
}