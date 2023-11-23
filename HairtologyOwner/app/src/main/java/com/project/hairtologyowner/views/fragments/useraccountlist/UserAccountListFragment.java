package com.project.hairtologyowner.views.fragments.useraccountlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.ErrorUtil;
import com.project.hairtologyowner.components.utils.ToastMessage;
import com.project.hairtologyowner.models.UserModel;
import com.project.hairtologyowner.views.activities.MainActivity;
import com.project.hairtologyowner.views.fragments.useraccountinfo.UserAccountInfoFragment;

import java.util.ArrayList;

public class UserAccountListFragment extends Fragment {

    private UserAccountListViewModel mViewModel;
    private UserAccountListAdapter mUserAccountListAdapter;
    private ArrayList<UserModel> mUserAccountArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account_list, container, false);

        mViewModel = new ViewModelProvider(this).get(UserAccountListViewModel.class);
        mViewModel.viewModel(requireActivity().getApplication());

        mUserAccountListAdapter = new UserAccountListAdapter(getContext(), mUserAccountArrayList);
        mUserAccountListAdapter.setOnServiceListener((position, user) -> {
            if (getActivity() == null) {
                Log.e(getClass().getSimpleName(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        UserAccountListFragment.class
                ));
                return;
            }

            ((MainActivity) getActivity()).replaceFragment(
                    UserAccountInfoFragment.newInstance(user),
                    MainActivity.containerViewId);
        });

        RecyclerView recyclerView = view.findViewById(R.id.userAccountRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mUserAccountListAdapter);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getUserAccountList(new UserAccountListViewModel.OnUserAccountDataListener() {
            @Override
            public void onSuccess(ArrayList<UserModel> user) {
                mUserAccountArrayList.addAll(user);
                mUserAccountListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMessage) {
                ToastMessage.display(getContext(), errorMessage);
            }
        });
    }

}