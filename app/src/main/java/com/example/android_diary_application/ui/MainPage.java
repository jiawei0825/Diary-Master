package com.example.android_diary_application.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.RelativeLayout;

import com.example.android_diary_application.adapter.ItemAdapter;
import com.example.android_diary_application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainPage extends AppCompatActivity {
    private FrameLayout listViewContainer;
    private FrameLayout overlayFrameLayout;
    private RecyclerView recyclerView;
    private List<String> recyclerItems;
    private int currentItemCount = 0;
    ItemAdapter itemAdapter;

    @SuppressLint("ClickableViewAccessibility")// 用来消除overlayFrameLayout这部分的无障碍适配提示，无功能
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        listViewContainer = findViewById(R.id.listViewContainer);
        ListView diaryListView = findViewById(R.id.diaryListView);
        Button showListViewButton = findViewById(R.id.showListViewButton);
        overlayFrameLayout = findViewById(R.id.overlayFrameLayout);

        List<String> items = Arrays.asList("月度视图", "列表视图", "收藏", "草稿");

        CustomListViewAdapter adapter = new CustomListViewAdapter(this, items);
        diaryListView.setAdapter(adapter);

        showListViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayFrameLayout.setVisibility(View.VISIBLE);
                showListView();
            }
        });

        overlayFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isPointInsideView(listViewContainer, event.getRawX(), event.getRawY())) {
                    hideListView();
                    overlayFrameLayout.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        Button addButton = findViewById(R.id.add_button);
        Button removeButton = findViewById(R.id.remove_button);

        recyclerItems = new ArrayList<>();
        recyclerItems.add("item1");
        itemAdapter = new ItemAdapter(recyclerItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(itemAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        List<String> finalItems = recyclerItems;
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalItems.isEmpty()) {
                    removeItem(finalItems.size() - 1);
                }
            }
        });

    }






    private void showListView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.75f), // 调整列表占比
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        listViewContainer.bringToFront();
        listViewContainer.setLayoutParams(params);
        listViewContainer.setVisibility(View.VISIBLE);

    }

    private void hideListView() {
        listViewContainer.setVisibility(View.GONE);
    }

    private boolean isPointInsideView(View view, float x, float y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        if (x > viewX && x < viewX + view.getWidth() &&
                y > viewY && y < viewY + view.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    private void addItem() {
        recyclerItems.add("Item " + (++currentItemCount));
        itemAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(recyclerItems.size() - 1);
    }

    private void removeItem(int position) {
        recyclerItems.remove(position);
        itemAdapter.notifyDataSetChanged();
    }

}
