package com.example.realtimechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailAddressEditText, passwordEditText;
    private String emailAddress, password;
    public static final String APP_PREFERENCES_CHECK = "LogStatus";
    private SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        emailAddressEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        mSettings = getSharedPreferences(APP_PREFERENCES_CHECK,MODE_PRIVATE);
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
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putBoolean(APP_PREFERENCES_CHECK,true);
                        editor.apply();
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