package com.project.hairtologyowner.views.fragments.reservationlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyowner.R;
import com.project.hairtologyowner.components.utils.StringFormat;
import com.project.hairtologyowner.models.ReservationModel;
import com.project.hairtologyowner.models.ReservationStatus;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {

    public interface OnReservationTapListener {
        void onReservationTap(int position);
        void onReservationCancel(int position);
    }

    private final Context mContext;
    private final ArrayList<ReservationModel> mReservationList;
    private OnReservationTapListener mListener;

    public void onReservationTapListener(OnReservationTapListener listener) {
        this.mListener = listener;
    }

    public ReservationListAdapter(Context context, ArrayList<ReservationModel> reservationList) {
        mContext = context;
        mReservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_reservation_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReservationModel reservation = mReservationList.get(position);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        Date date = new Date(year, Integer.parseInt(reservation.getMonth()), Integer.parseInt(reservation.getDay()));

        int hour = Integer.parseInt(reservation.getTime());
        int minute = Integer.parseInt(reservation.getMinute());

        if (reservation.getStatus() == ReservationStatus.OWNER_CANCELLED) {
            holder.cancelledOwnerTextView.setVisibility(View.VISIBLE);
            holder.cancelReservation.setVisibility(View.GONE);
        } else if (reservation.getStatus() == ReservationStatus.USER_CANCELLED) {
            holder.cancelledUserTextView.setVisibility(View.VISIBLE);
            holder.cancelReservation.setVisibility(View.GONE);
        }

        holder.shopNameTextView.setText(reservation.getShopName());
        holder.dayTextView.setText(reservation.getDay());
        holder.timeTextView.setText(StringFormat.time(new Time(hour, minute, 0)));
        holder.monthTextView.setText(new SimpleDateFormat("MMMM").format(date.getMonth()));
        holder.priceTextView.setText(mContext.getString(R.string.str_symbol_peso) + " " + reservation.getPrice() + ".00");
        holder.detailTextView.setText(reservation.getServiceDetail());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ReservationModel> reservationList) {
        mReservationList.clear();
        mReservationList.addAll(reservationList);
    }

    @Override
    public int getItemCount() {
        if (mReservationList == null)
            return 0;

        return mReservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout reservationLinearLayout;
        LinearLayout detailLinearLayout;
        TextView cancelledOwnerTextView;
        TextView cancelledUserTextView;
        TextView shopNameTextView;
        TextView timeTextView;
        TextView dayTextView;
        TextView monthTextView;
        TextView priceTextView;
        TextView detailTextView;
        Button cancelReservation;

        public ViewHolder(@NonNull View itemView, OnReservationTapListener listener) {
            super(itemView);

            reservationLinearLayout = itemView.findViewById(R.id.reservationItemLinearLayout);
            detailLinearLayout = itemView.findViewById(R.id.detailLinearLayout);
            cancelledOwnerTextView = itemView.findViewById(R.id.cancelledOwnerTextView);
            cancelledUserTextView = itemView.findViewById(R.id.cancelledUserTextView);
            shopNameTextView = itemView.findViewById(R.id.shopNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            monthTextView = itemView.findViewById(R.id.monthTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            detailTextView = itemView.findViewById(R.id.detailTextView);
            cancelReservation = itemView.findViewById(R.id.cancelReservationButton);

            reservationLinearLayout.setOnClickListener(view -> {
                listener.onReservationTap(getAdapterPosition());
            });

            cancelReservation.setOnClickListener(view -> {
                listener.onReservationCancel(getAdapterPosition());
            });
        }
    }

}
