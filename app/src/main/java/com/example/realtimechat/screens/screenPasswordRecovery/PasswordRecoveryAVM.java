package com.example.realtimechat.screens.screenPasswordRecovery;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.realtimechat.domain.FirebaseRepository;

public class PasswordRecoveryAVM extends AndroidViewModel {

    private final FirebaseRepository firebaseRepository;

    public PasswordRecoveryAVM(@NonNull Application application) {
        super(application);
        firebaseRepository = new FirebaseRepository();
    }

    //Востановление пароля
    public void passwordRecovery(String email) {
        if (!email.isEmpty()) {
            firebaseRepository.passwordChange(email, new FirebaseRepository.DataListener<Object>() {
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
