package com.example.android_diary_application.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        EditText etUsernameOrEmail = findViewById(R.id.etUsernameOrEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnForgotPassword = findViewById(R.id.btnForgotPassword);

        btnLogin.setOnClickListener(v -> {
            String usernameOrEmail = etUsernameOrEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(usernameOrEmail) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginPage.this, "Please enter username/email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the registered user name, email address, and password from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String registeredUsername = sharedPreferences.getString("username", null);
            String registeredEmail = sharedPreferences.getString("email", null);
            String registeredPassword = sharedPreferences.getString("password", null);

            // Check whether there are registered users
            if (TextUtils.isEmpty(registeredUsername) || TextUtils.isEmpty(registeredPassword)) {
                Toast.makeText(LoginPage.this, "The user is not registered, please register first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verify user name or email and password
            if ((usernameOrEmail.equals(registeredUsername) || usernameOrEmail.equals(registeredEmail))
                    && password.equals(registeredPassword)) {
                Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginPage.this, "Username/email or password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });

        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, ForgotPasswordPage.class);
            startActivity(intent);
        });
    }
}