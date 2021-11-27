package com.example.realtimechat.screens.screenStart;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.realtimechat.R;

public class StartActivity extends AppCompatActivity {

    private TextView hintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        hintView = findViewById(R.id.textViewHint);

        StartVM startVM = new ViewModelProvider(this).get(StartVM.class);
        startVM.checkStatus();

        startVM.getHintsData().observe(this, s -> hintView.setText(s));


    }

}