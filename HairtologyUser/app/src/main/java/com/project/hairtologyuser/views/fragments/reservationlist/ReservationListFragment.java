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
import com.project.hairtologyuser.models.ServiceType;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;

import java.util.ArrayList;

public class ReservationListFragment extends BaseFragment {

    private ReservationListViewModel mViewModel;

    private View mView;

    private ReservationListAdapter mReservationListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reservation_list, container, false);

        ReservationModel reservationModel1 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.HAIRCUT);
        reservationModel1.setTime("6AM - 7AM");
        reservationModel1.setDay("13");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 1");
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.BEARD_CUT);
        reservationModel1.setTime("8AM - 9AM");
        reservationModel1.setDay("12");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 2");
        ReservationModel reservationModel3 = new ReservationModel();
        reservationModel1.setServiceType(ServiceType.RELAXING);
        reservationModel1.setTime("10AM - 11AM");
        reservationModel1.setDay("11");
        reservationModel1.setMonth("April 2023");
        reservationModel1.setNote("Note 3");

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
//        mViewModel.getReservation();
    }

}