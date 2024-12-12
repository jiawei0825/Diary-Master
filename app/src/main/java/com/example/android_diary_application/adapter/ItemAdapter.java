package com.example.android_diary_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<String> items;

    public ItemAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Add a method, update the data list, and notify RecyclerView
    public void updateData(List<String> newItems) {
        items.clear();  // Clear old data
        items.addAll(newItems);  // Add new data
        notifyDataSetChanged();  // Update RecyclerView
    }

    public void addItem(String item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);  // use notifyItemInserted for efficiency
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);  // Use notifyItemRemoved for efficiency
            notifyItemRangeChanged(position, items.size() - position);  // Update the location of the remaining items
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text_view);
        }
    }
}