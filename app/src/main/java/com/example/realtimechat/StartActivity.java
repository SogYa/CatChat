package com.example.realtimechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES_CHECK = "LogStatus";
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mSettings = getSharedPreferences(APP_PREFERENCES_CHECK,MODE_PRIVATE);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mSettings.contains(APP_PREFERENCES_CHECK)){
                    if(!mSettings.getBoolean(APP_PREFERENCES_CHECK,false)){
                        Intent intent = new Intent(StartActivity.this,SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(StartActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Intent intent = new Intent(StartActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);
    }
}