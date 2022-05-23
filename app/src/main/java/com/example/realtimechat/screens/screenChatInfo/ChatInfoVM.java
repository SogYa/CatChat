package com.example.realtimechat.screens.screenChatInfo;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realtimechat.data.model.User;
import com.example.realtimechat.domain.FirebaseRepository;

import java.util.ArrayList;

public class ChatInfoVM extends ViewModel {
    private final ArrayList<User> mListUsers = new ArrayList<>();
    private final FirebaseRepository firebaseRepository;
    private final MutableLiveData<ArrayList<User>> usersLiveData;
    public ChatInfoVM() {
        firebaseRepository = new FirebaseRepository();
        usersLiveData = new MutableLiveData<>();
        initRecyclerView();
    }

    public void initRecyclerView() {
        firebaseRepository.readAllUsers(user -> {
            mListUsers.add(user);
            usersLiveData.postValue(mListUsers);
        });
    }

    public MutableLiveData<ArrayList<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
