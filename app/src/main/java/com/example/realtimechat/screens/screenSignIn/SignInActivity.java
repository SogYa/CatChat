package com.example.realtimechat.screens.screenSignIn;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.realtimechat.R;

public class SignInActivity extends AppCompatActivity {
    private SignInVM vm;
    private EditText emailAddressEditText, passwordEditText;
    private ConstraintLayout loadingLayout;
    private ConstraintLayout signInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log_in);

        vm = new ViewModelProvider(this).get(SignInVM.class);

        emailAddressEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loadingLayout = findViewById(R.id.loading);
        ImageView imageView = findViewById(R.id.imageCatSignIn);
        imageView.setOnClickListener(view -> vm.onCatClick());
    }

    public void onClickGoToRegistration(View view) {
        vm.goToRegistration();
    }

    public void onClickLogIn(View view) {
        loadingLayout.setVisibility(View.VISIBLE);
        vm.logIn(emailAddressEditText.getText().toString(), passwordEditText.getText().toString(), aBoolean -> {
            if (!aBoolean) {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    public void onClickGoToPasswordRecovery(View view) {
        vm.goToPasswordRecovery();
    }
}
