package com.project.hairtologyowner.views.fragments.userreservationlist;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.hairtologyowner.BuildConfig;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.components.utils.ToastMessage;
import com.project.hairtologyowner.models.ReservationModel;
import com.project.hairtologyowner.models.UserReservationModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.useraccountinfo.UserAccountInfoFragment;
import com.project.hairtologyowner.views.fragments.useraccountlist.UserAccountListFragment;
import com.project.hairtologyowner.views.fragments.userreservationinfo.UserReservationInfoFragment;

import java.util.ArrayList;
import java.util.Objects;

public class UserReservationListFragment extends Fragment {
    private UserReservationListViewModel mViewModel;
    private UserReservationListAdapter mUserReservationAdapter;
    private ArrayList<UserReservationModel> mUserReservationArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reservation_list, container, false);

        mViewModel = new ViewModelProvider(this).get(UserReservationListViewModel.class);
        mViewModel.viewModel(requireActivity().getApplication());

        mUserReservationAdapter = new UserReservationListAdapter(getContext(), mUserReservationArrayList);
        mUserReservationAdapter.setOnServiceListener((position, userReservation) -> {
            if (getActivity() == null) {
                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        UserReservationListFragment.class
                ));
                return;
            }

            ((MainActivity) getActivity()).replaceFragment(
                    UserReservationInfoFragment.newInstance(
                            userReservation.getUserUuid(),
                            userReservation.getUserFirstName(),
                            userReservation.getUserLastName(),
                            userReservation.getReservationList().get(position)),
                    MainActivity.containerViewId);
        });

        RecyclerView recyclerView = view.findViewById(R.id.userReservationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mUserReservationAdapter);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getUserReservation(new UserReservationListViewModel.OnUserReservationDataListener() {
            @Override
            public void onSuccess(ArrayList<UserReservationModel> userReservation) {
                mUserReservationArrayList.addAll(userReservation);
                mUserReservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMessage) {
                ToastMessage.display(getContext(), errorMessage);
            }
        });
    }

}