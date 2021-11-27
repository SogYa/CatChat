package com.example.realtimechat.screens.screenUserProfile;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.realtimechat.R;
import com.example.realtimechat.app.App;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.model.User;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.myCallBack;
import com.example.realtimechat.screens.screenSignIn.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UserProfileVM extends AndroidViewModel {

    private final AuthRepo authRepo;
    private final FirebaseAuth mAuth;
    private final MutableLiveData<User> userInfo;
    private final App app;

    public UserProfileVM(@NonNull Application application) {
        super(application);
        userInfo = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        authRepo = new AuthRepo();
        app = new App();
    }

    //Метод получения ифнормации о пользователе
    public void checkUser() {
        authRepo.readUserFromDataBase(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), value -> {
            userInfo.postValue(value);
            SPControl.getInstance().updatePrefs(Constants.APP_PREFS_USER_NAME, value.name);
        });
    }

    //Метод выхода из аккаунта
    public void logOut() {
        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AUTH, false);
        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AVATAR_CREATED, false);
        authRepo.logOut();
        getApplication().startActivity(new Intent(getApplication(), SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    //Метод востановки пароля
    public void passwordChange() {
        authRepo.passwordChange(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(), new AuthRepo.DataListener<Object>() {
            @Override
            public void data(Object o) {
                Toast.makeText(getApplication(), "Письмо выслано на вашу эл. почту", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(String error) {
                Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Метод обрезки фото
    public void setImage(ImageView imageView, Activity o) {
        app.setImage(imageView, o);
    }

    //Метод отправки сообщения в хранилище
    public void sendImage(Uri uri, ImageView imageView) {
        SPControl.getInstance().updatePrefs(Constants.AVATAR_URI, uri.toString());
        authRepo.sendImageToStorage(Uri.parse(SPControl.getInstance().getStringPrefs(Constants.AVATAR_URI)), new AuthRepo.DataListener<Object>() {
            @Override
            public void data(Object o) {
                updateAvatar(imageView, uri -> Toast.makeText(getApplication(), "Аватар успешно загружен.", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void error(String error) {
                Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Метод обновления аватара
    public void updateAvatar(ImageView imageView, myCallBack<Boolean> myCallBack) {
        authRepo.downloadImage(Constants.STORAGE_PATH_TO_AVATARS, uriString ->
                Glide.with(getApplication().getApplicationContext())
                        .load(Uri.parse(SPControl.getInstance().getStringPrefs(Constants.AVATAR_URI)))
                        .placeholder(R.mipmap.ic_deafault_avatar)
                        .into(imageView));
        myCallBack.data(true);

    }

    public void saveNewData(String name) {
        authRepo.updateUserName(name);
    }

    public String checkName(String name) {
        if (SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_NAME).equals(name)) {
            return name;
        } else {
            return SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_NAME);
        }
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }
}
