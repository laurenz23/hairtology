package com.project.hairtologyuser.views.fragments.home;

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
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends ViewModel {

    public interface onReservationListener {
        void onReservationSuccess(List<ReservationModel> reservationList);
        void onReservationFailed(DatabaseError error);
    }

    public interface onReserveListener {
        void onReserveSuccess(ReservationModel reservation);
        void onReserveFailed(Throwable throwable);
        void onReserveFailed(DatabaseError error);

    }

    private FirebaseClient mFirebaseClient;

    private Session mSession;

    private MutableLiveData<UserModel> mCurrentUser;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mSession = new Session(application.getApplicationContext());
        mCurrentUser = new MutableLiveData<>(mSession.getCurrentUser());
    }

    public MutableLiveData<UserModel> getCurrentUser() {
        return mCurrentUser;
    }

    public void getReservationData(onReservationListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(Objects.requireNonNull(mCurrentUser.getValue()).getUuid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ReservationModel> reservationList = new ArrayList<>();

                        if (snapshot.exists()) {
                            Log.e(getClass().getSimpleName(), "Existing");
                        } else {
                            Log.e(getClass().getSimpleName(), "Don't exist: " + snapshot.getValue());
                        }

                        int x = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Log.e(getClass().getSimpleName(), "Working : " + x);
                            reservationList.add(data.getValue(ReservationModel.class));
                        }

                        listener.onReservationSuccess(reservationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onReservationFailed(error);
                    }
                });
    }

    public void reserve(String date, String time, String note, onReserveListener listener) {
        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiReservation(Objects.requireNonNull(mCurrentUser.getValue()).getUuid()))
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long childCount = 0;

                    if (snapshot.exists()) {
                        childCount = snapshot.getChildrenCount();
                    }

                    ReservationModel reservation = new ReservationModel(date, time, note);

                    mFirebaseClient.getDatabaseReference()
                        .child(mFirebaseClient.apiReservation(Objects.requireNonNull(mCurrentUser.getValue()).getUuid()))
                        .child(String.valueOf(childCount))
                        .setValue(new ReservationModel(date, time, note))
                        .addOnSuccessListener(data -> {
                            listener.onReserveSuccess(reservation);
                        }).addOnFailureListener(listener::onReserveFailed);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onReserveFailed(error);
                }
            });
    }

}