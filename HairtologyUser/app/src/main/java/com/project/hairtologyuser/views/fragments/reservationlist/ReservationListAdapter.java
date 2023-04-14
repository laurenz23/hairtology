package com.project.hairtologyuser.views.fragments.reservationlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.client.FirebaseClient;
import com.project.hairtologyuser.components.repository.Session;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {

    public interface OnReservationClickListener {
        void onReservationCancel(int position);
    }

    private final Context mContext;
    private final ArrayList<ReservationModel> mReservationList;
    private OnReservationClickListener mListener;

    public void setOnReservationClickListener(OnReservationClickListener listener) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReservationModel reservation = mReservationList.get(position);

        switch (reservation.getServiceType()) {
            case STYLING:
                holder.stylingLinearLayout.setVisibility(View.VISIBLE);
                break;
            case TREATMENT:
                holder.treatmentLinearLayout.setVisibility(View.VISIBLE);
                break;
            case COLORING:
                holder.coloringLinearLayout.setVisibility(View.VISIBLE);
                break;
            case RELAXING:
                holder.relaxingLinearLayout.setVisibility(View.VISIBLE);
                break;
            case HAIRCUT:
                holder.haircutLinearLayout.setVisibility(View.VISIBLE);
                break;
            case BLOW_DRY:
                holder.blowDryLinearLayout.setVisibility(View.VISIBLE);
                break;
            case BEARD_CUT:
                holder.beardCutLinearLayout.setVisibility(View.VISIBLE);
                break;
            case SPECIAL:
                holder.specialLinearLayout.setVisibility(View.VISIBLE);
                break;
        }

        String note = reservation.getNote();
        if (note.isEmpty()) {
            holder.noteLinearLayout.setVisibility(View.GONE);
        } else {
            holder.noteTextView.setText(note);
            holder.noteLinearLayout.setVisibility(View.VISIBLE);
        }

        holder.dayTextView.setText(reservation.getDay());
        holder.timeTextView.setText("Time: " + reservation.getTime());
        holder.monthTextView.setText(reservation.getMonth());
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

        LinearLayout stylingLinearLayout;
        LinearLayout treatmentLinearLayout;
        LinearLayout coloringLinearLayout;
        LinearLayout relaxingLinearLayout;
        LinearLayout haircutLinearLayout;
        LinearLayout blowDryLinearLayout;
        LinearLayout beardCutLinearLayout;
        LinearLayout specialLinearLayout;
        LinearLayout noteLinearLayout;
        TextView timeTextView;
        TextView dayTextView;
        TextView monthTextView;
        TextView noteTextView;
        Button cancelReservation;

        public ViewHolder(@NonNull View itemView, OnReservationClickListener listener) {
            super(itemView);

            stylingLinearLayout = itemView.findViewById(R.id.stylingLinearLayout);
            treatmentLinearLayout = itemView.findViewById(R.id.treatmentLinearLayout);
            coloringLinearLayout = itemView.findViewById(R.id.coloringLinearLayout);
            relaxingLinearLayout = itemView.findViewById(R.id.relaxingLinearLayout);
            haircutLinearLayout = itemView.findViewById(R.id.haircutLinearLayout);
            blowDryLinearLayout = itemView.findViewById(R.id.blowDryLinearLayout);
            beardCutLinearLayout = itemView.findViewById(R.id.beardCutLinearLayout);
            specialLinearLayout = itemView.findViewById(R.id.specialLinearLayout);
            noteLinearLayout = itemView.findViewById(R.id.noteLinearLayout);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            monthTextView = itemView.findViewById(R.id.monthTextView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            cancelReservation = itemView.findViewById(R.id.cancelReservationButton);

            cancelReservation.setOnClickListener(view -> {
                listener.onReservationCancel(getAdapterPosition());
            });
        }
    }

}
