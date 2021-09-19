package com.example.realtimechat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_CHECK = "LogStatus";
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES_CHECK,MODE_PRIVATE);
    }

    public void onClickLogOut(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Выход из аккаунта")
                            .setMessage("Вы уверены, что хотите выйти из аккаунта?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                                    SharedPreferences.Editor editor = mSettings.edit();
                                    editor.putBoolean(APP_PREFERENCES_CHECK, false);
                                    editor.apply();
                                    finish();
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setIcon(R.drawable.ic_baseline_warning)
                            .show();
                }
            }
        });

    }

}