package com.example.realtimechat.screens.screenPasswordRecovery;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.realtimechat.R;

public class PasswordRecoveryActivity extends AppCompatActivity {
    private EditText emailAddressEditText;
    private PasswordRecoveryVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        emailAddressEditText = findViewById(R.id.editTextEmailAddressRec);

        vm = new ViewModelProvider(this).get(PasswordRecoveryVM.class);
    }

    public void onClickSendRecoveryMail(View view) {
        vm.passwordRecovery(emailAddressEditText.getText().toString());
    }

    public void onClickGoBack(View view) {
        finish();
    }
}