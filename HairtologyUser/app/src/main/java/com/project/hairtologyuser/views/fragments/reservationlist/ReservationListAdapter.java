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
        holder.dateTextView.setText(reservation.getDate());
        holder.dateTextView.setText(reservation.getTime());
        holder.dateTextView.setText(reservation.getNote());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ReservationModel> reservationList) {
        mReservationList.clear();
        mReservationList.addAll(reservationList);
    }

    @Override
    public int getItemCount() {
        return mReservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView timeTextView;
        TextView noteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.reservationDateTextView);
            timeTextView = itemView.findViewById(R.id.reservationTimeTextView);
            noteTextView = itemView.findViewById(R.id.reservationNoteTextView);
        }
    }

}
