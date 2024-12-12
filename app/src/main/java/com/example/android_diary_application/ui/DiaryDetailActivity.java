package com.example.android_diary_application.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;

public class DiaryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        // 获取传递的数据
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String content = getIntent().getStringExtra("content");

        // 绑定视图
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvContent = findViewById(R.id.tvContent);

        // 设置数据
        tvTitle.setText(title);
        tvDate.setText(date);
        tvContent.setText(content);
    }
}