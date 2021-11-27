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

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<User> users;
    private final OnUserClickListener onClickListener;


    interface OnUserClickListener {
        void onUserClick(User user, int position);
    }

    public UsersAdapter(Context context, List<User> users,OnUserClickListener onClickListener ) {
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
        holder.userName.setText(user.getName());
        holder.userStatus.setText(user.getStatus());
        holder.userAvatar.setImageResource(R.mipmap.ic_deafault_avatar);
        holder.itemView.setOnClickListener(view -> onClickListener.onUserClick(user,position));
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
            userAvatar = itemView.findViewById(R.id.imageViewUserAvatar);
            userName = itemView.findViewById(R.id.textViewUserName);
            userStatus = itemView.findViewById(R.id.textViewUserStatus);
        }
    }
}
