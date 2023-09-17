package com.project.hairtologyowner.views.fragments.useraccountlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.UserModel;

import java.util.ArrayList;

public class UserAccountListAdapter extends RecyclerView.Adapter<UserAccountListAdapter.ViewHolder> {

    public interface OnUserAccountItemListener {
        void onTap (int position, UserModel user);
    }

    private Context mContext;
    private ArrayList<UserModel> mUserArrayList;
    private UserAccountListAdapter.OnUserAccountItemListener mListener;

    public UserAccountListAdapter(Context context, ArrayList<UserModel> userArrayList) {
        mContext = context;
        mUserArrayList = userArrayList;
    }

    public void setOnServiceListener(OnUserAccountItemListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public UserAccountListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_user_account_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserAccountListAdapter.ViewHolder holder, int position) {
        UserModel user = mUserArrayList.get(position);
        holder.name.setText(user.getFirstName() + " " + user.getLastName());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        if (mUserArrayList == null) {
            return 0;
        }

        return mUserArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private UserModel user;
        private LinearLayout userAccountLinearLayout;
        private TextView name;
        private TextView email;

        public ViewHolder(@NonNull View itemView, OnUserAccountItemListener listener) {
            super(itemView);

            userAccountLinearLayout = itemView.findViewById(R.id.itemUserAccountLinearLayout);
            name = itemView.findViewById(R.id.itemUserAccountName);
            email = itemView.findViewById(R.id.itemUserAccountEmail);

            userAccountLinearLayout.setOnClickListener(v -> {
                listener.onTap(getAdapterPosition(), user);
            });
        }

    }

}
