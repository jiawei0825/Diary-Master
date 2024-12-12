package com.example.android_diary_application.data;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreRepository {
    private FirebaseFirestore db;

    public FirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addDataToFirestore(DiaryEntry data) {
        // Use db to operate Firestore
        db.collection("diary_entries")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    // Add successful callback
                })
                .addOnFailureListener(e -> {
                    // Add failed callback
                });
    }
}