package com.example.realtimechat.screens.screenChatInfo;


import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.myCallBack;

import java.util.ArrayList;
import java.util.Objects;

public class ChatInfoAVM extends AndroidViewModel {
    private final ArrayList<User> mListUsers = new ArrayList<>();
    private final AuthRepo authRepo;
    public ChatInfoAVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
    }

    public void initRecyclerView(RecyclerView recyclerView,  myCallBack<Object> myCallBack){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());

        UsersAdapter adapter = new UsersAdapter(getApplication(), mListUsers, (user, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("uid", user.getUid());
            myCallBack.data(bundle);
        });

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
