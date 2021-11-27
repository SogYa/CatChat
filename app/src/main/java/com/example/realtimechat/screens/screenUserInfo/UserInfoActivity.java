package com.example.realtimechat.screens.screenUserInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realtimechat.R;

public class UserInfoActivity extends AppCompatActivity {
    private UserInfoVM vm;
    private ImageView imageViewAvatar;
    private TextView userNameTextView, userEmailTextView, userHeaderTextView;
    private ConstraintLayout loadingLayout;
    private Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        imageViewAvatar = findViewById(R.id.imageViewUserIAvatar);
        userNameTextView = findViewById(R.id.textViewUserInfoName);
        userEmailTextView = findViewById(R.id.textViewUserInfoEmail);
        userHeaderTextView = findViewById(R.id.textViewHeaderName);
        loadingLayout = findViewById(R.id.loadingLayout);
        arguments = getIntent().getExtras();

        vm = new ViewModelProvider(this).get(UserInfoVM.class);
    }

    public void onClickGoBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.readUser(arguments.get("uid").toString(), imageViewAvatar, aBoolean -> loadingLayout.setVisibility(View.GONE));
        vm.getUserInfo().observe(this, user -> {
            userEmailTextView.setText(user.email);
            userNameTextView.setText(user.name);
            userHeaderTextView.setText(user.name);
        });

    }
}