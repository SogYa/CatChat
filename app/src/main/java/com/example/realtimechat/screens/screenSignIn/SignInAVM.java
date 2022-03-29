package com.example.realtimechat.screens.screenSignIn;


import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.myCallBack;

public class SignInAVM extends AndroidViewModel {

    private final AuthRepo authRepo;
    private int count = 0;
    private final MutableLiveData<Integer> navigationLiveData;

    public SignInAVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
        navigationLiveData = new MutableLiveData<>();
    }


    //Пеереход к экрану регистрации(Временно не испольузется)
    public void goToRegistration() {
        navigationLiveData.postValue(R.id.action_signInFragment_to_registrationFragment);
    }

    //Переход на экран востановлени пароля(временно не используется)
    public void goToPasswordRecovery() {
        navigationLiveData.postValue(R.id.action_signInFragment_to_passwordRecoveryFragment);
    }

    //Вход в систему
    public void logIn(String email, String password, myCallBack<Boolean> myCallBack) {
        //Проверка на заполненность полей
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplication(), "Заполните поля", Toast.LENGTH_SHORT).show();
            return;
        }
        authRepo.signIn(email, password, new AuthRepo.DataListener<String>() {
                    @Override
                    public void data(String o) {
                        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_AUTH, true);
                        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_USER_ID, o);
                        myCallBack.data(true);
                        Toast.makeText(getApplication(), "С вовзращением!", Toast.LENGTH_SHORT).show();
                        navigationLiveData.postValue(R.id.action_signInFragment_to_chatFragment2);
                    }

                    @Override
                    public void error(String error) {
                        Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
                        myCallBack.data(false);
                    }
                }
        );
    }

    //Секрет с логотипом
    public void onCatClick() {
        switch (count) {
            default:
                Toast.makeText(getApplication(), "Meow", Toast.LENGTH_SHORT).show();
                count++;
                break;
            case 1:
                Toast.makeText(getApplication(), "*Sniff-sniff*", Toast.LENGTH_SHORT).show();
                count++;
                break;
            case 2:
                Toast.makeText(getApplication(), "*Mrrrrr*", Toast.LENGTH_SHORT).show();
                count++;
                break;
            case 3:
                Toast.makeText(getApplication(), "Cats really love you", Toast.LENGTH_SHORT).show();
                count = 0;
                break;
        }
    }
    //Navigation LiveData
    public MutableLiveData<Integer> getNavigationLiveData() {
        return navigationLiveData;
    }
}