package com.example.realtimechat.screens.screenUserProfile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.datamanager.RxData;
import com.theartofdev.edmodo.cropper.CropImage;

public class UserProfileActivity extends AppCompatActivity {

    private EditText userNameEditText, userEmailEditText;
    private ImageView avatarImageView;
    private UserProfileVM vm;
    private ConstraintLayout loadingLayout;
    private LinearLayout buttonsLayout;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxData.activityBehaviorRelay.accept(this);
        setContentView(R.layout.fragment_user_profile);

        userNameEditText = findViewById(R.id.editTextUserName);
        userEmailEditText = findViewById(R.id.editTextUserEmail);

        loadingLayout = findViewById(R.id.loadingSignIn);
        buttonsLayout = findViewById(R.id.linearLayoutButtons);

        saveButton = findViewById(R.id.buttonSave);
        cancelButton = findViewById(R.id.buttonCancel);

        avatarImageView = findViewById(R.id.imageViewAvatar);
        avatarImageView.setClickable(false);
        userNameEditText.setEnabled(false);

        loadingLayout.setVisibility(View.VISIBLE);
        vm = new ViewModelProvider(this).get(UserProfileVM.class);
        vm.checkUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxData.activityBehaviorRelay.accept(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxData.activityBehaviorRelay.accept(this);
        vm.updateAvatar(avatarImageView, aBoolean -> {
        });
        vm.getUserInfo().observe(this, user -> {
            userEmailEditText.setText(user.email);
            userNameEditText.setText(user.name);
            loadingLayout.setVisibility(View.GONE);
        });
    }

    public void onClickLogOut(View view) {
        vm.logOutDialog();
    }

    public void onClickChangePassword(View view) {
        vm.passwordChange();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickSetImage(View view) {
        //vm.setImage(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            vm.sendImage(CropImage.getActivityResult(data).getUri(), avatarImageView);
        }
    }

    public void onClickGoBack(View view) {
        finish();
    }

    public void onClickSave(View view) {
        vm.saveButton(aBoolean -> {
            if (aBoolean) {
                buttonsLayout.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                avatarImageView.setClickable(false);
                userNameEditText.setEnabled(false);
                vm.saveNewData(userNameEditText.getText().toString());
            }
        });
    }

    public void onClickChangeInfo(View view) {
        buttonsLayout.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        avatarImageView.setClickable(true);
        userNameEditText.setEnabled(true);
    }

    public void onClickCancel(View view) {
        vm.checkName(userNameEditText.getText().toString());
        buttonsLayout.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        avatarImageView.setClickable(false);
        userNameEditText.setEnabled(false);
    }
}