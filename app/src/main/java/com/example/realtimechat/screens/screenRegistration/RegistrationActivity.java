package com.example.realtimechat.screens.screenRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.realtimechat.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class RegistrationActivity extends AppCompatActivity {

    private ConstraintLayout loadingLayout;
    private EditText emailAddressEditText, passwordEditText, userNameEditText;
    private RegistrationVM vm;
    private ImageView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);

        vm = new ViewModelProvider(this).get(RegistrationVM.class);

        emailAddressEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        userNameEditText = findViewById(R.id.editTextName);

        avatarView = findViewById(R.id.imageSignInAvatar);

        loadingLayout = findViewById(R.id.loading);

    }


    public void onClickSignIn(View view) {
        loadingLayout.setVisibility(View.VISIBLE);
        vm.registration(userNameEditText.getText().toString(),
                emailAddressEditText.getText().toString(),
                passwordEditText.getText().toString(), o -> loadingLayout.setVisibility(View.GONE));

    }

    public void onClickGoBack(View view) {
        finish();
    }

    public void onClickSetAvatar(View view) {
        //vm.setAvatar(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            vm.sendImage(CropImage.getActivityResult(data).getUri(),avatarView);
        }
    }

}