package com.project.hairtologyowner.views.fragments.userchat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.hairtologyowner.R;
import com.project.hairtologyowner.models.ChatModel;

import java.util.ArrayList;

public class UserChatListAdapter extends RecyclerView.Adapter<UserChatListAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private ArrayList<ChatModel> mChatArrayList;
    private String mShopUuid;

    public UserChatListAdapter(Context context, ArrayList<ChatModel> chatArrayList, String shopUuid) {
        mContext = context;
        mChatArrayList = chatArrayList;
        mShopUuid = shopUuid;
    }

    @NonNull
    @Override
    public UserChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_LEFT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_right_layout, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_left_layout, parent, false);
        }
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserChatListAdapter.ViewHolder holder, int position) {
        ChatModel chat = mChatArrayList.get(position);
        holder.messageTextView.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        if (mChatArrayList == null)
            return 0;

        return mChatArrayList.size();
    }

    public int getItemViewType(int position) {
        if (mChatArrayList.get(position).getReceiver().equals(mShopUuid)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.messageItemChat);
        }

    }
}