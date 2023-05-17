package com.project.hairtologyowner.views.fragments.userreservationlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.ReservationModel;
import com.project.hairtologyowner.models.UserModel;
import com.project.hairtologyowner.models.UserReservationModel;

import java.util.ArrayList;
import java.util.Objects;

public class UserReservationListViewModel extends ViewModel {

    public interface OnUserReservationDataListener {
        void onSuccess(ArrayList<UserReservationModel> userReservation);
        void onFailed(String errorMessage);
    }

    private FirebaseClient mFirebaseClient;

    public void viewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getUserReservation(OnUserReservationDataListener listener) {
        mFirebaseClient.getDatabaseReference()
            .child(mFirebaseClient.apiUsers())
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<UserReservationModel> userReservationList = new ArrayList<>();
                    for (DataSnapshot userList : snapshot.getChildren()) {
                        UserReservationModel userReservation = new UserReservationModel();

                        for (DataSnapshot userData : userList.getChildren()) {
                            if (Objects.equals(userData.getKey(), "info")) {
                                UserModel user = userData.getValue(UserModel.class);
                                if (user != null) {
                                    userReservation.setUserUuid(user.getUuid());
                                    userReservation.setUserFirstName(user.getFirstName());
                                    userReservation.setUserLastName(user.getLastName());
                                }
                            } else if (Objects.equals(userData.getKey(), "reservation")) {
                                ArrayList<ReservationModel> reservationList = new ArrayList<>();
                                for (DataSnapshot reservationDataList : userData.getChildren()) {
                                    reservationList.add(reservationDataList.getValue(ReservationModel.class));
                                }
                                userReservation.setReservationList(reservationList);
                            }
                        }

                        if (userReservation.getReservationList() != null) {
                            userReservationList.add(userReservation);
                        }
                    }

                    listener.onSuccess(userReservationList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    listener.onFailed(error.getMessage());
                }
            });
    }

}