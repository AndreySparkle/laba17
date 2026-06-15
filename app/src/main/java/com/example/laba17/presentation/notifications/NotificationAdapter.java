package com.example.laba17.presentation.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.common.OrderDateFormatter;
import com.example.laba17.domain.model.NotificationItem;

import java.util.List;
import java.util.Locale;

/**
 * Purpose: Displays local notifications in a RecyclerView.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onBindViewHolder binds title, message and formatted time.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    private final List<NotificationItem> items;

    public NotificationAdapter(List<NotificationItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        NotificationItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.message.setText(item.getMessage());
        holder.time.setText(OrderDateFormatter.format(
                item.getCreatedAtMillis(),
                System.currentTimeMillis(),
                Locale.getDefault()
        ));
        holder.itemView.setAlpha(item.isRead() ? 0.65f : 1f);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView message;
        private final TextView time;

        Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            time = itemView.findViewById(R.id.notificationTime);
        }
    }
}
