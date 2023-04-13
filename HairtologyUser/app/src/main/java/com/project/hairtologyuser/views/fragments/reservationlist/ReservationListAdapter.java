package com.project.hairtologyuser.views.fragments.reservationlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ReservationModel;

import java.util.ArrayList;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ReservationModel> mReservationList;

    public ReservationListAdapter(Context context, ArrayList<ReservationModel> reservationList) {
        mContext = context;
        mReservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_reservation_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReservationModel reservation = mReservationList.get(position);

        if (reservation.getServiceType() != null) {
            holder.serviceTypeTextView.setText(reservation.getServiceType().toString());
        } else {
            holder.serviceTypeTextView.setText("");
        }

        holder.timeTextView.setText(reservation.getTime());
        holder.dayTextView.setText(reservation.getDay());
        holder.monthTextView.setText(reservation.getMonth());
        holder.noteTextView.setText(reservation.getNote());
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

        TextView serviceTypeTextView;
        TextView timeTextView;
        TextView dayTextView;
        TextView monthTextView;
        TextView noteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceTypeTextView = itemView.findViewById(R.id.serviceTypeTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            monthTextView = itemView.findViewById(R.id.monthTextView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
        }
    }

}
