package com.project.hairtologyuser.views.fragments.userchat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ChatModel;

import java.util.ArrayList;
import java.util.HashMap;

public class UserChatFragment extends Fragment {

    private static String mReceiverUuid;
    private UserChatViewModel mViewModel;
    private View mView;
    private EditText mMessageEditText;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private UserChatListAdapter mUserChatListAdapter;
    private ArrayList<ChatModel> mChatArrayList;
    private DatabaseReference mReference;
    private FirebaseUser mUser;

    public static UserChatFragment newInstance(String receiverUuid) {
        mReceiverUuid = receiverUuid;
        return new UserChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_chat, container, false);

        mMessageEditText = mView.findViewById(R.id.editTextUserChat);
        mImageView = mView.findViewById(R.id.sendUserChatFragment);
        mRecyclerView = mView.findViewById(R.id.userChatRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mUserChatListAdapter);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mUser != null) {
            mImageView.setOnClickListener(view -> {
                String message = String.valueOf(mMessageEditText.getText());
                if (!message.isEmpty()) {
                    onSendMessage(mUser.getUid(), mReceiverUuid, message);
                    mMessageEditText.setText("");
                }
            });

            onReadMessage(mUser.getUid(), mReceiverUuid);
        }

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserChatViewModel.class);
    }

    private void onSendMessage(String sender, String receiver, String message) {
        mReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        mReference.child("chats").push().setValue(hashMap);
    }

    private void onReadMessage(final String myId, final String userId) {
        mChatArrayList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("chats");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatModel chat = dataSnapshot.getValue(ChatModel.class);

                    if (chat != null) {

                        if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                            mChatArrayList.add(chat);
                        }

                        mUserChatListAdapter = new UserChatListAdapter(getContext(), mChatArrayList);
                        mRecyclerView.setAdapter(mUserChatListAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}