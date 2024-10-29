package com.example.android_diary_application.data;

public class DiaryEntry {
    private String id; // 日记条目的唯一标识
    private String title; // 日记标题
    private String content; // 日记内容
    private String timestamp; // 时间戳


    public DiaryEntry() {}

    public DiaryEntry(String id, String title, String content, String timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
