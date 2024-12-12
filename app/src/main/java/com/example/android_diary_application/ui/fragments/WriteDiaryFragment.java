package com.example.android_diary_application.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
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

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ColorAdapter;
import com.example.android_diary_application.ui.HomePage;

public class WriteDiaryFragment extends Fragment {

    private EditText editTextDiaryContent;
    private Button buttonSaveDiary;
    private Button buttonColorPicker;
    private SharedPreferences sharedPreferences;
    private HomePage homePage; // 添加 HomePage 成员变量
    private Integer selectedColor = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_diary, container, false);

        editTextDiaryContent = view.findViewById(R.id.editTextDiaryContent);
        buttonSaveDiary = view.findViewById(R.id.buttonSaveDiary);
        buttonColorPicker = view.findViewById(R.id.buttonColorPicker);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        buttonSaveDiary.setOnClickListener(v -> saveDiaryEntry());

        buttonColorPicker.setOnClickListener(v -> {
            showColorPickerDialog();
        });

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

    private void showColorPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.color_picker_dialog, null);
        builder.setView(dialogView);

        GridView gridView = dialogView.findViewById(R.id.gridView);
        ColorAdapter adapter = new ColorAdapter(requireContext());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedColor = adapter.getColor(position); // 保存选中的颜色
            builder.create().dismiss();
            applySelectedColorToText();
        });

        // 可以在这里添加代码来检测 EditText 中的文本选择，但更简单的做法是在 EditText 失去焦点时处理
        // 因为对话框会暂时使 EditText 失去焦点，所以最好是在对话框关闭后的某个其他时刻处理

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 添加一个方法来处理 EditText 文本颜色的改变
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
            selectedColor = null; // 重置选中的颜色
        }
    }
}