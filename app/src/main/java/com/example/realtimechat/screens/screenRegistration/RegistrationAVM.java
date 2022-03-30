package com.example.realtimechat.screens.screenRegistration;

import android.app.Application;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bumptech.glide.Glide;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.PhotoInstruments;
import com.example.realtimechat.instruments.myCallBack;

public class RegistrationAVM extends AndroidViewModel {

    private final AuthRepo authRepo;

    public RegistrationAVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
        PhotoInstruments photoInstruments = new PhotoInstruments();
    }

    //Метод регистрации пользователя
    public void registration(String name, String email, String password,
                             myCallBack<Object> myCallBack) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                || !SPControl.getInstance().getBoolPrefs(Constants.APP_PREFS_IS_AVATAR_CREATED)) {
            Toast.makeText(getApplication(), "Ой-ой, вы что-то не заполнили(", Toast.LENGTH_SHORT).show();
            myCallBack.data(true);
        } else {
            authRepo.registration(email, password, new AuthRepo.DataListener<String>() {
                @Override
                public void data(String o) {
                    authRepo.createNewUser(name, s -> authRepo.sendImageToStorage(Uri.parse(SPControl.getInstance().getStringPrefs(Constants.AVATAR_URI)),
                            new AuthRepo.DataListener<Object>() {
                                @Override
                                public void data(Object o) {
                                    authRepo.setImage(s);
                                }

                                @Override
                                public void error(String error) {
                                    Toast.makeText(getApplication(), error,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }));
                    SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AUTH, true);
                    SPControl.getInstance().updatePrefs(Constants.APP_PREFS_USER_ID, o);
                    SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AVATAR_CREATED, false);
                    myCallBack.data("Success");
                    Toast.makeText(getApplication(), "Добро пожаловать!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void error(String error) {
                    myCallBack.data(true);
                    Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sendImage(Uri uri, ImageView imageView) {
        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AVATAR_CREATED, true);
        SPControl.getInstance().updatePrefs(Constants.AVATAR_URI, uri.toString());
        Glide.with(getApplication().getApplicationContext())
                .load(uri)
                .into(imageView);
    }
}
