package com.example.android_diary_application.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 加载日记数据
        List<String> diaryEntries = loadDiaryEntries();
        itemAdapter = new ItemAdapter(diaryEntries);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }

    private List<String> loadDiaryEntries() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedDiaries = sharedPreferences.getString("diaryEntries", "");

        List<String> diaryList = new ArrayList<>();
        if (!savedDiaries.isEmpty()) {
            String[] diaries = savedDiaries.split("\\|\\|"); // 根据 "||" 分割日记
            for (String diary : diaries) {
                diaryList.add(diary);
            }
        }

        return diaryList;
    }

    public void refreshData() {
        List<String> updatedDiaryEntries = loadDiaryEntries();
        itemAdapter.updateData(updatedDiaryEntries);
    }
}