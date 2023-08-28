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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.components.utils.ToastMessage;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.base.BaseFragment;
import com.project.hairtologyuser.views.fragments.reservation.ReservationFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ReservationListFragment extends BaseFragment {

    private ReservationListViewModel mViewModel;
    private LinearLayout mReservationLoadingLinearLayout;
    private RecyclerView mReservationRecyclerView;
    private ReservationListAdapter mReservationListAdapter;
    private ArrayList<ReservationModel> mReservationArrayList;
    private TextView mNoReservationYetTextView;

    @SuppressLint("CutPasteId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_list, container, false);

        mNoReservationYetTextView = view.findViewById(R.id.reservationNoReservationYetTextView);
        mReservationRecyclerView = view.findViewById(R.id.reservationListItem);
        mReservationLoadingLinearLayout = view.findViewById(R.id.reservationLoadingLinearLayout);
        mReservationArrayList = new ArrayList<>();

        mReservationListAdapter = new ReservationListAdapter(getContext(), mReservationArrayList);
        mReservationListAdapter.onReservationTapListener(new ReservationListAdapter.OnReservationTapListener() {
            @Override
            public void onReservationTap(int position) {
                Log.e("Working", "Position: " + position);
            }

            @Override
            public void onReservationCancel(int position) {
                ReservationModel reservation = mReservationArrayList.get(position);
                mViewModel.cancelReservation(position, reservation, new ReservationListViewModel.onReservationCancellation() {
                    @Override
                    public void onSuccess(int position) {
                        if (getActivity() == null) {
                            ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                    ReservationListFragment.class
                            ));
                            return;
                        }

                        ((MainActivity) getActivity()).replaceFragment(
                                new ReservationFragment(),
                                containerViewId);
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        ToastMessage.display(getContext(), "Error: " + exception.getMessage());
                    }
                });
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.reservationListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mReservationListAdapter);

        mNoReservationYetTextView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ReservationListViewModel.class);
        mViewModel.setViewModel(requireActivity().getApplication());
        mViewModel.getReservation(new ReservationListViewModel.onReservationFetch() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(ArrayList<ReservationModel> reservationList) {
                if (reservationList.isEmpty()) {
                    mNoReservationYetTextView.setVisibility(View.VISIBLE);
                } else {
                    mNoReservationYetTextView.setVisibility(View.GONE);
                }

                mReservationArrayList.addAll(reservationList);
                mReservationListAdapter.notifyDataSetChanged();
                mReservationLoadingLinearLayout.setVisibility(View.GONE);

                if (Objects.requireNonNull(mReservationRecyclerView.getAdapter()).getItemCount() > 1) {
                    mReservationRecyclerView.smoothScrollToPosition(mReservationRecyclerView.getAdapter().getItemCount()-1);
                }
            }

            @Override
            public void onFailed(DatabaseError error) {
                ToastMessage.display(getContext(), "Error: " + error);
            }
        });

        mReservationLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

}