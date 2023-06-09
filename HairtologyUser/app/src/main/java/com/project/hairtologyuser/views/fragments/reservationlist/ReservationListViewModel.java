package com.project.hairtologyuser.views.fragments.reservationlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ServiceType;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private MutableLiveData<ArrayList<ReservationModel>> mReservationList;

    public void setViewModel(@NonNull Application application) {
        mSession = new Session(application.getApplicationContext());
        mFirebaseClient = new FirebaseClient(application);
        mReservationList = new MutableLiveData<>(null);
    }
    public MutableLiveData<ArrayList<ReservationModel>> getReservationList() {
        return mReservationList;
    }

    public void getReservation(onReservationFetch listener) {
        if (mSession.getCurrentUser() == null) {
            return;
        }

        UserModel currentUser = mSession.getCurrentUser();
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ReservationModel> reservationList = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ReservationModel reservation = data.getValue(ReservationModel.class);
                            reservationList.add(reservation);
                        }

                        mReservationList.setValue(reservationList);
                        listener.onSuccess(mReservationList.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailed(error);
                    }
                });
    }

    public void cancelReservation(int position, onReservationCancellation listener) {
        UserModel currentUser = mSession.getCurrentUser();
        if (currentUser == null)
            return;

        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                .child("" + position)
                .removeValue()
                .addOnSuccessListener(unused -> listener.onSuccess(position))
                .addOnFailureListener(listener::onFailed);
    }

}