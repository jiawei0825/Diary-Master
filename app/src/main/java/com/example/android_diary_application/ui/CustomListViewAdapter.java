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

        // 获取自定义布局中的TextView
        TextView textView = convertView.findViewById(R.id.function);

        // 从数据源中获取数据并设置到TextView上
        String item = getItem(position);
        textView.setText(item);

        return convertView;
    }
}
