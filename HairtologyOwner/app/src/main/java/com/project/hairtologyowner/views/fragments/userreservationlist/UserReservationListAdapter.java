package com.project.hairtologyowner.views.fragments.userreservationlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.StringFormat;
import com.project.hairtologyowner.models.ReservationModel;
import com.project.hairtologyowner.models.UserReservationModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class UserReservationListAdapter extends RecyclerView.Adapter<UserReservationListAdapter.ViewHolder> {

    public interface OnUserReservationItemListener {
        void onTap(int position, UserReservationModel userReservation);
    }

    private Context mContext;
    private ArrayList<UserReservationModel> mUserReservationList;
    private OnUserReservationItemListener mListener;

    public UserReservationListAdapter(Context context, ArrayList<UserReservationModel> userReservationList) {
        this.mContext = context;
        this.mUserReservationList = userReservationList;
    }

    public void setOnServiceListener(OnUserReservationItemListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public UserReservationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_user_reservation_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserReservationListAdapter.ViewHolder holder, int position) {
        UserReservationModel userReservation = mUserReservationList.get(position);
        holder.userReservation = userReservation;
        holder.userName.setText(userReservation.getUserFirstName() + " " + userReservation.getUserLastName());

        for (ReservationModel reservation : userReservation.getReservationList()) {
            holder.detail.setText(reservation.getServiceDetail());
            holder.shopName.setText(reservation.getShopName());
            holder.detail.setText(reservation.getServiceDetail());
            holder.price.setText(reservation.getPrice());

            int year = Integer.parseInt(reservation.getYear());
            int month = Integer.parseInt(reservation.getMonth());
            int day = Integer.parseInt(reservation.getDay());
            Date date = new Date(year, month, day);

            int hour = Integer.parseInt(reservation.getTime());
            int minute = Integer.parseInt(reservation.getMinute());
            Time time = new Time(hour, minute, 0);

            holder.date.setText(StringFormat.date(date));
            holder.time.setText(StringFormat.date(time));
        }
    }

    @Override
    public int getItemCount() {
        if (mUserReservationList == null)
            return 0;

        return mUserReservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private UserReservationModel userReservation;
        private LinearLayout userReservationLinearLayout;
        private TextView userName;
        private TextView shopName;
        private TextView detail;
        private TextView date;
        private TextView time;
        private TextView price;

        public ViewHolder(@NonNull View itemView, OnUserReservationItemListener listener) {
            super(itemView);

            userReservationLinearLayout = itemView.findViewById(R.id.itemUserReservationLinearLayout);
            userName = itemView.findViewById(R.id.itemUserReservationName);
            shopName = itemView.findViewById(R.id.itemUserReservationShopName);
            detail = itemView.findViewById(R.id.itemUserReservationDetail);
            date = itemView.findViewById(R.id.itemUserReservationDate);
            time = itemView.findViewById(R.id.itemUserReservationTime);
            price = itemView.findViewById(R.id.itemUserReservationPrice);

            userReservationLinearLayout.setOnClickListener(v -> {
                listener.onTap(getAdapterPosition(), userReservation);
            });
        }

    }

}
