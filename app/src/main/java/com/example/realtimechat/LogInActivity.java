package com.example.realtimechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailAddressEditText, passwordEditText;
    private String emailAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        emailAddressEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
    }

    public void onClickGoToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }

    public void onClickLogIn(View view) {
        emailAddress = emailAddressEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(!emailAddress.isEmpty() & !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "С возвращением!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Пожалуйста заполните поля",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickGoToPasswordRecovery(View view) {
        startActivity(new Intent(getApplicationContext(),PasswordRecoveryActivity.class));
    }
}