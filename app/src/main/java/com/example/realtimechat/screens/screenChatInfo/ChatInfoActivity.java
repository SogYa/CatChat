package com.example.realtimechat.screens.screenChatInfo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimechat.R;

public class ChatInfoActivity extends AppCompatActivity {
    private ConstraintLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_info);

        ChatInfoVM vm = new ViewModelProvider(this).get(ChatInfoVM.class);
        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);
        loadingLayout = findViewById(R.id.loadingLayoutUsers);

        vm.initRecyclerView(recyclerView, aBoolean -> loadingLayout.setVisibility(View.GONE));
    }

    public void onClickGoBack(View view) {
        finish();
    }
}