package com.example.realtimechat.screens.screenUserInfo;

import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realtimechat.data.model.User;
import com.example.realtimechat.domain.FirebaseRepository;
import com.example.realtimechat.instruments.PhotoInstruments;
import com.example.realtimechat.instruments.myCallBack;

import io.reactivex.Completable;

public class UserInfoVM extends ViewModel {
    private final FirebaseRepository firebaseRepository;
    private final MutableLiveData<User> userInfo;
    private final PhotoInstruments photoInstruments;

    public UserInfoVM() {
        firebaseRepository = new FirebaseRepository();
        userInfo = new MutableLiveData<>();
        photoInstruments = new PhotoInstruments();
    }

    public void readUser(String uid, ImageView imageView, myCallBack<Boolean> myCallBack) {
        firebaseRepository.readUserFromDataBase(uid, value -> {
            Completable.fromAction(() -> photoInstruments.downloadAndSetImage(uid, imageView)).subscribe();
            myCallBack.data(true);
            userInfo.postValue(value);
        });
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }
}
