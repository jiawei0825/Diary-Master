package com.example.android_diary_application.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        // Gets references to input fields and buttons
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBack = findViewById(R.id.btnBack);

        // Return button click event
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish(); // End current activity
            }
        });

        // Register button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register button click event
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Verify data
                if (TextUtils.isEmpty(username) || username.length() < 4) {
                    Toast.makeText(RegisterPage.this, "The user name must be at least 4 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterPage.this, "The email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!email.matches(emailPattern)) {
                    Toast.makeText(RegisterPage.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(RegisterPage.this, "The password needs at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterPage.this, "Confirm that the password is inconsistent with the password", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Save user information to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String registeredEmail = sharedPreferences.getString("email", null);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", email); // Save email
                editor.putString("password", password);
                editor.apply();

                // The registration success message is displayed and the login page is displayed
                Toast.makeText(RegisterPage.this, "Registered successfully ^_^", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish(); // End current activity
            }
        });
    }
}