package com.example.android_diary_application.ui;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android_diary_application.R;
import com.example.android_diary_application.ui.fragments.AccountFragment;
import com.example.android_diary_application.ui.fragments.HistoryFragment;
import com.example.android_diary_application.ui.fragments.ScheduleFragment;
import com.example.android_diary_application.ui.fragments.WriteDiaryFragment;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // The calendar page is displayed by default
        loadFragment(new ScheduleFragment());

        // Set button click event
        Button btnSchedule = findViewById(R.id.btnSchedule);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnWrite = findViewById(R.id.btnWrite);
        Button btnAccount = findViewById(R.id.btnAccount);

        btnSchedule.setOnClickListener(v -> loadFragment(new ScheduleFragment()));
        btnHistory.setOnClickListener(v -> loadFragment(new HistoryFragment()));
        btnWrite.setOnClickListener(v -> loadFragment(new WriteDiaryFragment()));
        btnAccount.setOnClickListener(v -> loadFragment(new AccountFragment()));
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutContent, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void refreshHistoryFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutContent);
        if (currentFragment instanceof HistoryFragment) {
            ((HistoryFragment) currentFragment).refreshData();
        }
    }
}