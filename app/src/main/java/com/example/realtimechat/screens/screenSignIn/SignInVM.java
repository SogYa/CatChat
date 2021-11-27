package com.example.realtimechat.screens.screenSignIn;


import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.myCallBack;
import com.example.realtimechat.screens.screenChat.MainActivity;
import com.example.realtimechat.screens.screenPasswordRecovery.PasswordRecoveryActivity;
import com.example.realtimechat.screens.screenRegistration.RegistrationActivity;

public class SignInVM extends AndroidViewModel {

    private final AuthRepo authRepo;
    private int count = 0;

    public SignInVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
    }

    //Пеереход к экрану регистрации
    public void goToRegistration() {
        getApplication().startActivity(new Intent(getApplication(), RegistrationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //Переход на экран востановлени пароля
    public void goToPasswordRecovery() {
        getApplication().startActivity(new Intent(getApplication(), PasswordRecoveryActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
                        getApplication().startActivity(new Intent(getApplication(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
}