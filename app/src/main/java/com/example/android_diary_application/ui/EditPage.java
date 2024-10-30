package com.example.android_diary_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditPage extends AppCompatActivity {

    private EditText titleInput;
    private EditText dateInput;
    private EditText contentInput;

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
    }
}