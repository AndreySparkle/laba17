package com.example.laba17.data;

import android.content.Context;

import com.example.laba17.R;
import com.example.laba17.domain.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Supplies local test notifications without network requests.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getNotifications returns notifications in newest-first order.
 */
public class LocalNotificationRepository {

    private final Context context;

    public LocalNotificationRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public List<NotificationItem> getNotifications() {
        long now = System.currentTimeMillis();
        List<NotificationItem> items = new ArrayList<>();
        items.add(new NotificationItem(
                1,
                context.getString(R.string.notification_order_title),
                context.getString(R.string.notification_order_message),
                now - 15 * 60_000L,
                false
        ));
        items.add(new NotificationItem(
                2,
                context.getString(R.string.notification_sale_title),
                context.getString(R.string.notification_sale_message),
                now - 3 * 60 * 60_000L,
                false
        ));
        items.add(new NotificationItem(
                3,
                context.getString(R.string.notification_delivery_title),
                context.getString(R.string.notification_delivery_message),
                now - 2 * 24 * 60 * 60_000L,
                true
        ));
        return items;
    }
}
