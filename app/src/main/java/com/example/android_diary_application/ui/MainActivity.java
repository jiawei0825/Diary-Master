package com.example.android_diary_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST_CODE = 1; // Request code
    private RecyclerView recyclerView;
    private List<String> recyclerItems;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // Initialize the list and adapter
        recyclerItems = new ArrayList<>();
        itemAdapter = new ItemAdapter(recyclerItems);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        // Add button click listener, jump to EditPage
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditPage.class);
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });

        // Add a click listener for the Remove button to remove the last item
        Button removeButton = findViewById(R.id.remove_button);
        removeButton.setOnClickListener(v -> {
            if (!recyclerItems.isEmpty()) {
                int lastIndex = recyclerItems.size() - 1;
                recyclerItems.remove(lastIndex);
                itemAdapter.notifyItemRemoved(lastIndex);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String itemText = data.getStringExtra("item_text");
            if (itemText != null && !itemText.isEmpty()) {
                recyclerItems.add(itemText);
                itemAdapter.notifyItemInserted(recyclerItems.size() - 1);
                recyclerView.smoothScrollToPosition(recyclerItems.size() - 1);
            }
        }
    }
}