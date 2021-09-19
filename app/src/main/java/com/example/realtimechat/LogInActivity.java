package com.example.realtimechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onClickGoToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }

    public void onClickLogIn(View view) {
    }
}