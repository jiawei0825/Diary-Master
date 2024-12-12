package com.example.android_diary_application.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_diary_application.ui.Chat.ChatActivity;
import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ColorAdapter;
import com.example.android_diary_application.ui.HomePage;

public class WriteDiaryFragment extends Fragment {

    private EditText editTextDiaryContent;
    private Button buttonSaveDiary;
    private Button buttonColorPicker;
    private Button chat;
    private SharedPreferences sharedPreferences;
    private HomePage homePage; // Add HomePage member variable
    private Integer selectedColor = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_diary, container, false);

        editTextDiaryContent = view.findViewById(R.id.editTextDiaryContent);
        buttonSaveDiary = view.findViewById(R.id.buttonSaveDiary);
        buttonColorPicker = view.findViewById(R.id.buttonColorPicker);
        chat = view.findViewById(R.id.chat);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        buttonSaveDiary.setOnClickListener(v -> saveDiaryEntry());

        buttonColorPicker.setOnClickListener(v -> {
            showColorPickerDialog();
        });

        chat.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChatActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomePage) {
            homePage = (HomePage) context; // Initialize HomePage
        }
    }

    private void saveDiaryEntry() {
        String content = editTextDiaryContent.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(getContext(), "The content of diary can not be null！", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingDiaries = sharedPreferences.getString("diaryEntries", "");
        String updatedDiaries = existingDiaries + content + "||";
        editor.putString("diaryEntries", updatedDiaries);
        editor.apply();

        // Notify HomePage to refresh HistoryFragment
        if (homePage != null) {
            homePage.refreshHistoryFragment();
        }

        Toast.makeText(getContext(), "Diary saved！", Toast.LENGTH_SHORT).show();
        editTextDiaryContent.setText("");
    }

    private void showColorPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.color_picker_dialog, null);
        builder.setView(dialogView);

        GridView gridView = dialogView.findViewById(R.id.gridView);
        ColorAdapter adapter = new ColorAdapter(requireContext());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedColor = adapter.getColor(position); // Save the selected color
            builder.create().dismiss();
            applySelectedColorToText();
        });

        // We can add code here to detect text selection in EditText, but it's easier to handle it when EditText loses focus.
        // Because the dialog takes the EditText out of focus temporarily, it's best to deal with it at some other time after the dialog is closed.

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Add a method to handle EditText text color changes
    private void applySelectedColorToText() {
        if (selectedColor != null) {
            int startSelection = editTextDiaryContent.getSelectionStart();
            int endSelection = editTextDiaryContent.getSelectionEnd();
            if (startSelection != endSelection) {
                Editable editableText = editTextDiaryContent.getText();
                Spannable spannable = new SpannableString(editableText);
                spannable.setSpan(new ForegroundColorSpan(selectedColor), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                editTextDiaryContent.setText(spannable);
            }
            selectedColor = null; // Reset selected color
        }
    }
}