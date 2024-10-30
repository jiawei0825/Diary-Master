package com.example.android_diary_application.data;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreRepository {
    private FirebaseFirestore db;

    public FirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addDataToFirestore(DiaryEntry data) {
        // 使用 db 进行 Firestore 操作
        db.collection("diary_entries")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    // 添加成功的回调
                })
                .addOnFailureListener(e -> {
                    // 添加失败的回调
                });
    }
}