package com.example.realtimechat.screens.screenChatInfo;


import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.myCallBack;
import com.example.realtimechat.screens.screenUserInfo.UserInfoActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ChatInfoVM extends AndroidViewModel {
    private final ArrayList<User> mListUsers = new ArrayList<>();
    private final AuthRepo authRepo;
    public ChatInfoVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
    }

    public void initRecyclerView(RecyclerView recyclerView,  myCallBack<Boolean> myCallBack){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());

        UsersAdapter adapter = new UsersAdapter(getApplication(), mListUsers, (user, position) -> getApplication().startActivity(new Intent(getApplication(), UserInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("uid",user.getUid())));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        authRepo.readAllUsers(user -> {
            mListUsers.add(user);
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(1);
            myCallBack.data(true);
        });
    }
}
