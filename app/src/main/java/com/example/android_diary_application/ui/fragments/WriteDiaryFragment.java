package com.example.android_diary_application.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_diary_application.R;
import com.example.android_diary_application.ui.HomePage;

public class WriteDiaryFragment extends Fragment {

    private EditText editTextDiaryContent;
    private Button buttonSaveDiary;
    private SharedPreferences sharedPreferences;
    private HomePage homePage; // 添加 HomePage 成员变量

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_diary, container, false);

        editTextDiaryContent = view.findViewById(R.id.editTextDiaryContent);
        buttonSaveDiary = view.findViewById(R.id.buttonSaveDiary);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        buttonSaveDiary.setOnClickListener(v -> saveDiaryEntry());

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomePage) {
            homePage = (HomePage) context; // 初始化 HomePage
        }
    }

    private void saveDiaryEntry() {
        String content = editTextDiaryContent.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(getContext(), "日记内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingDiaries = sharedPreferences.getString("diaryEntries", "");
        String updatedDiaries = existingDiaries + content + "||";
        editor.putString("diaryEntries", updatedDiaries);
        editor.apply();

        // 通知 HomePage 刷新 HistoryFragment
        if (homePage != null) {
            homePage.refreshHistoryFragment();
        }

        Toast.makeText(getContext(), "日记已保存！", Toast.LENGTH_SHORT).show();
        editTextDiaryContent.setText("");
    }
}