package com.example.android_diary_application.adapter;

import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<String> diaryList;

    public ItemAdapter(List<String> diaryList) {
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Load the journal entry layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String diaryEntry = diaryList.get(position);

        // Example parsing: The first line is the title, the second line is the date, and the third line is the content.
        String[] parts = diaryEntry.split("\n", 3);
        String title = parts[0];
        String date = parts.length > 1 ? parts[1] : "";
        String content = parts.length > 2 ? parts[2] : "";

        holder.tvDiaryDate.setText(date);
        holder.tvDiaryContent.setText(title); // Display the tile as a summary of the content

        // Set click event
        holder.itemView.setOnClickListener(view -> {
            // Launch DiaryDetailActivity and pass data
            Intent intent = new Intent(view.getContext(), com.example.android_diary_application.ui.DiaryDetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("date", date);
            intent.putExtra("content", content);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public void updateData(List<String> newDiaryList) {
        diaryList.clear();
        diaryList.addAll(newDiaryList);
        notifyDataSetChanged();
    }

    // ViewHolder inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiaryDate;
        TextView tvDiaryContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiaryDate = itemView.findViewById(R.id.tvDiaryDate);
            tvDiaryContent = itemView.findViewById(R.id.tvDiaryContent);
        }
    }
}