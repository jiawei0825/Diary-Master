package com.example.android_diary_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ItemAdapter;
import com.example.android_diary_application.data.DiaryEntry;
import com.example.android_diary_application.data.FirestoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private TextView textViewUsername, textViewAccount, textViewRegistrationDate;
    private Button buttonEditInfo, buttonLogout, buttonWriteDiary;
    private RecyclerView recyclerViewDiaryEntries;
    private ItemAdapter itemAdapter;
    private FirebaseAuth auth;
    private FirestoreRepository firestoreRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // 初始化 Firebase 和 FirestoreRepository
        auth = FirebaseAuth.getInstance();
        firestoreRepository = new FirestoreRepository();

        // 绑定视图
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewAccount = findViewById(R.id.textViewAccount);
        textViewRegistrationDate = findViewById(R.id.textViewRegistrationDate);
        buttonEditInfo = findViewById(R.id.buttonEditInfo);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonWriteDiary = findViewById(R.id.buttonWriteDiary);
        recyclerViewDiaryEntries = findViewById(R.id.recyclerViewDiaryEntries);

        // 配置 RecyclerView
        recyclerViewDiaryEntries.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerViewDiaryEntries.setAdapter(itemAdapter);

        // 显示用户信息
//        loadUserInfo();
//
//        // 加载日记记录
//        loadDiaryEntries();

        // 按钮事件
        buttonEditInfo.setOnClickListener(view -> editUsername());
        buttonLogout.setOnClickListener(view -> logout());
        buttonWriteDiary.setOnClickListener(view -> writeDiary());
    }

//    private void loadUserInfo() {
//        String userId = auth.getCurrentUser().getUid();
//        firestoreRepository.getUserInfo(userId)
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        textViewUsername.setText(documentSnapshot.getString("username"));
//                        textViewAccount.setText(documentSnapshot.getString("account"));
//                        textViewRegistrationDate.setText(documentSnapshot.getString("registrationDate"));
//                    } else {
//                        Toast.makeText(this, "用户信息未找到", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(e -> Toast.makeText(this, "加载用户信息失败", Toast.LENGTH_SHORT).show());
//    }
//
//    private void loadDiaryEntries() {
//        String userId = auth.getCurrentUser().getUid();
//        firestoreRepository.getUserDiaryEntries(userId)
//                .addOnSuccessListener(querySnapshot -> {
//                    List<String> entries = new ArrayList<>();
//                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
//                        DiaryEntry entry = doc.toObject(DiaryEntry.class);
//                        entries.add(entry != null ? entry.getContent() : "无内容");
//                    }
//                    itemAdapter = new ItemAdapter(entries);
//                    recyclerViewDiaryEntries.setAdapter(itemAdapter);
//                })
//                .addOnFailureListener(e -> Toast.makeText(this, "加载日记失败", Toast.LENGTH_SHORT).show());
//    }

    private void editUsername() {
        // 添加修改用户名逻辑
        Toast.makeText(this, "修改用户名功能", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        auth.signOut();
        Intent intent = new Intent(HomePage.this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void writeDiary() {
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }
}