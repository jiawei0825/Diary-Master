package com.example.android_diary_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_diary_application.R;

public class StartPage extends AppCompatActivity {

    private Button startButton; // Button to start

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the start page
        setContentView(R.layout.start_page);

        // Find the Start button in the layout
        startButton = findViewById(R.id.startButton);

        // Set the click listener for the button
        startButton.setOnClickListener(v -> {
            // When the button is clicked, transition to the login page
            Intent intent = new Intent(StartPage.this, LoginPage.class);
            startActivity(intent);
            finish(); // Close the start page to prevent the user from returning to it
        });
    }
}

