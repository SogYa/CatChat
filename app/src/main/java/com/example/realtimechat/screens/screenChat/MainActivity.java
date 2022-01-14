package com.example.realtimechat.screens.screenChat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.R;
import com.example.realtimechat.instruments.Constants;

public class MainActivity extends AppCompatActivity {
    private EditText messageEditText;
    private MainVM vm;
    private ConstraintLayout loadingLayout;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatRecyclerView);

        loadingLayout = findViewById(R.id.loading);

        messageEditText = findViewById(R.id.editTextMessage);

        System.out.println(Constants.APP_PREFS_USER_ID);

        vm = new ViewModelProvider(this).get(MainVM.class);
        vm.initRecyclerView(recyclerView, aBoolean -> loadingLayout.setVisibility(View.GONE));
    }

    @Override
    protected void onStart() {
        super.onStart();
        vm.activityStatus(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        vm.activityStatus(false);
    }

    public void onClickProfile(View view) {
        vm.goToSettings();
    }

    public void onClickChatInfo(View view) {
        vm.goToChatInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSendMessage(View view) {
        vm.sendMessage(messageEditText.getText().toString());
        messageEditText.setText("");
    }
}