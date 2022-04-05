package com.example.realtimechat.screens.screenChatInfo;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.model.User;

import java.util.ArrayList;

public class ChatInfoVM extends ViewModel {
    private final ArrayList<User> mListUsers = new ArrayList<>();
    private final AuthRepo authRepo;
    private final MutableLiveData<ArrayList<User>> usersLiveData;
    public ChatInfoVM() {
        authRepo = new AuthRepo();
        usersLiveData = new MutableLiveData<>();
        initRecyclerView();
    }

    public void initRecyclerView() {
        authRepo.readAllUsers(user -> {
            mListUsers.add(user);
            usersLiveData.postValue(mListUsers);
        });
    }

    public MutableLiveData<ArrayList<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
