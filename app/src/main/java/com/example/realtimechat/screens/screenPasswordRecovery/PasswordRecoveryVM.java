package com.example.realtimechat.screens.screenPasswordRecovery;

import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.realtimechat.datalayer.AuthRepo;

public class PasswordRecoveryVM extends AndroidViewModel {

    private final AuthRepo authRepo;

    public PasswordRecoveryVM(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
    }

    //Востановление пароля
    public void passwordRecovery(String email) {
        if (!email.isEmpty()) {
            authRepo.passwordChange(email, new AuthRepo.DataListener<Object>() {
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
    }
}
