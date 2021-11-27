package com.example.realtimechat.screens.screenUserInfo;

import android.app.Application;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.myCallBack;

public class UserInfoVM extends AndroidViewModel {
    private final AuthRepo authRepo;
    private final MutableLiveData<User> userInfo;

    public UserInfoVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
        userInfo = new MutableLiveData<>();
    }

    public void readUser(String uid, ImageView imageView, myCallBack<Boolean> myCallBack) {
        authRepo.readUserFromDataBase(uid, value -> {
            SPControl.getInstance().updatePrefs(Constants.APP_PREFS_USER_NAME, value.name);
            authRepo.downloadImage(Constants.STORAGE_PATH_TO_AVATAR.child(uid), uriString ->
                    Glide.with(getApplication().getApplicationContext())
                            .load(Uri.parse(value.getPhotoUrl()))
                            .placeholder(R.mipmap.ic_deafault_avatar)
                            .into(imageView));
            myCallBack.data(true);
            userInfo.postValue(value);
        });

    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }
}
