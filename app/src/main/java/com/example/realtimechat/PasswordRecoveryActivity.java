package com.example.realtimechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRecoveryActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private EditText emailAddressEditText;
private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        mAuth = FirebaseAuth.getInstance();
        emailAddressEditText = (EditText) findViewById(R.id.editTextEmailAddressRec);
    }

    public void onClickSendRecoveryMail(View view) {
        email = emailAddressEditText.getText().toString();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(), "Письмо отправлено. ", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}