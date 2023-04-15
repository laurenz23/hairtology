package com.project.hairtologyuser.views.fragments.reservationlist;

import static com.project.hairtologyuser.views.activities.MainActivity.containerViewId;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ServiceType;
import com.project.hairtologyuser.models.UserModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.activities.OnBoardingActivity;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;
import com.project.hairtologyuser.views.fragments.login.LoginFragment;
import com.project.hairtologyuser.views.fragments.reserve.ReserveFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ReservationListFragment extends BaseFragment {

    private ReservationListViewModel mViewModel;
    private View mView;
    private Session mSession;
    private FirebaseClient mFirebaseClient;
    private ReservationListAdapter mReservationListAdapter;
    private ArrayList<ReservationModel> mReservationArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reservation_list, container, false);

        mSession = new Session(getActivity().getApplication());
        mFirebaseClient = new FirebaseClient(getActivity().getApplication());
        mReservationArrayList = new ArrayList<>();

        mReservationListAdapter = new ReservationListAdapter(getContext(), mReservationArrayList);
        mReservationListAdapter.setOnReservationClickListener(position -> {
            mViewModel.cancelReservation(position, new ReservationListViewModel.onReservationCancellation() {
                @Override
                public void onSuccess(int position) {
                    if (getActivity() == null) {
                        Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                ReservationListFragment.class
                        ));
                        return;
                    }

                    ((MainActivity) getActivity()).replaceFragment(
                            new ReserveFragment(),
                            containerViewId);

//                    mReservationArrayList.remove(position);
//                    mReservationListAdapter.notifyItemRemoved(position);
                }

                @Override
                public void onFailed(Exception exception) {
                    Log.e(getClass().getSimpleName(), "" + exception);
                }
            });
        });

        RecyclerView recyclerView = mView.findViewById(R.id.reservationListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mReservationListAdapter);

        if (mSession.getCurrentUser() != null) {
            UserModel currentUser = mSession.getCurrentUser();
            mFirebaseClient.getDatabaseReference()
                    .child(mFirebaseClient.apiReservation(currentUser.getUuid()))
                    .addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<ReservationModel> reservationList = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                ReservationModel reservation = data.getValue(ReservationModel.class);
                                reservationList.add(reservation);
                            }
                            mReservationArrayList.addAll(reservationList);
                            mReservationListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(getClass().getSimpleName(), "" + error);
                        }
                    });
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationListViewModel.class);

        if (getActivity() == null)
            return;

        mViewModel.setViewModel(getActivity().getApplication());
    }

}