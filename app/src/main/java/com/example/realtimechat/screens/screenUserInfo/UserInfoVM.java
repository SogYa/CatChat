package com.example.realtimechat.screens.screenUserInfo;

import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.PhotoInstruments;
import com.example.realtimechat.instruments.myCallBack;

import io.reactivex.Completable;

public class UserInfoVM extends ViewModel {
    private final AuthRepo authRepo;
    private final MutableLiveData<User> userInfo;
    private final PhotoInstruments photoInstruments;

    public UserInfoVM() {
        authRepo = new AuthRepo();
        userInfo = new MutableLiveData<>();
        photoInstruments = new PhotoInstruments();
    }

    public void readUser(String uid, ImageView imageView, myCallBack<Boolean> myCallBack) {
        authRepo.readUserFromDataBase(uid, value -> {
            Completable.fromAction(() -> photoInstruments.downloadAndSetImage(uid, imageView)).subscribe();
            myCallBack.data(true);
            userInfo.postValue(value);
        });
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }
}
