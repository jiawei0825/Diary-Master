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

        // TextViews
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewPassword = view.findViewById(R.id.textViewPassword);
        textViewGender = view.findViewById(R.id.textViewGender);
        textViewBirthday = view.findViewById(R.id.textViewBirthday);

        // EditTexts
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

        // Initialize state
        loadUserInfo();
        toggleEditMode(false);

        // Edit button
        buttonEdit.setOnClickListener(v -> toggleEditMode(true));

        // Save button
        buttonSave.setOnClickListener(v -> saveUserInfo());

        // Birthday picker
        editTextBirthday.setOnClickListener(v -> showDatePickerDialog());

        // Logout button
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void loadUserInfo() {
        String username = sharedPreferences.getString("username", "Username not set");
        String email = sharedPreferences.getString("email", "Email not set");
        String password = sharedPreferences.getString("password", "Password not set");
        String gender = sharedPreferences.getString("gender", "Gender not set");
        String birthday = sharedPreferences.getString("birthday", "Birthday not set");

        textViewUsername.setText("Username: " + username);
        textViewEmail.setText("Email: " + email);
        textViewPassword.setText("Password: " + password);
        textViewGender.setText("Gender: " + gender);
        textViewBirthday.setText("Birthday: " + birthday);

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
            Toast.makeText(getContext(), "Username, email, and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("gender", gender);
        editor.putString("birthday", birthday);
        editor.apply();

        Toast.makeText(getContext(), "Information saved", Toast.LENGTH_SHORT).show();

        loadUserInfo();
        toggleEditMode(false);
    }

    private void toggleEditMode(boolean isEditMode) {
        if (isEditMode) {
            // Show EditTexts
            editTextUsername.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            editTextGender.setVisibility(View.VISIBLE);
            editTextBirthday.setVisibility(View.VISIBLE);

            // Hide TextViews
            textViewUsername.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewPassword.setVisibility(View.GONE);
            textViewGender.setVisibility(View.GONE);
            textViewBirthday.setVisibility(View.GONE);

            buttonSave.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.GONE);
        } else {
            // Show TextViews
            textViewUsername.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            textViewPassword.setVisibility(View.VISIBLE);
            textViewGender.setVisibility(View.VISIBLE);
            textViewBirthday.setVisibility(View.VISIBLE);

            // Hide EditTexts
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