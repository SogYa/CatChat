package com.example.realtimechat.screens.screenChatInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.PhotoInstruments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<User> users;
    private final OnUserClickListener onClickListener;
    private final PhotoInstruments photoInstruments = new PhotoInstruments();



    interface OnUserClickListener {
        void onUserClick(User user, int position);
    }

    public UsersAdapter(Context context, List<User> users, OnUserClickListener onClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.users = users;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.users_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        Constants.PATH_TO_USER.child(user.uid).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.userStatus.setText(Objects.requireNonNull(snapshot.getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        photoInstruments.downloadAndSetImage(user.uid, holder.userAvatar);
        holder.userName.setText(user.getName());
        holder.itemView.setOnClickListener(view -> onClickListener.onUserClick(user, position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userStatus;
        private final ImageView userAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            userName = itemView.findViewById(R.id.textViewUserName);
            userStatus = itemView.findViewById(R.id.textViewUserStatus);
        }
    }
}
