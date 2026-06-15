package com.example.laba17.domain.model;

/**
 * Purpose: Describes one local shop notification.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters expose title, message, timestamp and read state.
 */
public class NotificationItem {

    private final int id;
    private final String title;
    private final String message;
    private final long createdAtMillis;
    private boolean read;

    public NotificationItem(int id, String title, String message, long createdAtMillis, boolean read) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAtMillis = createdAtMillis;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public long getCreatedAtMillis() {
        return createdAtMillis;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
