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
import com.project.hairtologyuser.models.ServiceType;
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
    private MutableLiveData<ServiceType> mSelectedServiceType;
    private MutableLiveData<String> mSelectedTime;
    private MutableLiveData<String> mSelectedDay;
    private MutableLiveData<String> mSelectedMonth;

    public void setViewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
        mSession = new Session(application.getApplicationContext());
        mCurrentUser = new MutableLiveData<>(mSession.getCurrentUser());
        mSelectedServiceType = new MutableLiveData<>(null);
        mSelectedTime = new MutableLiveData<>("");
        mSelectedDay = new MutableLiveData<>("");
        mSelectedMonth = new MutableLiveData<>("");
    }

    public MutableLiveData<UserModel> getCurrentUser() {
        return mCurrentUser;
    }

    public void setSelectedServiceType(ServiceType type) {
        mSelectedServiceType.setValue(type);
    }
    public MutableLiveData<ServiceType> getSelectedServiceType() {
        return mSelectedServiceType;
    }

    public void setSelectedTime(String time) {
        mSelectedTime.setValue(time);
    }
    public MutableLiveData<String> getSelectedTime() {
        return mSelectedTime;
    }

    public void setSelectedDay(String day) {
        mSelectedDay.setValue(day);
    }
    public MutableLiveData<String> getSelectedDay() {
        return mSelectedDay;
    }

    public void setSelectedMonth(String month) {
        mSelectedMonth.setValue(month);
    }
    public MutableLiveData<String> getSelectedMonth() {
        return mSelectedMonth;
    }

    public void getReservationData(onReservationListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiReservation(Objects.requireNonNull(mCurrentUser.getValue()).getUuid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ReservationModel> reservationList = new ArrayList<>();

//                        if (snapshot.exists()) {
//                            Log.e(getClass().getSimpleName(), "Existing");
//                        } else {
//                            Log.e(getClass().getSimpleName(), "Don't exist: " + snapshot.getValue());
//                        }
//
//                        int x = 0;
//                        for (DataSnapshot data : snapshot.getChildren()) {
//                            Log.e(getClass().getSimpleName(), "Working : " + x);
//                            reservationList.add(data.getValue(ReservationModel.class));
//                        }

                        listener.onReservationSuccess(reservationList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onReservationFailed(error);
                    }
                });
    }

    public void reserve(String note, onReserveListener listener) {
        if (getCurrentUser().getValue() == null)
            return;

        if (getSelectedServiceType().getValue() == null)
            return;

        if (Objects.requireNonNull(getSelectedTime().getValue()).isEmpty())
            return;

        if (Objects.requireNonNull(getSelectedDay().getValue()).isEmpty())
            return;

        if (Objects.requireNonNull(getSelectedMonth().getValue()).isEmpty())
            return;

        ReservationModel reservation = new ReservationModel(
                getSelectedServiceType().getValue(),
                getSelectedTime().getValue(),
                getSelectedDay().getValue(),
                getSelectedMonth().getValue(), note);

        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiReservation(getCurrentUser().getValue().getUuid()))
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long childCount = 0;

                    if (snapshot.exists()) {
                        childCount = snapshot.getChildrenCount();
                    }

                    mFirebaseClient.getDatabaseReference()
                        .child(mFirebaseClient.apiReservation(Objects.requireNonNull(mCurrentUser.getValue()).getUuid()))
                        .child(String.valueOf(childCount))
                        .setValue(reservation)
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