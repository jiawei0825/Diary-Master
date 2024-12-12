package com.example.android_diary_application.ui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android_diary_application.R;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public CustomListViewAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.mainlist_item, parent, false);

        // Obtain a TextView from a custom layout
        TextView textView = convertView.findViewById(R.id.function);

        // Get data from the data source and set it to a TextView
        String item = getItem(position);
        textView.setText(item);

        return convertView;
    }
}
