package com.example.android_diary_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ColorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditPage extends AppCompatActivity {

    private EditText titleInput;
    private EditText dateInput;
    private EditText contentInput;
    private Integer selectedColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_page);

        titleInput = findViewById(R.id.title_input);
        dateInput = findViewById(R.id.date_input);
        contentInput = findViewById(R.id.content_input);

        Button addCurrentDateButton = findViewById(R.id.add_current_date_button);
        addCurrentDateButton.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            dateInput.setText(sdf.format(new Date()));
        });

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> {
            String titleText = titleInput.getText().toString();
            String dateText = dateInput.getText().toString();
            String inputText = contentInput.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("item_text", "Title:" +  titleText + "\n" + "Date:" + "\n" + dateText );
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        findViewById(R.id.buttonColorPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog();
            }
        });
    }

    private void showColorPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.color_picker_dialog, null);
        builder.setView(dialogView);

        GridView gridView = dialogView.findViewById(R.id.gridView);
        ColorAdapter adapter = new ColorAdapter(this);
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
            int startSelection = contentInput.getSelectionStart();
            int endSelection = contentInput.getSelectionEnd();
            if (startSelection != endSelection) {
                Editable editableText = contentInput.getText();
                Spannable spannable = new SpannableString(editableText);
                spannable.setSpan(new ForegroundColorSpan(selectedColor), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentInput.setText(spannable);
            }
            selectedColor = null; // 重置选中的颜色
        }
    }
}