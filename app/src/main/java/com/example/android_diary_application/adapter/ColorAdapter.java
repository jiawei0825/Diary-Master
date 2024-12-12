package com.example.android_diary_application.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android_diary_application.R;

public class ColorAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private final int[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA,
            Color.CYAN, Color.BLACK, Color.WHITE, Color.GRAY, Color.LTGRAY,
            Color.DKGRAY,
    };

    public ColorAdapter(Context context) {
        super(context, R.layout.color_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.colorImageView);
        imageView.setBackgroundColor(colors[position]);

        return view;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    public int getColor(int position){
        return colors[position];
    }
}