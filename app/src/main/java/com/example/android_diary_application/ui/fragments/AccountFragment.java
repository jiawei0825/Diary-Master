package com.example.android_diary_application.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_diary_application.R;
import com.example.android_diary_application.ui.LoginPage;

import java.util.Calendar;

public class AccountFragment extends Fragment {

    private TextView textViewUsername, textViewEmail, textViewPassword, textViewGender, textViewBirthday;
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextGender, editTextBirthday;
    private Button buttonEdit, buttonSave, buttonLogout;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // TextView
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewPassword = view.findViewById(R.id.textViewPassword);
        textViewGender = view.findViewById(R.id.textViewGender);
        textViewBirthday = view.findViewById(R.id.textViewBirthday);

        // EditText
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextGender = view.findViewById(R.id.editTextGender);
        editTextBirthday = view.findViewById(R.id.editTextBirthday);

        // Buttons
        buttonEdit = view.findViewById(R.id.buttonEdit);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // 初始化状态
        loadUserInfo();
        toggleEditMode(false);

        // 修改按钮
        buttonEdit.setOnClickListener(v -> toggleEditMode(true));

        // 保存按钮
        buttonSave.setOnClickListener(v -> saveUserInfo());

        // 生日选择器
        editTextBirthday.setOnClickListener(v -> showDatePickerDialog());

        // 登出按钮
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void loadUserInfo() {
        String username = sharedPreferences.getString("username", "未设置用户名");
        String email = sharedPreferences.getString("email", "未设置邮箱");
        String password = sharedPreferences.getString("password", "未设置密码");
        String gender = sharedPreferences.getString("gender", "未设置性别");
        String birthday = sharedPreferences.getString("birthday", "未设置生日");

        textViewUsername.setText("用户名: " + username);
        textViewEmail.setText("邮箱: " + email);
        textViewPassword.setText("密码: " + password);
        textViewGender.setText("性别: " + gender);
        textViewBirthday.setText("生日: " + birthday);

        editTextUsername.setText(username);
        editTextEmail.setText(email);
        editTextPassword.setText(password);
        editTextGender.setText(gender);
        editTextBirthday.setText(birthday);
    }

    private void saveUserInfo() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "用户名、邮箱和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("gender", gender);
        editor.putString("birthday", birthday);
        editor.apply();

        Toast.makeText(getContext(), "信息已保存", Toast.LENGTH_SHORT).show();

        loadUserInfo();
        toggleEditMode(false);
    }

    private void toggleEditMode(boolean isEditMode) {
        if (isEditMode) {
            // 显示 EditText
            editTextUsername.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            editTextGender.setVisibility(View.VISIBLE);
            editTextBirthday.setVisibility(View.VISIBLE);

            // 隐藏 TextView
            textViewUsername.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewPassword.setVisibility(View.GONE);
            textViewGender.setVisibility(View.GONE);
            textViewBirthday.setVisibility(View.GONE);

            buttonSave.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.GONE);
        } else {
            // 显示 TextView
            textViewUsername.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            textViewPassword.setVisibility(View.VISIBLE);
            textViewGender.setVisibility(View.VISIBLE);
            textViewBirthday.setVisibility(View.VISIBLE);

            // 隐藏 EditText
            editTextUsername.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);
            editTextGender.setVisibility(View.GONE);
            editTextBirthday.setVisibility(View.GONE);

            buttonSave.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.VISIBLE);
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            String selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            editTextBirthday.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
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