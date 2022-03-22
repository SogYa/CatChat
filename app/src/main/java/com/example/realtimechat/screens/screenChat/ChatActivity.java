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

public class ChatActivity extends AppCompatActivity {
    private EditText messageEditText;
    private ChatVM vm;
    private ConstraintLayout loadingLayout;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        //Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatRecyclerView);

        loadingLayout = findViewById(R.id.loading);

        messageEditText = findViewById(R.id.editTextMessage);

        vm = new ViewModelProvider(this).get(ChatVM.class);
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