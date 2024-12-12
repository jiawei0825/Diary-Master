package com.example.android_diary_application.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_diary_application.R;
import com.example.android_diary_application.ui.LoginPage;

public class AccountFragment extends Fragment {

    private TextView textViewUsername, textViewAccount;
    private Button buttonLogout, buttonEditInfo;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewAccount = view.findViewById(R.id.textViewAccount);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonEditInfo = view.findViewById(R.id.buttonEditInfo);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // 加载用户信息
        loadUserInfo();

        // 修改信息按钮
        buttonEditInfo.setOnClickListener(v -> {
            String newUsername = "新用户名";  // 示例
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", newUsername);
            editor.apply();
            textViewUsername.setText(newUsername);
            Toast.makeText(getContext(), "用户名已修改", Toast.LENGTH_SHORT).show();
        });

        // 登出按钮
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void loadUserInfo() {
        String username = sharedPreferences.getString("username", "未设置用户名");
        String account = sharedPreferences.getString("account", "未设置邮箱");
        textViewUsername.setText(username);
        textViewAccount.setText(account);
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getContext(), LoginPage.class);
        startActivity(intent);
        requireActivity().finish();
    }
}