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
                Toast.makeText(LoginPage.this, "请输入用户名/邮箱和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 从 SharedPreferences 获取注册的用户名、邮箱和密码
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String registeredUsername = sharedPreferences.getString("username", null);
            String registeredEmail = sharedPreferences.getString("email", null);
            String registeredPassword = sharedPreferences.getString("password", null);

            // 检查是否有已注册用户
            if (TextUtils.isEmpty(registeredUsername) || TextUtils.isEmpty(registeredPassword)) {
                Toast.makeText(LoginPage.this, "该用户未注册，请先注册", Toast.LENGTH_SHORT).show();
                return;
            }

            // 验证用户名或邮箱和密码
            if ((usernameOrEmail.equals(registeredUsername) || usernameOrEmail.equals(registeredEmail))
                    && password.equals(registeredPassword)) {
                Toast.makeText(LoginPage.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginPage.this, "用户名/邮箱或密码错误", Toast.LENGTH_SHORT).show();
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