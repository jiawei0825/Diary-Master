package com.example.android_diary_application.data;

import java.time.LocalDateTime;

public class User {
    private String username;
    private String accountId;
    private LocalDateTime registrationDate;

    public User(String username, String accountId, LocalDateTime registrationDate) {
        this.username = username;
        this.accountId = accountId;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "DiaryUser{" +
                "username='" + username + '\'' +
                ", accountId='" + accountId + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
