package com.project.hairtologyuser.views.fragments.reservationlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;

import java.util.ArrayList;

public class ReservationListFragment extends BaseFragment {

    private ReservationListViewModel mViewModel;

    private View mView;

    private ReservationListAdapter mReservationListAdapter;

    public static ReservationListFragment newInstance() {
        return new ReservationListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reservation_list, container, false);

        ReservationModel reservationModel1 = new ReservationModel();
        reservationModel1.setDate("Wed");
        reservationModel1.setTime("1PM");
        reservationModel1.setNote("Cut");
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setDate("Mon");
        reservationModel1.setTime("2PM");
        reservationModel1.setNote("Trim");
        ReservationModel reservationModel3 = new ReservationModel();
        reservationModel1.setDate("Fri");
        reservationModel1.setTime("3PM");
        reservationModel1.setNote("Cut");

        ArrayList<ReservationModel> reservationList = new ArrayList<>();
        reservationList.add(reservationModel1);
        reservationList.add(reservationModel2);
        reservationList.add(reservationModel3);

        mReservationListAdapter = new ReservationListAdapter(getContext(), reservationList);

        RecyclerView recyclerView = mView.findViewById(R.id.reservationListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mReservationListAdapter);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationListViewModel.class);

        if (getActivity() == null)
            return;

        mViewModel.setViewModel(getActivity().getApplication());
        mViewModel.getReservation();
    }

}