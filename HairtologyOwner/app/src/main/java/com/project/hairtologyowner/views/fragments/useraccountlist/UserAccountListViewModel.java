package com.project.hairtologyowner.views.fragments.useraccountlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyowner.components.client.FirebaseClient;
import com.project.hairtologyowner.models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class UserAccountListViewModel extends ViewModel {

    public interface OnUserAccountDataListener {
        void onSuccess(ArrayList<UserModel> user);
        void onFailed(String errorMessage);
    }

    private FirebaseClient mFirebaseClient;

    public void viewModel(@NonNull Application application) {
        mFirebaseClient = new FirebaseClient(application);
    }

    public void getUserAccountList(UserAccountListViewModel.OnUserAccountDataListener listener) {
        mFirebaseClient.getDatabaseReference()
                .child(mFirebaseClient.apiUsers())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<UserModel> userArrayList = new ArrayList<>();
                        for (DataSnapshot userList : snapshot.getChildren()) {

                            for (DataSnapshot userData : userList.getChildren()) {
                                if (Objects.equals(userData.getKey(), "info")) {
                                    UserModel userInfo = userData.getValue(UserModel.class);
                                    if (userInfo != null) {
                                        userArrayList.add(userInfo);
                                    }
                                }
                            }
                        }

                        listener.onSuccess(userArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailed(error.getMessage());
                    }
                });
    }

}