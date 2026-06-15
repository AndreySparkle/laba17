package com.example.laba17.presentation.notifications;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.data.LocalNotificationRepository;
import com.example.laba17.presentation.common.BaseDrawerActivity;

/**
 * Purpose: Displays local shop notifications.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate configures the notifications RecyclerView.
 */
public class NotificationActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        RecyclerView list = findViewById(R.id.notificationsList);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new NotificationAdapter(
                new LocalNotificationRepository(this).getNotifications()
        ));
    }
}
